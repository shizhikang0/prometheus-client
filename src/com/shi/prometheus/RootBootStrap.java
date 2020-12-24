package com.shi.prometheus;

import com.shi.prometheus.business.channel.cache.ChannelListCache;
import com.shi.prometheus.business.park.cache.CurrentParkCache;
import com.shi.prometheus.db.cache.SqlServerConnectSetCache;
import com.shi.prometheus.frame.ClientFrame;
import com.shi.prometheus.utils.FileLockHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

/**
 * @Description: 项目根启动类
 * @Author: shizhikang
 * @Date: 2020/11/29
 */

public class RootBootStrap {

    public static final Logger logger = LogManager.getLogger(RootBootStrap.class);

    public static void main(String[] args) {
        try {
            //文件锁，保证程序单例运行
            FileLockHelper.makeSingle();
            initDBStat();
            //启动Frame
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        ClientFrame frame = new ClientFrame();
                        frame.setVisible(true);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            });
        } catch (Exception e) {
            logger.error(e);
        }
    }

    //模块化加载，模块前后有依赖关系
    public static void initDBStat() {
        //1. 读取SQL Server内的道口、停车场数据，并放置在内存和本地sqlite中
        try {
            CurrentParkCache.getInstance().initial();
            ChannelListCache.getInstance().initial();
        } catch (Exception e) {
            SqlServerConnectSetCache.LOAD_DB_EXCEPTION = true;
        }
    }
}
