package com.shi.prometheus.common;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/11
 */
public enum EquipTypeEnum {
    BARRIER(1, "道闸"),
    CAMERA(2, "相机");

    private Integer type;

    private String desc;

    EquipTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
