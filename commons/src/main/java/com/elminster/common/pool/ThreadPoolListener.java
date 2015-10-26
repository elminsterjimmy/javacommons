package com.elminster.common.pool;

import com.elminster.common.listener.Listener;

/**
 * The thread pool listener.
 * 
 * @author jgu
 * @version 1.0
 */
public interface ThreadPoolListener extends Listener {

  /**
   * On thread pool shutdown.
   */
  public void onShutdown();
}
