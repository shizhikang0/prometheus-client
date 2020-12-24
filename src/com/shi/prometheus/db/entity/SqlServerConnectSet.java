package com.shi.prometheus.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/16
 */
@DatabaseTable(tableName = "sql_server_connect_set")
public class SqlServerConnectSet {
    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    @DatabaseField(columnName = "port")
    private String port;

    @DatabaseField(columnName = "database_name")
    private String dataBaseName;

    @DatabaseField(columnName = "user")
    private String user;

    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = "status")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
