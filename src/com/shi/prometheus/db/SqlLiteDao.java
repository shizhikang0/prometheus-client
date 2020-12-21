package com.shi.prometheus.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.shi.prometheus.RootBootStrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/8
 */
public class SqlLiteDao<T> {

    public static final Logger logger = LogManager.getLogger(SqlLiteDao.class);

    public void createOne(Class<T> clazz, T one) {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(DBUtils.getSqlLiteUrl());
            TableUtils.createTableIfNotExists(connectionSource, clazz);
            Dao<T, String> dao =
                    DaoManager.createDao(connectionSource, clazz);
            dao.createOrUpdate(one);
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            try {
                if (connectionSource != null) {
                    connectionSource.close();
                }
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    public T queryById(Class<T> clazz, String id) {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(DBUtils.getSqlLiteUrl());
            TableUtils.createTableIfNotExists(connectionSource, clazz);
            Dao<T, String> dao =
                    DaoManager.createDao(connectionSource, clazz);
            return dao.queryForId(id);
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            try {
                if (connectionSource != null) {
                    connectionSource.close();
                }
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return null;
    }

    public T queryOneOrderBy(Class<T> clazz, String field, boolean asc) {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(DBUtils.getSqlLiteUrl());
            TableUtils.createTableIfNotExists(connectionSource, clazz);
            Dao<T, String> dao =
                    DaoManager.createDao(connectionSource, clazz);
            return dao.queryBuilder().orderBy(field, asc).limit(1L).queryForFirst();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            try {
                if (connectionSource != null) {
                    connectionSource.close();
                }
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return null;
    }
}
