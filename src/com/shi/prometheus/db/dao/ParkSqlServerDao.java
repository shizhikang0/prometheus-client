package com.shi.prometheus.db.dao;

import com.shi.prometheus.business.park.entity.Park;
import com.shi.prometheus.db.SQLServerHelper;
import com.shi.prometheus.db.callback.impl.ParkQueryCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
public class ParkSqlServerDao {

    public List<Park> getParksFromSqlServer() {
        List<Park> parks = new ArrayList<>();
        SQLServerHelper.getInstance().executeQuerySql("select * from dbo.esp_parkinfo", new ParkQueryCallback(parks));
        return parks;
    }
}
