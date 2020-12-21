package com.shi.prometheus.db.dao;

import com.shi.prometheus.business.channel.entity.Channel;
import com.shi.prometheus.db.SQLServerHelper;
import com.shi.prometheus.db.callback.impl.ChannelQueryCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
public class ChannelSqlServerDao {

    public List<Channel> getChannelsFromSqlServer() {
        List<Channel> channelList = new ArrayList<>();
        SQLServerHelper.getInstance().executeQuerySql("select * from dbo.esp_channelinfo", new ChannelQueryCallback(channelList));
        return channelList;
    }
}
