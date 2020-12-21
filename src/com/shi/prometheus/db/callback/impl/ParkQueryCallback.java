package com.shi.prometheus.db.callback.impl;

import com.shi.prometheus.business.channel.cache.ChannelListCache;
import com.shi.prometheus.business.park.entity.Park;
import com.shi.prometheus.db.callback.SqlCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
public class ParkQueryCallback implements SqlCallback {

    public static final Logger logger = LogManager.getLogger(ParkQueryCallback.class);
    private List<Park> parkList;

    public ParkQueryCallback(List<Park> parkList) {
        this.parkList = parkList;
    }

    @Override
    public void execute(ResultSet rs) {
        try {
            while (rs.next()) {

                Park park = new Park();
                int id = rs.getInt("ID");
                park.setId(id);
                String parkName = rs.getString("ParkName");
                park.setName(parkName);
                String parkNo = rs.getString("Uuid");
                park.setParkNo(parkNo);
                int parkId = rs.getInt("ParkNum");
                park.setParkId(parkId);
                int state = rs.getInt("State");
                park.setState(state);
                parkList.add(park);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
