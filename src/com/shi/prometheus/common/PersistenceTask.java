package com.shi.prometheus.common;

import com.shi.prometheus.business.barrier.cache.BarrierCSTStatCache;
import com.shi.prometheus.business.barrier.entity.BarrierCSTStat;
import com.shi.prometheus.business.barrier.entity.BarrierRDGStat;
import com.shi.prometheus.business.cloud.netty.CloudNettyClient;
import com.shi.prometheus.db.SqlLiteDao;
import com.shi.prometheus.protobuf.MessageDTO;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/9
 */
public class PersistenceTask<T> implements Runnable {

    private Class<T> clazz;

    private T object;

    private boolean needPushToCloud;

    private MessageDTO.Message msg;

    public PersistenceTask(Class<T> clazz, T object, boolean needPushToCloud, MessageDTO.Message msg) {
        this.clazz = clazz;
        this.object = object;
        this.needPushToCloud = needPushToCloud;
        this.msg = msg;
    }

    @Override
    public void run() {
        if (clazz == BarrierCSTStat.class) {
            BarrierCSTStat barrierCSTStat = (BarrierCSTStat) this.object;
            BarrierCSTStatCache.getInstance().appendSignCode(barrierCSTStat.getCst());
        } else if (clazz == BarrierRDGStat.class) {
            BarrierRDGStat barrierRDGStat = (BarrierRDGStat) this.object;
            BarrierCSTStatCache.getInstance().appendSignCode(barrierRDGStat.getRdg());
        }

        new SqlLiteDao<T>().createOrUpdateOne(clazz, object);
        if (needPushToCloud && msg != null) {
            CloudNettyClient.getInstance().sendMsg(msg);
        }
    }
}
