package com.elminster.common.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * The standard configuration that using configuration files.
 * 
 * @author jgu
 * @version 1.0
 */
public class StandardConfiguration extends CommonConfiguration {

  /** the configuration files. */
  protected String[] configurationFiles;

  /**
   * Constructor.
   * 
   * @param configurationFiles
   *          the configuration files
   */
  public StandardConfiguration(String... configurationFiles) {
    this.configurationFiles = configurationFiles;
    loadResources();
  }
  
  protected StandardConfiguration() {
  }

  /**
   * Load the resource files.
   */
  protected void loadResources() {
    if (null != configurationFiles) {
      Reader reader = null;
      for (String configurationFile : configurationFiles) {
        if (logger.isDebugEnabled()) {
          logger.debug(String.format("Load Configuration file [%s]", configurationFile));
        }
        try {
          reader = new FileReader(configurationFile);
          properties.load(reader);
        } catch (FileNotFoundException e) {
          File file = new File(configurationFile);
          throw new RuntimeException(String.format("Configuration file [%s] is not found at [%s]",
              configurationFile, file.getAbsoluteFile()));
        } catch (IOException e) {
          throw new RuntimeException(e);
        } finally {
          if (null != reader) {
            try {
              reader.close();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
        }
      }
    }
  }
}
