package com.elminster.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.elminster.common.constants.Constants.EncodingConstants;
import com.elminster.common.constants.Constants.StringConstants;

import static com.elminster.common.constants.Constants.StringConstants.EMPTY_STRING;
import static com.elminster.common.constants.Constants.StringConstants.TAB;
import static com.elminster.common.constants.Constants.StringConstants.LF;
import static com.elminster.common.constants.Constants.StringConstants.UNDER_LINE;
import com.elminster.common.util.Messages.Message;

/**
 * File Utilities
 * 
 * @author Gu
 * @version 1.0
 */
public abstract class FileUtil {

  /** File separate in Windows System: <code>\\</code> */
  public static final String WINDOW_FILE_SEPARATE = "\\\\"; //$NON-NLS-1$

  /** File separate in Linux/Unix System: <code>/</code> */
  public static final String LINUX_FILE_SEPARATE = "/"; //$NON-NLS-1$

  /** Buffer size in file copy */
  public static final int BUFF_SIZE = 1024;

  /** Base folder path */
  public static final String BASE = new File(".").getAbsolutePath() //$NON-NLS-1$
      + LINUX_FILE_SEPARATE;
  /** manifest file's name */
  private static final String MANIFEST_FILE_NAME = "manifest.mf"; //$NON-NLS-1$
  /** the UTF-8 charset. */
  public static final String DEFAULT_CHARSET = EncodingConstants.UTF8;
  /** the extension split: <code>.</code>. */
  public static final String EXTENSION_SPLIT = StringConstants.DOT;
  /** the command to parent directory: <code>../</code>. */
  public static final String PARENT_DIR = "../";
  /** the command to current directory: <code>./</code>. */
  public static final String CURRENT_DIR = "./";
  /** the file separator specified in OS. */
  public static final String FILE_SEPARATOR = System.getProperty("file.separator"); //$NON-NLS-1$
  /** the temporary directory specified in OS. */
  public static final String TEMPORARY_DIR = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$

  /**
   * Get the system temporary folder.
   * 
   * @return the system temporary folder path
   */
  public static String getSystemTemporaryFolder() {
    return fixFolderName(TEMPORARY_DIR);
  }

  /**
   * Generate temporary folder by UUID.
   * 
   * @param parent
   *          the parent folder (if it is not set, the parent folder will be the
   *          system temporary folder).
   * @return the temporary folder path
   */
  public static String generateTemporaryFolderByUUID(String parent) {
    String base = null == parent ? getSystemTemporaryFolder() : parent;
    UUID uuid = UUID.randomUUID();
    return fixFolderName(fixFolderName(base) + uuid.toString());
  }

  /**
   * Generate temporary file by UUID.
   * 
   * @param parent
   *          the parent folder (if it is not set, the parent folder will be the
   *          system temporary folder).
   * @return the temporary file path
   */
  public static String generateTemporaryFileByUUID(String parent) {
    String base = null == parent ? getSystemTemporaryFolder() : parent;
    UUID uuid = UUID.randomUUID();
    return fixFolderName(base) + uuid.toString();
  }

  /**
   * Generate the temporary file by timestamp.
   * 
   * @param parent
   *          the parent folder (if it is not set, the parent folder will be the
   *          system temporary folder).
   * @param prefix
   *          the prefix of the temporary file
   * @param suffix
   *          the suffix of the temporary file
   * @return the temporary file path
   */
  public static String generateTemporaryFileByTimestamp(String parent, String prefix, String suffix) {
    String base = null == parent ? getSystemTemporaryFolder() : parent;
    StringBuilder sb = new StringBuilder();
    sb.append(base);
    if (null != prefix) {
      sb.append(prefix);
    }
    sb.append(System.currentTimeMillis());
    sb.append(UNDER_LINE);
    sb.append(getRandomString(6));
    sb.append(UNDER_LINE);
    if (null != suffix) {
      sb.append(suffix);
    }
    return sb.toString();
  }

  /**
   * Get random String.
   * 
   * @param length
   *          the String length
   * @return the specified length random String
   */
  public static String getRandomString(int length) {
    char[] charArray = new char[length];
    for (int i = 0; i < length; i++) {
      Random r = new Random();
      int n = r.nextInt(123);
      while (n < 48 || (n > 57 && n < 65) || (n > 90 && n < 97) || n > 122) {
        n = r.nextInt(123);
      }
      charArray[i] = (char) n;
    }
    return String.valueOf(charArray);
  }

  /**
   * Create folder using the specified absolute file path (create the parent
   * folder if necessary)
   * 
   * @param fileName
   *          the absolute file path
   */
  public static void createFolder(String fileName) {
    if (null == fileName) {
      return;
    }
    String name = replaceFileSeparate(fileName);
    boolean isEndedBySeparate = false;
    // whether end with "/"
    if (name.endsWith(LINUX_FILE_SEPARATE)) {
      isEndedBySeparate = true;
    }
    File f;
    // split the full file name with "/"
    String[] splited = name.split(LINUX_FILE_SEPARATE);
    int length = splited.length;
    String folder = EMPTY_STRING;
    // ignore the last character if it was "/"
    length = isEndedBySeparate ? length : length - 1;
    // create the parent folder if necessary
    for (int i = 0; i < length; i++) {
      folder += splited[i] + LINUX_FILE_SEPARATE;
      f = new File(folder);
      if (!f.exists() || !f.isDirectory()) {
        f.mkdir();
      }
    }
  }
  
  /**
   * Create a new file.
   * @param fileName the file name
   * @throws IOException on error
   */
  public static void createNewFile(String fileName) throws IOException {
    createFolder(fileName);
    File file = new File(fileName);
    file.createNewFile();
  }

  /**
   * Move the specified file to another file (create folder for move to file if
   * necessary)
   * 
   * @param srcFile
   *          the file to move
   * @param destFile
   *          the file to move to
   */
  public static void moveFile(String srcFile, String destFile) throws IllegalArgumentException {
    if (isFileExist(srcFile)) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FILE_ISNT_EXIST, srcFile));
    }
    File src = new File(srcFile);
    createFolder(destFile);
    File dest = new File(destFile);
    src.renameTo(dest);
  }

  /**
   * Copy the specified file to another file (create folder for move to file if
   * necessary)
   * 
   * @param srcFile
   *          the file to copy
   * @param destFile
   *          the file to move to
   * @throws IOException
   *           on error
   */
  public static void copyFile(String srcFile, String destFile) throws IllegalArgumentException, IOException {
    if (StringUtil.isEmpty(srcFile)) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FILE_IS_NULL, srcFile));
    }
    File file = new File(srcFile);
    if (!file.exists()) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FILE_ISNT_EXIST, srcFile));
    }
    if (file.isDirectory()) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FILE_IS_FOLDER, srcFile));
    }
    if (StringUtil.isEmpty(destFile)) {
      throw new IllegalArgumentException(Messages.getString(Message.DEST_FILE_IS_NULL, destFile));
    }
    createFolder(destFile);
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new FileInputStream(srcFile);
      out = new FileOutputStream(destFile);
      byte[] buffer = new byte[BUFF_SIZE];
      int n = 0;
      while (-1 != (n = in.read(buffer))) {
        out.write(buffer, 0, n);
      }
      out.flush();
    } finally {
      if (null != out) {
        out.close();
      }
      if (null != in) {
        in.close();
      }
    }
  }

  /**
   * Copy the specified file to another file (create folder for move to file if
   * necessary)
   * 
   * @param srcInputStream
   *          the inputString to copy
   * @param destFile
   *          the file to move to
   * @throws Exception
   *           Exception
   */
  public static void copyFile(InputStream srcInputStream, String destFile) throws Exception {
    if (StringUtil.isEmpty(destFile)) {
      throw new IllegalArgumentException(Messages.getString(Message.DEST_FILE_IS_NULL, destFile));
    }
    createFolder(destFile);
    OutputStream out = null;
    try {
      out = new FileOutputStream(destFile);
      byte[] buffer = new byte[BUFF_SIZE];
      int n = 0;
      while (-1 != (n = srcInputStream.read(buffer))) {
        out.write(buffer, 0, n);
      }
      out.flush();
    } finally {
      if (null != out) {
        out.close();
      }
    }
  }

  /**
   * Fix the specified folder name what should end with a file separate
   * 
   * @param folderName
   *          the specified folder name
   * @return the folder name which end with a file separate
   */
  public static String fixFolderName(String folderName) throws IllegalArgumentException {
    if (StringUtil.isEmpty(folderName)) {
      throw new IllegalArgumentException(Messages.getString(Message.FOLDER_NAME_IS_NULL));
    }
    String rst = replaceFileSeparate(folderName);
    if (rst.endsWith(LINUX_FILE_SEPARATE)) {
      return rst;
    } else {
      return rst + LINUX_FILE_SEPARATE;
    }
  }

  /**
   * Copy the specified file to a folder (create the copy to folder if
   * necessary)
   * 
   * @param srcFile
   *          the file to copy
   * @param destFolder
   *          the copy to folder
   * @throws IOException
   *           on error
   */
  public static void copyFile2Directory(String srcFile, String destFolder) throws IllegalArgumentException, IOException {
    if (StringUtil.isEmpty(srcFile)) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FILE_IS_NULL, srcFile));
    }

    File file = new File(srcFile);
    if (!file.exists()) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FILE_ISNT_EXIST, srcFile));
    }
    if (file.isDirectory()) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FILE_IS_FOLDER, srcFile));
    }
    if (StringUtil.isEmpty(destFolder)) {
      throw new IllegalArgumentException(Messages.getString(Message.DEST_FILE_IS_NULL, destFolder));
    }

    String fixedFolderName = fixFolderName(destFolder);
    createFolder(fixedFolderName);
    String destFileName = fixedFolderName + getFileName(srcFile);
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new FileInputStream(srcFile);
      out = new FileOutputStream(destFileName);
      int n = 0;
      byte[] buffer = new byte[BUFF_SIZE];
      while (-1 != (n = in.read(buffer))) {
        out.write(buffer, 0, n);
      }
      out.flush();
    } finally {
      if (null != out) {
        out.close();
      }
      if (null != in) {
        in.close();
      }
    }
  }

  /**
   * Copy the specified folder to an folder (create the copy to folder if
   * necessary).
   * 
   * @param src
   *          the folder to copy
   * @param dest
   *          the copy to folder
   * @throws IOException
   *           on error
   */
  public static void copyDirectory(String srcFolder, String destFolder) throws IllegalArgumentException, IOException {
    if (StringUtil.isEmpty(srcFolder)) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FOLDER_IS_NULL, srcFolder));
    }
    File file = new File(srcFolder);
    if (!file.exists()) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FOLDER_ISNT_EXIST, srcFolder));
    }
    if (file.isFile()) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FOLDER_IS_FILE, srcFolder));
    }
    if (StringUtil.isEmpty(destFolder)) {
      throw new IllegalArgumentException(Messages.getString(Message.DEST_FILE_IS_NULL, destFolder));
    }
    createFolder(destFolder);
    File[] srcFiles = file.listFiles();
    int length = srcFiles.length;
    for (int i = 0; i < length; i++) {
      File f = srcFiles[i];
      copyFile2Directory(f.getAbsolutePath(), destFolder);
    }
  }

  /**
   * Convert all file separate of the specified file name to "/"
   * 
   * @param fileName
   *          the specified file name
   * @return converted file name
   */
  public static String replaceFileSeparate(String fileName) {
    if (null == fileName) {
      return null;
    }
    return fileName.replaceAll(WINDOW_FILE_SEPARATE, LINUX_FILE_SEPARATE);
  }

  /**
   * Get the folder name of the specified absolute path
   * 
   * @param path
   *          the specified absolute path
   * @return the folder name
   */
  public static String getPath(String path) {
    String value = null;
    File file = new File(path);
    value = file.getParentFile().getAbsolutePath();
    return value;
  }

  /**
   * Get the file name of the specified absolute path.
   * 
   * @param path
   *          the specified absolute path
   * @return the file name
   */
  public static String getFileName(String path) {
    String fileName = EMPTY_STRING;
    File file = new File(path);
    if (file.isFile()) {
      fileName = file.getName();
    }
    return fileName;
  }

  /**
   * Get the file extension of the specified absolute path.
   * 
   * @param path
   *          the specified absolute path
   * @return the file extension
   */
  public static String getFileExtension(String path) {
    if (StringUtil.isEmpty(path)) {
      return EMPTY_STRING;
    }
    int idx = path.lastIndexOf(EXTENSION_SPLIT);
    if (-1 == idx) {
      return EMPTY_STRING;
    }
    return path.substring(idx + 1);
  }

  /**
   * Get the file name of the specified absolute path without extension.
   * 
   * @param path
   *          the specified absolute path
   * @return the file name without extension
   */
  public static String getFileNameExcludeExtension(String path) {

    String fileName = null;
    File file = new File(path);

    if (file.isFile()) {
      fileName = path.substring(getPath(path).length());
      fileName = fileName.substring(0, fileName.lastIndexOf(EXTENSION_SPLIT));
      if (fileName.startsWith(FILE_SEPARATOR)) {
        fileName = fileName.substring(1);
      }
    }
    return fileName;
  }

  /**
   * Delete the specified folder unless it's empty.
   * 
   * @param folderName
   *          the specified folder
   */
  public static void deleteEmptyFolder(String folderName) {
    File folder = new File(folderName);
    if (!folder.exists()) {
      return;
    }
    folder.delete();
  }

  /**
   * Delete the specified file or folder (folder contains all sub folder and sub
   * file).
   * 
   * @param filename
   *          the specified absolute path
   */
  public static void deleteFile(String filename) {
    File folder = new File(filename);
    if (!folder.exists()) {
      return;
    }
    if (folder.isDirectory()) {
      File[] list = folder.listFiles();
      for (File f : list) {
        deleteFile(f.getAbsolutePath());
      }
      folder.delete();
    } else if (folder.isFile()) {
      folder.delete();
    }
  }

  /**
   * Get all sub file name in the specified folder in sort.
   * 
   * @param folderPath
   *          the specified folder
   * @return all sub file name in the specified folder in sort
   */
  public static List<String> getFileNameList(String folderPath) {

    List<String> fileList = null;
    File d = new File(folderPath);
    File[] list = d.listFiles();

    if (list.length > 0) {
      fileList = new ArrayList<String>();
      int length = list.length;
      for (int i = 0; i < length; i++) {
        if (list[i].isFile()) {
          fileList.add(list[i].getName());
        }
      }
      Collections.sort(fileList);
    }
    return fileList;
  }

  /**
   * Create a zip file.
   * 
   * @param outputZipFileName
   *          output zip file name
   * @param files
   *          input zip file(s)
   * @throws IOException
   *           on error
   */
  public static void createZip(String outputZipFileName, String... files) throws IllegalArgumentException, IOException {
    if (null == outputZipFileName || null == files) {
      throw new IllegalArgumentException(Messages.getString(Message.PARA_IS_NULL));
    }
    int fileCount = files.length;
    ZipOutputStream out = null;
    FileOutputStream fout = null;
    try {
      fout = new FileOutputStream(outputZipFileName);
      out = new ZipOutputStream(fout);
      for (int i = 0; i < fileCount; i++) {
        File f = new File(files[i]);
        zip(out, f, EMPTY_STRING);
      }
      out.flush();
    } finally {
      if (null != out) {
        out.close();
      }
      if (null != fout) {
        fout.close();
      }
    }
  }

  /**
   * Zip a file.
   * 
   * @param zOut
   *          ZipOutputStream
   * @param file
   *          input file
   * @param label
   *          label
   * @throws IOException
   *           on error
   */
  private static void zip(ZipOutputStream zOut, File file, String label) throws IllegalArgumentException, IOException {
    if (null == zOut || null == file) {
      throw new IllegalArgumentException(Messages.getString(Message.PARA_IS_NULL));
    }
    FileInputStream fin = null;
    try {
      if (file.isDirectory()) {
        label += file.getName() + LINUX_FILE_SEPARATE;
        ZipEntry zipEntry = new ZipEntry(label);
        zOut.putNextEntry(zipEntry);
        int childrenCount = file.listFiles().length;
        for (int i = 0; i < childrenCount; i++) {
          File child = file.listFiles()[i];
          zip(zOut, child, label);
        }
      } else {
        label += file.getName();
        ZipEntry zipEntry = new ZipEntry(label);
        zOut.putNextEntry(zipEntry);
        fin = new FileInputStream(file);
        byte[] buff = new byte[BUFF_SIZE];
        int cnt = 0;
        while (-1 != (cnt = fin.read(buff))) {
          zOut.write(buff, 0, cnt);
        }
        zOut.flush();
      }
    } finally {
      if (null != fin) {
        fin.close();
      }
      if (null != zOut) {
        zOut.closeEntry();
      }
    }
  }

  /**
   * Unzip a zip file.
   * 
   * @param zipFile
   *          zip file path
   * @param outputFile
   *          output file
   * @throws IOException
   *           on error
   */
  public static void unzip(String zipFile, String outputFile) throws IllegalArgumentException, IOException {
    if (null == zipFile || null == outputFile) {
      throw new IllegalArgumentException(Messages.getString(Message.PARA_IS_NULL));
    }
    ZipInputStream zin = null;
    FileInputStream fin = null;
    FileOutputStream fout = null;
    try {
      fin = new FileInputStream(zipFile);
      zin = new ZipInputStream(fin);
      String base = fixFolderName(outputFile);
      createFolder(base);
      ZipEntry zipEntry = null;
      while (null != (zipEntry = zin.getNextEntry())) {
        String file = base + zipEntry.getName();
        if (zipEntry.isDirectory()) {
          createFolder(fixFolderName(file));
        } else {
          createFolder(file);
          fout = new FileOutputStream(file);
          byte[] buff = new byte[BUFF_SIZE];
          int count = 0;
          while (-1 != (count = zin.read(buff))) {
            fout.write(buff, 0, count);
          }
          fout.flush();
        }
        zin.closeEntry();
      }
    } finally {
      if (null != zin) {
        zin.close();
      }
      if (null != fin) {
        fin.close();
      }
      if (null != fout) {
        fout.close();
      }
    }
  }

  /**
   * Get the relative path from a specified absolute path in the base path.
   * 
   * @param fullPath
   *          a specified absolute path
   * @param basePath
   *          the base path
   * @return the relative path
   * @throws Exception
   *           Exception
   */
  public static String getRelativePath(String fullPath, String basePath) throws IOException {
    String relativePath = EMPTY_STRING;

    fullPath = new File(fullPath).getCanonicalPath();
    basePath = new File(basePath).getCanonicalPath();

    String[] splitPath = replaceFileSeparate(fullPath).split(LINUX_FILE_SEPARATE);
    String[] splitBasePath = replaceFileSeparate(basePath).split(LINUX_FILE_SEPARATE);

    int length = splitBasePath.length;
    int splitLength = splitPath.length;
    int i = 0;
    for (; i < length; i++) {
      if (splitPath[i].equalsIgnoreCase(splitBasePath[i])) {
        continue;
      } else {
        break;
      }
    }

    if (i >= length) {
      if (splitLength >= i) {
        for (int j = i; j < splitLength; j++) {
          relativePath += splitPath[j] + LINUX_FILE_SEPARATE;
        }
        relativePath = relativePath.substring(0, relativePath.length() - 1);
      }
    } else {
      int remain = length - i;
      for (int j = 0; j < remain; j++) {
        relativePath += PARENT_DIR;
      }
      for (int j = i; j < splitLength; j++) {
        relativePath += splitPath[j] + LINUX_FILE_SEPARATE;
      }
      relativePath = relativePath.substring(0, relativePath.length() - 1);
    }
    return CURRENT_DIR + relativePath;
  }

  /**
   * Check whether the specified file exists.
   * 
   * @param filePath
   *          the absolute file path
   * @return whether the file is exist
   */
  public static boolean isFileExist(String filePath) {
    if (StringUtil.isEmpty(filePath)) {
      return false;
    }
    File file = new File(filePath);
    if (file.exists() && file.isFile()) {
      return true;
    }
    return false;
  }

  /**
   * Check whether the specified folder exists.
   * 
   * @param folderPath
   *          the absolute folder path
   * @return whether the folder is exist
   */
  public static boolean isFolderExist(String folderPath) {
    if (StringUtil.isEmpty(folderPath)) {
      return false;
    }
    File file = new File(folderPath);
    if (file.exists() && file.isDirectory()) {
      return true;
    }
    return false;
  }

  /**
   * Read a specified file to a list by line. <b>The input stream is not
   * closed.</b>
   * 
   * @param fileName
   *          a specified file
   * @param charset
   *          the charset of the specified file
   * @return the list contains all the file contexts
   * @throws Exception
   *           Exception
   */
  public static List<String> readFileByLine(InputStream is, boolean skipBlankLine, String charset) throws IOException {
    List<String> lines = new ArrayList<String>();
    Reader isReader = null;
    BufferedReader br = null;
    try {
      isReader = new InputStreamReader(is, charset);
      br = new BufferedReader(isReader);

      String line = null;
      while (null != (line = br.readLine())) {
        if (skipBlankLine && StringUtil.isEmpty(line)) {
          continue;
        }
        lines.add(line);
      }

      return lines;
    } finally {
      if (null != br) {
        br.close();
      }
      if (null != isReader) {
        isReader.close();
      }
    }
  }
  
  /**
   * Read a specified file to a list by line (ignore the blank line).
   * 
   * @param fileName
   *          a specified file
   * @return the list contains all the file contexts
   * @throws IOException
   *           on error
   */
  public static List<String> readFileByLine(String fileName) throws IOException {
    return readFileByLine(fileName, DEFAULT_CHARSET);
  }

  /**
   * Read a specified file to a list by line (ignore the blank line).
   * 
   * @param fileName
   *          a specified file
   * @param charset
   *          the charset of the specified file
   * @return the list contains all the file contexts
   * @throws IOException
   *           on error
   */
  public static List<String> readFileByLine(String fileName, String charset) throws IOException {
    return readFileByLine(fileName, true, charset);
  }

  /**
   * Read a specified file to a list by line (ignore the blank line).
   * 
   * @param fileName
   *          a specified file
   * @throws IOException
   *           on error
   */
  public static String readFile2String(String fileName) throws IOException {
    List<String> list = readFileByLine(fileName, DEFAULT_CHARSET);
    StringBuilder sb = new StringBuilder();
    if (CollectionUtil.isNotEmpty(list)) {
      for (String line : list) {
        sb.append(line);
        sb.append(LF);
      }
    }
    return sb.toString();
  }

  /**
   * Read a specified file to a list by line (ignore the blank line).
   * 
   * @param fileName
   *          a specified file
   * @param skipBlankLine
   *          skip blank line?
   * @throws IOException
   *           on error
   */
  public static String readFile2String(InputStream is, boolean skipBlankLine) throws IOException {
    List<String> list = readFileByLine(is, skipBlankLine, DEFAULT_CHARSET);
    StringBuilder sb = new StringBuilder();
    if (CollectionUtil.isNotEmpty(list)) {
      for (String line : list) {
        sb.append(line);
        sb.append(LF);
      }
    }
    return sb.toString();
  }

  /**
   * Read a specified file to a list by line (ignore the blank line).
   * 
   * @param fileName
   *          a specified file
   * @throws IOException
   *           on error
   */
  public static String readFile2String(InputStream is) throws IOException {
    return readFile2String(is, true);
  }

  /**
   * Read a specified file to a list by line.
   * 
   * @param fileName
   *          a specified file
   * @param skipBlankLine
   *          whether ignore the blank line
   * @param charset
   *          the charset of the specified file
   * @return　the list contains all the file contexts
   * @throws IOException
   *           on error
   */
  public static List<String> readFileByLine(String fileName, boolean skipBlankLine, String charset) throws IOException {
    InputStream is = null;
    try {
      is = new FileInputStream(fileName);
      return readFileByLine(is, skipBlankLine, charset);
    } finally {
      if (null != is) {
        is.close();
      }
    }
  }

  /**
   * Write out a file with the specified bytes.
   * 
   * @param bytes
   *          the specified bytes
   * @param fileName
   *          output file path
   * @throws IOException
   *           on error
   */
  public static void write2file(byte[] bytes, String fileName) throws IOException {
    OutputStream os = null;
    try {
      os = new FileOutputStream(fileName);
      os.write(bytes);
      os.flush();
    } finally {
      if (null != os) {
        os.close();
      }
    }
  }

  /**
   * Write out a file with the specified lines.
   * 
   * @param lines
   *          the specified lines
   * @param fileName
   *          output file path
   * @param append
   *          append mode on/off
   * @throws IOException
   *           on error
   */
  public static void writeLines2file(List<String> lines, String fileName, boolean append) throws IOException {
    writeLines2file(lines, fileName, append, DEFAULT_CHARSET);
  }

  /**
   * Write out a file with the specified line.
   * 
   * @param line
   *          the specified line
   * @param fileName
   *          output file path
   * @param append
   *          append mode on/off
   * @throws IOException
   *           on error
   */
  public static void writeLine2file(String line, String fileName, boolean append) throws IOException {
    writeLine2file(line, fileName, append, DEFAULT_CHARSET);
  }

  /**
   * Write out a file with the specified line.
   * 
   * @param line
   *          the specified line
   * @param fileName
   *          output file path
   * @param append
   *          append mode on/off
   * @param encoding
   *          the encoding
   * @throws IOException
   *           on error
   */
  public static void writeLine2file(String line, String fileName, boolean append, String encoding) throws IOException {
    List<String> lines = new ArrayList<String>();
    lines.add(line);
    writeLines2file(lines, fileName, append, encoding);
  }

  /**
   * Write out a file with the specified lines.
   * 
   * @param lines
   *          the specified lines
   * @param fileName
   *          output file path
   * @param append
   *          append mode on/off
   * @param encoding
   *          the encoding
   * @throws IOException
   *           on error
   */
  public static void writeLines2file(List<String> lines, String fileName, boolean append, String encoding)
      throws IOException {
    if (null == lines) {
      return;
    }
    OutputStream os = null;
    OutputStreamWriter writer = null;
    try {
      os = new FileOutputStream(fileName, append);
      writer = new OutputStreamWriter(os, encoding);
      boolean first = true;
      for (String line : lines) {
        if (!first) {
          writer.append(StringUtil.newline());
        } else {
          first = false;
        }
        writer.append(line.trim());
      }
    } finally {
      if (null != writer) {
        writer.close();
      }
      if (null != os) {
        os.close();
      }
    }
  }

  /**
   * List all absolute file paths in specified folder.
   * 
   * @param folder
   *          the folder
   * @param searchDeep
   *          search deep or not
   * @return a list of all absolute file paths
   * @throws Exception
   */
  public static List<String> listAllAbsoluteFilePath(String folder, boolean searchDeep) throws Exception {
    if (StringUtil.isEmpty(folder)) {
      throw new IllegalArgumentException(Messages.getString(Message.FOLDER_NAME_IS_NULL));
    }
    File parentFolder = new File(folder);
    if (!parentFolder.exists()) {
      throw new FileNotFoundException(Messages.getString(Message.FILE_CANNOT_FOUND, folder));
    }
    if (!parentFolder.isDirectory()) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FILE_IS_FOLDER, folder));
    }
    List<String> rtn = new ArrayList<String>();
    File[] children = parentFolder.listFiles();
    for (File child : children) {
      if (child.isDirectory()) {
        if (searchDeep) {
          rtn.addAll(listAllAbsoluteFilePath(child.getAbsolutePath(), true));
        } else {
          rtn.add(child.getAbsolutePath());
        }
      } else {
        rtn.add(child.getAbsolutePath());
      }
    }
    return rtn;
  }

  /**
   * Zip a set of files and add a manifest which contains MD5 check.
   * 
   * @param packagePath
   *          the output package path
   * @param rootFolder
   *          the root folder of the set of files
   * @param relatedPath
   *          the files related path to root folder
   * @throws Exception
   */
  public static void zipWithManifest(String packagePath, String rootFolder, String... relatedPath) throws Exception {
    rootFolder = FileUtil.fixFolderName(rootFolder);
    // create manifest file
    String manifestPath = rootFolder + MANIFEST_FILE_NAME;

    List<String> manifestContents = new ArrayList<String>();

    for (String input : relatedPath) {
      InputStream is = null;
      try {
        File f = new File(rootFolder + input);
        String md5;
        // generate md5
        if (f.isDirectory()) {
          md5 = EncryptUtil.encryptMD5(input.getBytes(DEFAULT_CHARSET));
        } else {
          is = new FileInputStream(f);
          byte[] content = IOUtil.toByteArray(is);
          md5 = EncryptUtil.encryptMD5(content);
        }
        // add information to manifest file
        manifestContents.add(input + TAB + md5);
      } finally {
        // have to close the stream
        if (null != is) {
          is.close();
        }
      }
    }
    // write information to manifest file
    writeLines2file(manifestContents, manifestPath, false);
    // create the zip file

    FileUtil.createZip(packagePath, (String[]) ObjectUtil.toObjectArray(CollectionUtil
        .collection2Array(listAllAbsoluteFilePath(rootFolder, false))));
  }

  /**
   * Unzip a file which contains the manifest file.
   * 
   * @param packagePath
   *          the package path
   * @param destFolderPath
   *          the dest folder path
   * @return a list of all MD5 check for the set of files
   * @throws Exception
   */
  public static Map<String, CheckResult> unzipWithManifest(String packagePath, String destFolderPath) throws Exception {
    Map<String, CheckResult> rtn = new LinkedHashMap<String, CheckResult>();
    // make sure the path end with /
    destFolderPath = FileUtil.fixFolderName(destFolderPath);
    String manifestPath = destFolderPath + MANIFEST_FILE_NAME;
    // unzip file
    FileUtil.unzip(packagePath, destFolderPath);
    // read manifest file
    List<String> lines = FileUtil.readFileByLine(manifestPath, DEFAULT_CHARSET);
    Map<String, String> map = new LinkedHashMap<String, String>();
    for (String line : lines) {
      String[] split = line.split(TAB);
      if (2 == split.length) {
        map.put(split[0], split[1]);
      }
    }

    // check md5
    for (String path : map.keySet()) {
      InputStream is = null;
      try {
        File f = new File(destFolderPath + path);
        String md5;
        if (f.isDirectory()) {
          md5 = EncryptUtil.encryptMD5(path.getBytes(DEFAULT_CHARSET));
        } else {
          is = new FileInputStream(destFolderPath + path);
          byte[] content = IOUtil.toByteArray(is);
          md5 = EncryptUtil.encryptMD5(content);
        }
        String orignalMd5 = map.get(path);
        CheckResult result = new CheckResult(path, destFolderPath + path, orignalMd5, md5);
        rtn.put(path, result);
      } finally {
        if (null != is) {
          is.close();
        }
      }
    }
    return rtn;
  }
}
