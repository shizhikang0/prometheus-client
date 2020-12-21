package com.shi.prometheus.db.callback;

import java.sql.ResultSet;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
public interface SqlCallback {
    void execute(ResultSet rs);
}
