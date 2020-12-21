package com.shi.prometheus.business.channel.cache;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.shi.prometheus.business.channel.entity.Channel;
import com.shi.prometheus.db.DBUtils;
import com.shi.prometheus.db.callback.impl.ChannelQueryCallback;
import com.shi.prometheus.db.dao.ChannelSqlServerDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
public class ChannelListCache {
    public static final Logger logger = LogManager.getLogger(ChannelListCache.class);
    private static ChannelListCache instance = new ChannelListCache();

    private List<Channel> channelList;

    private ChannelListCache() {
        ChannelSqlServerDao channelSqlServerDao = new ChannelSqlServerDao();
        channelList = channelSqlServerDao.getChannelsFromSqlServer();
    }

    public static ChannelListCache getInstance() {
        return instance;
    }

    public List<Channel> getChannelList() {
        return channelList;
    }

    /**
     * 将SqlServer中的道口信息保存到sqlite中，并保存到内存中
     */
    public void initial() {
        ConnectionSource connectionSource = null;
        try {
            String sqlLiteUrl = DBUtils.getSqlLiteUrl();
            connectionSource = new JdbcConnectionSource(sqlLiteUrl);
            Dao<Channel, String> channelDao =
                    DaoManager.createDao(connectionSource, Channel.class);
            TableUtils.createTableIfNotExists(connectionSource, Channel.class);
            List<Channel> channelList = ChannelListCache.getInstance().getChannelList();
            for (Channel channel : channelList) {
                channelDao.createOrUpdate(channel);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }
    }
}
