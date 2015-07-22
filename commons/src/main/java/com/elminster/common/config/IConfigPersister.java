package com.elminster.common.config;

import java.io.IOException;

/**
 * The configuration persister for persisting the configurations.
 *  
 * @author jgu
 * @version 1.0
 */
public interface IConfigPersister {

  /**
   * Persist the configuration. 
   * @throws IOException on error
   */
  public void persist() throws IOException;
}
