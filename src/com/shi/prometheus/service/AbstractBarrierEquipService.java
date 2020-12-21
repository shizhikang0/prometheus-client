package com.shi.prometheus.service;

import com.shi.prometheus.business.barrier.netty.BarrierNettyClient;

public abstract class AbstractBarrierEquipService implements EquipService{

    public abstract BarrierNettyClient getBarrierNettyClient();

    /**
     * 开闸
     */
    public abstract void open();

    /**
     * 关闸
     */
    public abstract void close();

    /**
     * 获取道闸状态
     */
    public abstract void getCST();

    /**
     * 获取地感状态
     */
    public abstract void getRDG();

    /**
     * 道闸加锁
     */
    public abstract void lock();

    /**
     * 道闸解锁
     */
    public abstract void unLock();

    /**
     * 解析道闸返回的指令
     * @param sign
     */
    public abstract void solve(String sign);

    /**
     * 后门
     */
    public abstract void special(String sign);
}
