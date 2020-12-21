package com.shi.prometheus.db;

import com.shi.prometheus.utils.TimeUtils;
import com.shi.prometheus.utils.PathUtil;

import java.io.File;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/8
 */
public class DBUtils {

    public static String getCurrentDBFilePath() {
        File dateFolder = new File(PathUtil.getCurrentRootPath() + "\\dbs\\" + TimeUtils.getCurrentLocalMonthForDB());
        if (!dateFolder.exists()) {
            dateFolder.mkdirs();
        }
        return dateFolder.getPath() + "\\" +TimeUtils.getCurrentLocalMonthForDB() +".db";
    }

    public static String getSqlLiteUrl() {
        return "jdbc:sqlite:" + getCurrentDBFilePath();
    }
}
