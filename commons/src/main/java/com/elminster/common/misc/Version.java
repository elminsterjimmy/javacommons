package com.elminster.common.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.constants.FileExtensionConstants;
import com.elminster.common.util.FileUtil;
import com.elminster.common.util.ReflectUtil;
import com.elminster.common.util.StringUtil;

/**
 * Utility class to get the version for a class.
 * 
 * @author jinggu
 * @version 1.0
 */
public class Version {
  
  private static final String VERSION = "version";
  private static final String MAVEN_DIR_IN_JAR = "!/META-INF/maven/";
  private static final String MAVEN_POM_PROPERTIES = "pom.properties";

  /**
   * Return the class' version.
   * @param clazz the class
   * @return class' version or <code></code> when the version cannot be found.
   */
  public static String getVersion(Class<?> clazz) {
    // get version from manifest.mf
    String version = clazz.getPackage().getImplementationVersion();
    if (StringUtil.isEmpty(version)) {
      version = clazz.getPackage().getSpecificationVersion();
    }
    if (StringUtil.isEmpty(version)) {
      URL codebase = ReflectUtil.getCodeBase(clazz);
      if (null != codebase) {
        String fileName = codebase.getFile();
        if (FileExtensionConstants.JAR_EXTENSION.equals(FileUtil.getFileNameExcludeExtension(fileName))) {
          // get from pom.properties
          File inJar = new File(fileName + MAVEN_DIR_IN_JAR);
          if (inJar.exists()) {
            File pomProps = FileUtil.findFileFrom(inJar, MAVEN_POM_PROPERTIES);
            try (InputStream is = new FileInputStream(pomProps)) {
              if (is != null) {
                Properties p = new Properties();
                p.load(is);
                version = p.getProperty(VERSION);
              }
            } catch (IOException ioe) {
              // ignore the ioe
              ioe = null;
            }
          }
          
          if (StringUtil.isEmpty(version)) {
            // get version from jar filename
            fileName = FileUtil.getFileNameExcludeExtension(fileName);
            int i = fileName.indexOf(StringConstants.HYPHEN);
            if (i >= 0) {
              fileName = fileName.substring(i + 1);
            }
            while (fileName.length() > 0 && !Character.isDigit(fileName.charAt(0))) {
              i = fileName.indexOf(StringConstants.HYPHEN);
              if (i >= 0) {
                fileName = fileName.substring(i + 1);
              } else {
                break;
              }
            }
            version = fileName;
          }
        }
      }
    }
    return null == version ? StringConstants.EMPTY_STRING : version;
  }
}
