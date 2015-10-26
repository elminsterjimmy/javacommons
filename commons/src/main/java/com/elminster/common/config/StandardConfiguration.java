package com.elminster.common.config;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * The standard configuration that using configuration files.
 * 
 * @author jgu
 * @version 1.0
 */
public class StandardConfiguration extends CommonConfiguration {

  /** the configuration files. */
  protected String[] configurationFiles;
  /** the properties. */
  protected Properties properties;

  /**
   * Constructor.
   * @param configurationFiles the configuration files
   */
  public StandardConfiguration(String... configurationFiles) {
    this.configurationFiles = configurationFiles;
  }

  /**
   * Load the resource files.
   */
  protected void loadResources() {
    if (null != configurationFiles) {
      Reader reader = null;
      for (String configurationFile : configurationFiles) {
        try {
          reader = new FileReader(configurationFile);
          properties.load(reader);
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
