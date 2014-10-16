package com.elminster.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.elminster.common.exception.CloseException;

/**
 * Utility class for closing resources.
 * 
 * @author jgu
 * @version 1.0
 */
abstract public class CloseUtil {

  private static void closeInputStream0(InputStream is, boolean quiet) {
    if (null != is) {
      try {
        is.close();
      } catch (IOException e) {
        if (!quiet) {
          throw new CloseException(e);
        }
      }
    }
  }
  
  public static void closeInputStreamQuiet(InputStream is) {
    closeInputStream0(is, true);
  }
  
  public static void closeInputStream(InputStream is) throws CloseException {
    closeInputStream0(is, false);
  }
  
  private static void closeOutputStream0(OutputStream out, boolean quiet) {
    if (null != out) {
      try {
        out.close();
      } catch (IOException e) {
        if (!quiet) {
          throw new CloseException(e);
        }
      }
    }
  }
  
  public static void closeOutputStreamQuiet(OutputStream out) {
    closeOutputStream0(out, true);
  }
  
  public static void closeOutputStream(OutputStream out) throws CloseException {
    closeOutputStream0(out, false);
  }
  
  private static void closeReader0(Reader r, boolean quiet) {
    if (null != r) {
      try {
        r.close();
      } catch (IOException e) {
        if (!quiet) {
          throw new CloseException(e);
        }
      }
    }
  }
  
  public static void closeReader(Reader r) throws CloseException {
    closeReader0(r, false);
  }
  
  public static void closeReaderQuiet(Reader r) {
    closeReader0(r, true);
  }
  
  private static void closeWriter0(Writer w, boolean quiet) {
    if (null != w) {
      try {
        w.close();
      } catch (IOException e) {
        if (!quiet) {
          throw new CloseException(e);
        }
      }
    }
  }
  
  public static void closeWriter(Writer w) throws CloseException {
    closeWriter0(w, false);
  }
  
  public static void closeWriterQuiet(Writer w) {
    closeWriter0(w, true);
  }
  
  private static void closeResultSet0(ResultSet rs, boolean quiet) {
    if (null != rs) {
      try {
        rs.close();
      } catch (SQLException e) {
        if (!quiet) {
          throw new CloseException(e);
        }
      }
    }
  }
  
  public static void closeResultSet(ResultSet rs) throws CloseException {
    closeResultSet0(rs, false);
  }
  
  public static void closeResultSetQuiet(ResultSet rs) throws CloseException {
    closeResultSet0(rs, true);
  }
  
  private static void closeStatement0(Statement stat, boolean quiet) {
    if (null != stat) {
      try {
        stat.close();
      } catch (SQLException e) {
        if (!quiet) {
          throw new CloseException(e);
        }
      }
    }
  }
  
  public static void closeStatement(Statement stat) throws CloseException {
    closeStatement0(stat, false);
  }
  
  public static void closeStatementQuiet(Statement stat) {
    closeStatement0(stat, true);
  }
  
  private static void closeConnection0(Connection conn, boolean quiet) {
    if (null != conn) {
      try {
        conn.close();
      } catch (SQLException e) {
        if (!quiet) {
          throw new CloseException(e);
        }
      }
    }
  }
  
  public static void closeConnection(Connection conn) throws CloseException {
    closeConnection0(conn, false);
  }
  
  public static void closeConnectionQuiet(Connection conn) {
    closeConnection0(conn, true);
  }
}
