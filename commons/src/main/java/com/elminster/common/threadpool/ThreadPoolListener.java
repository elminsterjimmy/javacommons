package com.elminster.common.threadpool;

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
  void onShutdown();
}
