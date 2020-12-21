package com.shi.prometheus.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealTimeStatusMsg {
    private String parkName;
    private String parkNo;
    private Integer parkId;

    private Integer channelNo;
    private String channelName;

    private int equipType;
    private String equipIp;

    private Integer statusCode;
}
