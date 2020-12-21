package com.shi.prometheus.business.barrier.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * 地感状态
 */
@DatabaseTable(tableName = "barrier_rdg_stat")
public class BarrierRDGStat {

    @DatabaseField(generatedId =true, columnName = "id")
    private Long id;

    @DatabaseField(columnName = "ip")
    private String ip;

    @DatabaseField(columnName = "channel_name")
    private String channelName;

    @DatabaseField(columnName = "local_time")
    private Date localTime;

    @DatabaseField(columnName = "rdg")
    private Integer rdg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Date getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Date localTime) {
        this.localTime = localTime;
    }

    public Integer getRdg() {
        return rdg;
    }

    public void setRdg(Integer rdg) {
        this.rdg = rdg;
    }

}
