package com.shi.prometheus.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorClientMsg {
    private String parkName;
    private String parkNo;
    private int connectStatus;
}