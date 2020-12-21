package com.shi.prometheus.common;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/8
 */
public enum ChannelStatusEnum {
    ON(1, "启用"),
    OFF(0, "弃用");

    private Integer status;
    private String desc;

    ChannelStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
