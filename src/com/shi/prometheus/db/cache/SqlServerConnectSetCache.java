package com.shi.prometheus.db.cache;

import com.shi.prometheus.db.SqlLiteDao;
import com.shi.prometheus.db.entity.SqlServerConnectSet;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
public class SqlServerConnectSetCache {
    public static Integer SQL_SERVER_CONNECT_SET_ID = 1;
    public static boolean LOAD_DB_EXCEPTION = false;
    public static String SQL_SERVER_PORT;
    public static String SQL_SERVER_DATABASE;
    public static String SQL_SERVER_USER;
    public static String SQL_SERVER_PASSWORD;

    static {
        SqlServerConnectSet sqlServerConnectSet = new SqlLiteDao<SqlServerConnectSet>().queryById(SqlServerConnectSet.class, SQL_SERVER_CONNECT_SET_ID.toString());
        if (sqlServerConnectSet != null) {
            SQL_SERVER_PORT = sqlServerConnectSet.getPort();
            SQL_SERVER_DATABASE = sqlServerConnectSet.getDataBaseName();
            SQL_SERVER_USER = sqlServerConnectSet.getUser();
            SQL_SERVER_PASSWORD = sqlServerConnectSet.getPassword();
        }
    }

    public static String getSQLServerURL() {
        return "jdbc:sqlserver://localhost:" + SQL_SERVER_PORT + ";DatabaseName=" + SQL_SERVER_DATABASE + ";";
    }
}
