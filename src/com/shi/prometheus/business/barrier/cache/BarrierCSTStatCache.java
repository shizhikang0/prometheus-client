package com.shi.prometheus.business.barrier.cache;

import com.shi.prometheus.common.BarrierCSTStatusConstants;
import com.shi.prometheus.db.cache.SqlServerConnectSetCache;

/**
 * @Description: 保存最近两分钟的道闸状态数据
 * @Author: shizhikang
 * @Date: 2020/12/9
 */
public class BarrierCSTStatCache {

    public static long QUERY_BARRIER_OVER_TIME = 2 * 60 * 1000;
    //评估容量，平均每秒中道闸返回一个状态信息的话，两分钟120个。每个状态需要4个char。1024足够
    private StringBuilder cstBuffer = new StringBuilder(1024);

    private Long preAppendTime;

    private static BarrierCSTStatCache instance = new BarrierCSTStatCache();

    public static BarrierCSTStatCache getInstance() {
        return instance;
    }

    public synchronized void appendSignCode(int code) {
        cstBuffer.append("-").append(code);
        preAppendTime = System.currentTimeMillis();
    }

    public synchronized void removeSignBeforeLastLMDown() {
        if (System.currentTimeMillis() - preAppendTime > 4*60*1000) {
            cstBuffer = new StringBuilder();
        } else {
            String s = cstBuffer.toString();
            String substring = s.substring(s.lastIndexOf("-" + BarrierCSTStatusConstants.LM_DOWN_CODE));
            StringBuilder tempBuilder = new StringBuilder(1024);
            tempBuilder.append(substring);
            cstBuffer = tempBuilder;
        }
    }

    public boolean overtime() {
        return System.currentTimeMillis() - preAppendTime > QUERY_BARRIER_OVER_TIME ? true : false;
    }
}
