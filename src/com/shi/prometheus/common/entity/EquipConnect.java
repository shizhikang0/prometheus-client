package com.shi.prometheus.common.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/16
 */
@DatabaseTable(tableName = "equip_connect")
public class EquipConnect {
    @DatabaseField(generatedId =true, columnName = "id")
    private Long id;

    @DatabaseField(columnName = "equip_type")
    private Integer equipType;  //设备类型：1：道闸；2：相机

    @DatabaseField(columnName = "connect_time")
    private Date connectTime;

    @DatabaseField(columnName = "status")
    private Integer status; //1: 连接成功；2：连接失败

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEquipType() {
        return equipType;
    }

    public void setEquipType(Integer equipType) {
        this.equipType = equipType;
    }

    public Date getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Date connectTime) {
        this.connectTime = connectTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
