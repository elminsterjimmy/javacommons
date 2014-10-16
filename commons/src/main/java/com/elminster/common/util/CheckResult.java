package com.elminster.common.util;

/**
 * MD5 check result.
 * 
 * @author jgu
 * @version 1.0
 */
public class CheckResult {
  
  /** the file name. */
  private String name;
  /** the file path. */
  private String path;
  /** the check result. */
  private Check check;
  /** the original MD5. */
  private String originalMD5;
  /** the checked MD5. */
  private String checkMD5;
  /** is the file directory or not? */
  private boolean isDir;
  /** is the file uploaded? */
  private boolean isUploaded;
  /** the file size. */
  private String size;
  /** the last modified time. */
  private String lastModified;
  
  /**
   * Constructor.
   * @param name the file name
   * @param path the file path
   * @param originalMD5 the original MD5
   * @param checkMD5 the checked MD5
   */
  public CheckResult(String name, String path, String originalMD5, String checkMD5) {
    super();
    this.name = name;
    this.path = path;
    this.originalMD5 = originalMD5;
    this.checkMD5 = checkMD5;
    this.check = originalMD5.equals(checkMD5) ? Check.OK : Check.NG;
    this.isDir = name.endsWith(FileUtil.LINUX_FILE_SEPARATE) ? true : false;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the check
   */
  public Check getCheck() {
    return check;
  }

  /**
   * @return the orignalMD5
   */
  public String getOriginalMD5() {
    return originalMD5;
  }

  /**
   * @return the checkMD5
   */
  public String getCheckMD5() {
    return checkMD5;
  }
  
  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * @return the isDir
   */
  public boolean isDir() {
    return isDir;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return ObjectUtil.buildToStringByReflect(this);
  }

  /**
   * @return the isUploaded
   */
  public boolean isUploaded() {
    return isUploaded;
  }

  /**
   * @param isUploaded the isUploaded to set
   */
  public void setUploaded(boolean isUploaded) {
    this.isUploaded = isUploaded;
  }

  /**
   * @return the size
   */
  public String getSize() {
    return size;
  }

  /**
   * @param size the size to set
   */
  public void setSize(String size) {
    this.size = size;
  }

  /**
   * @return the lastModified
   */
  public String getLastModified() {
    return lastModified;
  }

  /**
   * @param lastModified the lastModified to set
   */
  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }
}