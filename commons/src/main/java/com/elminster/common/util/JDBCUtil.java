package com.elminster.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDBC Utilities
 * 
 * @author Gu
 * @version 1.0
 * 
 */
public abstract class JDBCUtil {

  private static final Logger logger = LoggerFactory.getLogger(JDBCUtil.class);

  public static void closeConnection(Connection conn) {
    if (null != conn) {
      try {
        conn.close();
      } catch (SQLException sqle) {
        logger.debug("Can not close DB Connection");
      } catch (Throwable t) {
        logger.debug("Unexpected exception on closing DB Connection");
      }
    }
  }

  public static void closeStatement(Statement stmt) {
    if (null != stmt) {
      try {
        stmt.close();
      } catch (SQLException sqle) {
        logger.debug("Can not close DB Statement");
      } catch (Throwable t) {
        logger.debug("Unexpected exception on closing DB Statement");
      }
    }
  }

  public static void closeResultSet(ResultSet rs) {
    if (null != rs) {
      try {
        rs.close();
      } catch (SQLException sqle) {
        logger.debug("Can not close DB ResultSet");
      } catch (Throwable t) {
        logger.debug("Unexpected exception on closing DB ResultSet");
      }
    }
  }

  public static String getColumnName(ResultSetMetaData rsmd, int columnIndex) throws SQLException {
    String name = rsmd.getColumnLabel(columnIndex);
    if (name == null || name.length() < 1) {
      name = rsmd.getColumnName(columnIndex);
    }
    return name;
  }
}
