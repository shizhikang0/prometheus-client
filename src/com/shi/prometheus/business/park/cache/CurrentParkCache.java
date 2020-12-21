package com.shi.prometheus.business.park.cache;

import com.shi.prometheus.business.park.entity.Park;
import com.shi.prometheus.db.dao.ParkSqlServerDao;

import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/11
 */
public class CurrentParkCache {
    private HashMap<Integer, Park> parkHashMap = new HashMap<>();

    private CurrentParkCache() {
    }

    public static CurrentParkCache getInstance() {
        return LazyLoader.instance;
    }

    public void initial() {
        List<Park> parks =
                new ParkSqlServerDao().getParksFromSqlServer();
        if (parks != null) {
            for (Park park : parks) {
                parkHashMap.put(park.getId(), park);
            }
        }
    }

    public Park getParkById(Integer id) {
        return parkHashMap.get(id);
    }

    private static class LazyLoader{
        public static CurrentParkCache instance = new CurrentParkCache();
    }
}
