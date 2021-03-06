package com.elminster.common.util;

import com.elminster.common.constants.Constants.CharacterConstants;
import com.elminster.common.constants.Constants.EncodingConstants;
import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.util.Messages.Message;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.elminster.common.constants.Constants.StringConstants.*;

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

  // @formatter:off
  /** the illegal filename characters. */
  private static final char[] ILLEGAL_FILENAME_CHARACTERS = {
      CharacterConstants.SLASH,
      CharacterConstants.BACKSLASH,
      CharacterConstants.COLON,
      CharacterConstants.STAR,
      CharacterConstants.QUESTION,
      CharacterConstants.VERTICAL_BAR,
      CharacterConstants.GREAT_THAN,
      CharacterConstants.LESS_THAN,
      CharacterConstants.DOUBLE_QUOTE,
      CharacterConstants.CR,
      CharacterConstants.LF
  };
  // @formatter:on

  /**
   * Get the system temporary folder.
   *
   * @return the system temporary folder path
   */
  public static String getSystemTemporaryFolder() {
    return fixDirectoryName(TEMPORARY_DIR);
  }

  /**
   * Generate temporary folder by UUID.
   *
   * @param parent
   *     the parent folder (if it is not set, the parent folder will be the system temporary folder).
   * @return the temporary folder path
   */
  public static String generateTemporaryFolderByUUID(String parent) {
    String base = null == parent ? getSystemTemporaryFolder() : parent;
    UUID uuid = UUID.randomUUID();
    return fixDirectoryName(fixDirectoryName(base) + uuid.toString());
  }

  /**
   * Generate temporary file by UUID.
   *
   * @param parent
   *     the parent folder (if it is not set, the parent folder will be the system temporary folder).
   * @return the temporary file path
   */
  public static String generateTemporaryFileByUUID(String parent) {
    String base = null == parent ? getSystemTemporaryFolder() : parent;
    UUID uuid = UUID.randomUUID();
    return fixDirectoryName(base) + uuid.toString();
  }

  /**
   * Generate the temporary file by timestamp.
   *
   * @param parent
   *     the parent folder (if it is not set, the parent folder will be the system temporary folder).
   * @param prefix
   *     the prefix of the temporary file
   * @param suffix
   *     the suffix of the temporary file
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
   * Get random String only contains [0-9][a-z][A-Z].
   *
   * @param length
   *     the String length
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
   * Create directory using the specified absolute path (create the parent fixDirectoryName if necessary)
   *
   * @param dir
   *     the absolute path
   */
  public static void createDirectory(String dir) {
    Assert.notNull(dir);
    String name = fixDirectoryName(dir);
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
   * Create a new file (create the parent folder if necessary).
   *
   * @param fileName
   *     the file name
   * @throws IOException
   *     on error
   */
  public static void createNewFile(String fileName) throws IOException {
    createDirectory(fileName);
    File file = new File(fileName);
    file.createNewFile();
  }

  /**
   * Move the specified file to another file (create folder for move to file if necessary)
   *
   * @param srcFile
   *     the file to move
   * @param destFile
   *     the file to move to
   */
  public static void moveFile(String srcFile, String destFile) throws IllegalArgumentException {
    if (!isFileExist(srcFile)) {
      throw new IllegalArgumentException(Messages.getString(Message.SOURCE_FILE_ISNT_EXIST, srcFile));
    }
    File src = new File(srcFile);
    createDirectory(destFile);
    File dest = new File(destFile);
    src.renameTo(dest);
  }

  /**
   * Copy the specified file to another file (create folder for move to file if necessary)
   *
   * @param src
   *     the file to copy
   * @param dest
   *     the file to copy to
   * @throws IOException
   *     on error
   */
  public static void copyFile(String src, String dest) throws IllegalArgumentException, IOException {
    Assert.notNull(src);
    Assert.notNull(dest);
    if (!isFileExist(src)) {
      throw new IllegalArgumentException(String.format("src file [%s] does not exist or is a directory.", src));
    }
    File fSrc = new File(src);
    createDirectory(dest);
    File fDest = new File(dest);
    FileUtils.copyFile(fSrc, fDest);
  }

  /**
   * Copy the specified file to another file (create folder for move to file if necessary)
   *
   * @param src
   *     the inputString to copy
   * @param dest
   *     the file to copy to
   * @throws Exception
   *     Exception
   */
  public static void copyFile(InputStream src, String dest) throws Exception {
    Assert.notNull(src);
    createDirectory(dest);
    try (OutputStream out = new FileOutputStream(dest)) {
      IOUtils.copy(src, out);
    }
  }

  /**
   * Fix the specified directory name what should end with a file separate
   *
   * @param dir
   *     the specified directory name
   * @return the directory name which end with a file separate
   */
  public static String fixDirectoryName(String dir) throws IllegalArgumentException {
    if (StringUtil.isEmpty(dir)) {
      throw new IllegalArgumentException(Messages.getString(Message.FOLDER_NAME_IS_NULL));
    }
    String rst = replaceFileSeparate(dir);
    if (rst.endsWith(LINUX_FILE_SEPARATE)) {
      return rst;
    } else {
      return rst + LINUX_FILE_SEPARATE;
    }
  }

  /**
   * Copy the specified file to a folder (create the copy to folder if necessary)
   *
   * @param src
   *     the file to copy
   * @param dest
   *     the copy to folder
   * @throws IOException
   *     on error
   */
  public static void copyFile2Directory(String src, String dest) throws IllegalArgumentException, IOException {
    Assert.notNull(src);
    Assert.notNull(dest);
    if (!isFileExist(src)) {
      throw new IllegalArgumentException(String.format("src file [%s] does not exist or is a directory.", src));
    }
    String fixedFolderName = fixDirectoryName(dest);
    createDirectory(dest);
    String destFileName = fixedFolderName + getFileName(src);
    copyFile(src, destFileName);
  }

  /**
   * Copy the specified folder to an folder (create the copy to folder if necessary).
   *
   * @param src
   *     the folder to copy
   * @param dest
   *     the copy to folder
   * @throws IOException
   *     on error
   */
  public static void copyDirectory(final String src, final String dest) throws IllegalArgumentException, IOException {
    Assert.notNull(src);
    Assert.notNull(dest);
    if (!isDirectoryExist(src)) {
      throw new IllegalArgumentException(String.format("src directory [%s] does not exist or is a file.", src));
    }
    createDirectory(dest);
    File file = new File(src);
    File[] srcFiles = file.listFiles();
    int length = srcFiles.length;
    for (int i = 0; i < length; i++) {
      File f = srcFiles[i];
      if (file.isFile()) {
        copyFile2Directory(f.getAbsolutePath(), dest);
      } else if (file.isDirectory()) {
        copyDirectory(f.getAbsolutePath(), dest + "/" + f.getName());
      }
    }
  }

  /**
   * Convert all file separate of the specified file name to "/"
   *
   * @param fileName
   *     the specified file name
   * @return converted file name
   */
  public static String replaceFileSeparate(final String fileName) {
    if (null == fileName) {
      return null;
    }
    return fileName.replaceAll(WINDOW_FILE_SEPARATE, LINUX_FILE_SEPARATE);
  }

  /**
   * Get the directory name of the specified absolute path
   *
   * @param path
   *     the specified absolute path
   * @return the directory's absolute path of the path
   */
  public static String getParentDirectory(final String path) {
    Assert.notNull(path);
    String value = EMPTY_STRING;
    String rp = replaceFileSeparate(path);
    int idx = rp.lastIndexOf(LINUX_FILE_SEPARATE);
    if (idx > -1) {
      value = path.substring(0, idx);
    }
    return value;
  }

  /**
   * Get the file name of the specified absolute path.
   *
   * @param path
   *     the specified absolute path
   * @return the file name
   */
  public static String getFileName(final String path) {
    Assert.notNull(path);
    String fileName = EMPTY_STRING;
    String rp = replaceFileSeparate(path);
    int idx = rp.lastIndexOf(LINUX_FILE_SEPARATE);
    if (idx > -1) {
      fileName = path.substring(idx + 1);
    }
    return fileName;
  }

  /**
   * Get the file format (extension without <code>.</code>) of the specified absolute path.
   *
   * @param filePath
   *     the specified absolute path
   * @return the file format
   */
  public static String getFileFormat(String filePath) {
    Assert.notNull(filePath);
    int idx = filePath.lastIndexOf(EXTENSION_SPLIT);
    if (-1 == idx) {
      return EMPTY_STRING;
    }
    return filePath.substring(idx + 1);
  }

  /**
   * Get the file extension (.xxx) of the specified absolute path.
   *
   * @param filePath
   *     the specified absolute path
   * @return the file extension
   */
  public static String getFileExtension(String filePath) {
    Assert.notNull(filePath);
    if (StringUtil.isEmpty(filePath)) {
      return EMPTY_STRING;
    }
    int idx = filePath.lastIndexOf(EXTENSION_SPLIT);
    if (-1 == idx) {
      return EMPTY_STRING;
    }
    return filePath.substring(idx);
  }

  /**
   * Get the file name of the specified absolute path without extension.
   *
   * @param absolutePath
   *     the specified absolute path
   * @return the file name without extension
   */
  public static String getFileNameExcludeExtensionByAbsolutePath(String absolutePath) {
    File file = new File(absolutePath);
    return getFileNameExcludeExtension(file);
  }

  /**
   * Get the file name of the specified filename without extension.
   *
   * @param filename
   *     the specified filename
   * @return the file name without extension
   */
  public static String getFileNameExcludeExtension(String filename) {
    Assert.notNull(filename);
    int idx = filename.lastIndexOf(EXTENSION_SPLIT);
    if (idx < 0) {
      return EMPTY_STRING;
    } else {
      return filename.substring(0, idx);
    }
  }

  /**
   * Get the file name of the specified absolute path without extension.
   *
   * @param file
   *     the specified file
   * @return the file name without extension
   * @throws IllegalArgumentException
   *     on file is invalid.
   */
  public static String getFileNameExcludeExtension(File file) {
    Assert.notNull(file);
    if (file.isFile()) {
      String fileName = file.getName();
      return getFileNameExcludeExtension(fileName);
    } else {
      throw new IllegalArgumentException(String.format("File: [%s] is not a valid File.", file));
    }
  }

  /**
   * Delete the specified folder unless it's empty.
   *
   * @param folderName
   *     the specified folder
   */
  public static void deleteEmptyFolder(String folderName) {
    File folder = new File(folderName);
    if (!folder.exists()) {
      return;
    }
    folder.delete();
  }

  /**
   * Delete the specified file or directory (folder contains all sub directory and sub file).
   *
   * @param filename
   *     the specified absolute path
   */
  public static void deleteFile(String filename) {
    File dir = new File(filename);
    if (!dir.exists()) {
      return;
    }
    if (dir.isDirectory()) {
      File[] list = dir.listFiles();
      for (File f : list) {
        deleteFile(f.getAbsolutePath());
      }
      dir.delete();
    } else if (dir.isFile()) {
      dir.delete();
    }
  }

  /**
   * Get all sub file name in the specified directory.
   *
   * @param dir
   *     the specified directory
   * @return all sub file name in the specified folder in sort
   */
  public static List<String> getFileNameList(String dir) {
    List<String> fileList = null;
    File d = new File(dir);
    File[] list = d.listFiles();

    if (list.length > 0) {
      fileList = new ArrayList<>();
      int length = list.length;
      for (int i = 0; i < length; i++) {
        if (list[i].isFile()) {
          fileList.add(list[i].getName());
        }
      }
    }
    return fileList;
  }

  /**
   * Create a zip file.
   *
   * @param outputZipFileName
   *     output zip file name
   * @param files
   *     input zip file(s)
   * @throws IOException
   *     on error
   */
  public static void createZip(String outputZipFileName, String... files) throws IllegalArgumentException, IOException {
    if (null == outputZipFileName || null == files) {
      throw new IllegalArgumentException(Messages.getString(Message.PARA_IS_NULL));
    }
    int fileCount = files.length;
    try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputZipFileName))){
      for (int i = 0; i < fileCount; i++) {
        File f = new File(files[i]);
        zip(out, f, EMPTY_STRING);
      }
      out.flush();
    }
  }

  /**
   * Zip a file.
   *
   * @param zOut
   *     ZipOutputStream
   * @param file
   *     input file
   * @param label
   *     label
   * @throws IOException
   *     on error
   */
  private static void zip(ZipOutputStream zOut, File file, String label) throws IllegalArgumentException, IOException {
    Assert.notNull(zOut);
    Assert.notNull(file);
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
   *     zip file path
   * @param outputFile
   *     output file
   * @throws IOException
   *     on error
   */
  public static void unzip(String zipFile, String outputFile) throws IllegalArgumentException, IOException {
    Assert.notNull(zipFile);
    ZipInputStream zin = null;
    FileInputStream fin = null;
    FileOutputStream fout = null;
    try {
      fin = new FileInputStream(zipFile);
      zin = new ZipInputStream(fin);
      String base = fixDirectoryName(outputFile);
      createDirectory(base);
      ZipEntry zipEntry = null;
      while (null != (zipEntry = zin.getNextEntry())) {
        String file = base + zipEntry.getName();
        if (zipEntry.isDirectory()) {
          createDirectory(fixDirectoryName(file));
        } else {
          createDirectory(file);
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
   *     a specified absolute path
   * @param basePath
   *     the base path
   * @return the relative path
   * @throws Exception
   *     Exception
   */
  public static String getRelativePath(String fullPath, String basePath) throws IOException {
    Assert.notNull(fullPath);
    Assert.notNull(basePath);
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
   *     the absolute file path
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
   * Check whether the specified directory exists.
   *
   * @param dir
   *     the absolute directory path
   * @return whether the directory is exist
   */
  public static boolean isDirectoryExist(String dir) {
    if (StringUtil.isEmpty(dir)) {
      return false;
    }
    File file = new File(dir);
    if (file.exists() && file.isDirectory()) {
      return true;
    }
    return false;
  }

  /**
   * Read a specified file to a list by line. <b>The input stream is not closed.</b>
   *
   * @param is
   *     a specified file
   * @param charset
   *     the charset of the specified file
   * @return the list contains all the file contexts
   * @throws Exception
   *     Exception
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
   *     a specified file
   * @return the list contains all the file contexts
   * @throws IOException
   *     on error
   */
  public static List<String> readFileByLine(String fileName) throws IOException {
    return readFileByLine(fileName, DEFAULT_CHARSET);
  }

  /**
   * Read a specified file to a list by line (ignore the blank line).
   *
   * @param fileName
   *     a specified file
   * @param charset
   *     the charset of the specified file
   * @return the list contains all the file contexts
   * @throws IOException
   *     on error
   */
  public static List<String> readFileByLine(String fileName, String charset) throws IOException {
    return readFileByLine(fileName, false, charset);
  }

  /**
   * Read a specified file to a String (ignore the blank line).
   *
   * @param fileName
   *     a specified file
   * @throws IOException
   *     on error
   */
  public static String readFile2String(String fileName) throws IOException {
    return readFile2String(fileName, true);
  }

  /**
   * Read a specified file to a String.
   *
   * @param fileName
   *     a specified file
   * @param skipBlankLine
   *     skip blank line?
   * @throws IOException
   *     on error
   */
  public static String readFile2String(String fileName, boolean skipBlankLine) throws IOException {
    List<String> list = readFileByLine(fileName, skipBlankLine, DEFAULT_CHARSET);
    return combineLine(list);
  }

  private static String combineLine(List<String> list) {
    StringBuilder sb = new StringBuilder();
    if (CollectionUtil.isNotEmpty(list)) {
      for (String line : list) {
        sb.append(line);
        sb.append(StringUtil.newline());
      }
    }
    return sb.toString();
  }

  /**
   * Read a specified file to a String.
   *
   * @param is
   *     a specified file
   * @param skipBlankLine
   *     skip blank line?
   * @throws IOException
   *     on error
   */
  public static String readFile2String(InputStream is, boolean skipBlankLine) throws IOException {
    List<String> list = readFileByLine(is, skipBlankLine, DEFAULT_CHARSET);
    return combineLine(list);
  }

  /**
   * Read a specified file to a String (ignore the blank line).
   *
   * @param is
   *     a specified file
   * @throws IOException
   *     on error
   */
  public static String readFile2String(InputStream is) throws IOException {
    return readFile2String(is, true);
  }

  /**
   * Read a specified file to a list by line.
   *
   * @param fileName
   *     a specified file
   * @param skipBlankLine
   *     whether ignore the blank line
   * @param charset
   *     the charset of the specified file
   * @return the list contains all the file contexts
   * @throws IOException
   *     on error
   */
  public static List<String> readFileByLine(String fileName, boolean skipBlankLine, String charset) throws IOException {
    try (InputStream is = new FileInputStream(fileName)){
      return readFileByLine(is, skipBlankLine, charset);
    }
  }

  /**
   * Write out a file with the specified bytes.
   *
   * @param bytes
   *     the specified bytes
   * @param fileName
   *     output file path
   * @throws IOException
   *     on error
   */
  public static void write2file(byte[] bytes, String fileName) throws IOException {
    Assert.notNull(bytes);
    createDirectory(fileName);
    File file = new File(fileName);
    FileUtils.writeByteArrayToFile(file, bytes, true);
  }

  /**
   * Write out a file with the specified bytes.
   *
   * @param bytes
   *     the specified bytes
   * @param fileName
   *     output file path
   * @param append
   *     append to exist file?
   * @throws IOException
   *     on error
   */
  public static void write2file(byte[] bytes, String fileName, boolean append) throws IOException {
    Assert.notNull(bytes);
    createDirectory(fileName);
    File file = new File(fileName);
    FileUtils.writeByteArrayToFile(file, bytes, append);
  }

  /**
   * Write out a file with the specified lines.
   *
   * @param lines
   *     the specified lines
   * @param fileName
   *     output file path
   * @param append
   *     append mode on/off
   * @throws IOException
   *     on error
   */
  public static void writeLines2file(List<String> lines, String fileName, boolean append) throws IOException {
    writeLines2file(lines, fileName, append, DEFAULT_CHARSET);
  }

  /**
   * Write out a file with the specified lines.
   *
   * @param lines
   *     the specified lines
   * @param fileName
   *     output file path
   * @param append
   *     append mode on/off
   * @param overwrite
   *     overwrite exist file?
   * @throws IOException
   *     on error
   */
  public static void writeLines2file(List<String> lines, String fileName, boolean append, boolean overwrite) throws IOException {
    if (isFileExist(fileName)) {
      if (overwrite) {
        writeLines2file(lines, fileName, append);
      }
    } else {
      writeLines2file(lines, fileName, append);
    }
  }

  /**
   * Write out a file with the specified line.
   *
   * @param line
   *     the specified line
   * @param fileName
   *     output file path
   * @param append
   *     append mode on/off
   * @throws IOException
   *     on error
   */
  public static void writeLine2file(String line, String fileName, boolean append) throws IOException {
    writeLine2file(line, fileName, append, DEFAULT_CHARSET);
  }

  /**
   * Write out a file with the specified line.
   *
   * @param line
   *     the specified line
   * @param fileName
   *     output file path
   * @param append
   *     append mode on/off
   * @param overwrite
   *     overwrite the exist file?
   * @throws IOException
   *     on error
   */
  public static void writeLine2file(String line, String fileName, boolean append, boolean overwrite) throws IOException {
    if (isFileExist(fileName)) {
      if (overwrite) {
        writeLine2file(line, fileName, append);
      }
    } else {
      writeLine2file(line, fileName, append);
    }
  }

  /**
   * Write out a file with the specified line.
   *
   * @param line
   *     the specified line
   * @param fileName
   *     output file path
   * @param append
   *     append mode on/off
   * @param encoding
   *     the encoding
   * @throws IOException
   *     on error
   */
  public static void writeLine2file(String line, String fileName, boolean append, String encoding) throws IOException {
    List<String> lines = new ArrayList<String>();
    lines.add(line);
    writeLines2file(lines, fileName, append, encoding);
  }

  /**
   * Write out a file with the specified line.
   *
   * @param line
   *     the specified line
   * @param fileName
   *     output file path
   * @param append
   *     append mode on/off
   * @param encoding
   *     the encoding
   * @param overwrite
   *     overwrite the exist file?
   * @throws IOException
   *     on error
   */
  public static void writeLine2file(String line, String fileName, boolean append, String encoding, boolean overwrite) throws IOException {
    if (isFileExist(fileName)) {
      if (overwrite) {
        writeLine2file(line, fileName, append, encoding);
      }
    } else {
      writeLine2file(line, fileName, append, encoding);
    }
  }

  /**
   * Write out a file with the specified lines.
   *
   * @param lines
   *     the specified lines
   * @param fileName
   *     output file path
   * @param append
   *     append mode on/off
   * @param encoding
   *     the encoding
   * @throws IOException
   *     on error
   */
  public static void writeLines2file(List<String> lines, String fileName, boolean append, String encoding) throws IOException {
    if (null == lines) {
      return;
    }
    createDirectory(fileName);
    File file = new File(fileName);
    FileUtils.writeLines(file, lines, encoding, append);
  }

  /**
   * List all absolute file paths in specified directory.
   *
   * @param dir
   *     the directory
   * @param searchDeep
   *     search deep or not
   * @return a list of all absolute file paths
   * @throws Exception
   */
  public static List<String> listAllAbsoluteFilePath(String dir, boolean searchDeep) {
    if (!isDirectoryExist(dir)) {
      throw new IllegalArgumentException(String.format("src directory [%s] does not exist or is a file.", dir));
    }
    File parentDir = new File(dir);
    List<String> rtn = new ArrayList<>();
    File[] children = parentDir.listFiles();
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
   *     the output package path
   * @param rootFolder
   *     the root folder of the set of files
   * @param relatedPath
   *     the files related path to root folder
   * @throws Exception
   */
  public static void zipWithManifest(String packagePath, String rootFolder, String... relatedPath) throws Exception {
    rootFolder = FileUtil.fixDirectoryName(rootFolder);
    // create manifest file
    String manifestPath = rootFolder + MANIFEST_FILE_NAME;

    List<String> manifestContents = new ArrayList<>();

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
          byte[] content = IOUtils.toByteArray(is);
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

    FileUtil.createZip(packagePath, (String[]) ObjectUtil.toObjectArray(CollectionUtil.collection2Array(listAllAbsoluteFilePath(rootFolder, false))));
  }

  /**
   * Unzip a file which contains the manifest file.
   *
   * @param packagePath
   *     the package path
   * @param destFolderPath
   *     the dest folder path
   * @return a list of all MD5 check for the set of files
   * @throws Exception
   */
  public static Map<String, CheckResult> unzipWithManifest(String packagePath, String destFolderPath) throws Exception {
    Map<String, CheckResult> rtn = new LinkedHashMap<>();
    // make sure the path end with /
    destFolderPath = FileUtil.fixDirectoryName(destFolderPath);
    String manifestPath = destFolderPath + MANIFEST_FILE_NAME;
    // unzip file
    FileUtil.unzip(packagePath, destFolderPath);
    // read manifest file
    List<String> lines = FileUtil.readFileByLine(manifestPath, DEFAULT_CHARSET);
    Map<String, String> map = new LinkedHashMap<>();
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
          byte[] content = IOUtils.toByteArray(is);
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

  /**
   * Check if the directory contains the file with specified filename.
   *
   * @param directory
   *     the directory
   * @param filename
   *     the filename
   * @param includedExtension
   *     included extension
   * @param recursion
   *     if recursion
   * @return if the directory contains the file with specified filename
   */
  public static boolean contains(File directory, String filename, boolean includedExtension, boolean recursion) {
    Assert.notNull(directory, "directory cannot be null.");
    Assert.notNull(filename, "filename cannot be null.");
    if (!directory.isDirectory()) {
      throw new IllegalArgumentException(String.format("parameter [%s] is not a directory.", directory.getAbsolutePath()));
    }
    File[] listFiles = directory.listFiles();
    List<File> subDirectories = new LinkedList<>();
    for (File listFile : listFiles) {
      String compareFileName = filename;
      String listFileName = listFile.getName();
      if (!includedExtension) {
        listFileName = getFileNameExcludeExtension(listFile);
        compareFileName = getFileNameExcludeExtension(filename);
      }
      if (listFileName.equals(compareFileName)) {
        return true;
      }
      if (listFile.isDirectory()) {
        subDirectories.add(listFile);
      }
    }
    if (recursion) {
      for (File subDirectory : subDirectories) {
        if (contains(subDirectory, filename, includedExtension, recursion)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Change the file's extension to specified extension.
   *
   * @param fullName
   *     the file's full name.
   * @param extension2ChangeTo
   *     the extension to change to
   * @return the full name with changed extension
   */
  public static String changeFileExtension(String fullName, String extension2ChangeTo) {
    int idx = fullName.lastIndexOf(EXTENSION_SPLIT);
    String extension = extension2ChangeTo.startsWith(EXTENSION_SPLIT) ? extension2ChangeTo : EXTENSION_SPLIT + extension2ChangeTo;
    if (-1 == idx) {
      return fullName + extension;
    } else {
      return fullName.substring(0, idx) + extension;
    }
  }

  /**
   * Get the safe file name by replacing <code>*?<>/\|</code> to <code>#HEX</code> presentation.
   *
   * @param filename
   *     the original filename
   * @return the safe file name
   */
  public static String toSafeFileName(final String filename) {
    StringBuilder safeFileName = new StringBuilder();
    if (null != filename) {
      for (int i = 0; i < filename.length(); i++) {
        char c = filename.charAt(i);
        if (isIllegalFileNameCharacter(c)) {
          safeFileName.append(StringConstants.SHARP);
          safeFileName.append(BinaryUtil.getHex((byte) c));
        } else {
          if (CharacterConstants.SHARP == c) {
            safeFileName.append(StringConstants.SHARP);
          }
          safeFileName.append(c);
        }
      }
    }
    return safeFileName.toString();
  }

  /**
   * Restore the filename from safe filename.
   *
   * @param safeFileName
   *     the safe filename
   * @return the original filename
   */
  public static String restoreFromSafeFileName(final String safeFileName) {
    StringBuilder fileName = new StringBuilder();
    if (null != safeFileName) {
      for (int i = 0; i < safeFileName.length(); i++) {
        char c = safeFileName.charAt(i);
        if (CharacterConstants.SHARP == c) {
          try {
            char nextChar = safeFileName.charAt(++i);
            if (CharacterConstants.SHARP == nextChar) {
              fileName.append(nextChar);
            } else {
              char nextnextChar = safeFileName.charAt(++i);
              fileName.append((char) (Byte.parseByte(String.valueOf(nextChar) + String.valueOf(nextnextChar), 16)));
            }
          } catch (StringIndexOutOfBoundsException | NumberFormatException ex) {
            throw new RuntimeException(String.format("Failed to parse safeFileName [%s].", safeFileName));
          }
        } else {
          fileName.append(c);
        }
      }
    }
    return fileName.toString();
  }

  /**
   * Check if the character is an illegal file name character.
   *
   * @param c
   *     the character
   * @return is an illegal file name character
   */
  private static boolean isIllegalFileNameCharacter(char c) {
    for (char ch : ILLEGAL_FILENAME_CHARACTERS) {
      if (ch == c) {
        return true;
      }
    }
    return false;
  }

  /**
   * Find the specified file from a specified file.
   *
   * @param fileFrom
   *     the specified file
   * @param filenameToFind
   *     the file name need to find
   * @return found file or null for not found
   */
  public static File findFileFrom(final File fileFrom, final String filenameToFind) {
    Assert.notNull(fileFrom);
    Assert.notNull(filenameToFind);
    if (!fileFrom.exists()) {
      throw new IllegalArgumentException(String.format("The search root file [%s] does not exist.", fileFrom));
    }
    if (filenameToFind.equals(fileFrom.getName())) {
      return fileFrom;
    }
    if (fileFrom.isDirectory()) {
      for (File f : fileFrom.listFiles()) {
        File found = findFileFrom(f, filenameToFind);
        if (null != found) {
          return found;
        }
      }
    }
    return null;
  }
}
