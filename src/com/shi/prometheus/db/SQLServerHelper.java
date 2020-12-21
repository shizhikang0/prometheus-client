package com.shi.prometheus.db;

import com.shi.prometheus.db.cache.SqlServerConnectSetCache;
import com.shi.prometheus.db.callback.SqlCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
public class SQLServerHelper {

    public static final Logger logger = LogManager.getLogger(SQLServerHelper.class);

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    private static SQLServerHelper instance = new SQLServerHelper();

    private SQLServerHelper() {
    }

    public static SQLServerHelper getInstance() {
        return instance;
    }

    private void connect() {
        synchronized (SQLServerHelper.class) {
            try {
                conn = DriverManager.getConnection(SqlServerConnectSetCache.getSQLServerURL(), SqlServerConnectSetCache.SQL_SERVER_USER, SqlServerConnectSetCache.SQL_SERVER_PASSWORD);
                stmt = conn.createStatement();
            } catch (SQLException e) {
                logger.error(e);
            }

        }
    }

    public void executeQuerySql(String sql, SqlCallback sqlCallback) {
        connect();
        try {
            rs = stmt.executeQuery(sql);
            sqlCallback.execute(rs);
            if (rs != null) {
                rs.close();
            }
            rs = null;
        } catch (SQLException e) {
            logger.error(e);
        }
        closeConnect();
    }



    private void closeConnect() {
        if (stmt == null && conn == null) {
            return;
        }
        synchronized (SQLServerHelper.class) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e);
            } finally {
                stmt = null;
                conn = null;
            }
        }
    }
}
