package com.shi.prometheus.business.barrier.boot;

import com.shi.prometheus.business.barrier.netty.BarrierNettyClient;
import com.shi.prometheus.business.channel.cache.ChannelListCache;
import com.shi.prometheus.business.channel.entity.Channel;
import com.shi.prometheus.common.ChannelStatusEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/30
 */
public class BarrierClientBoot {

    public static final Logger logger = LogManager.getLogger(BarrierClientBoot.class);

    private static BarrierClientBoot instance = new BarrierClientBoot();

    private HashMap<String, BarrierNettyClient> barrierNettyClientHashMap = new HashMap<>();

    public static BarrierClientBoot getInstance() {
        return instance;
    }

    public void connectAllBarrier() {
        List<Channel> channelList =
                ChannelListCache.getInstance().getChannelList();
        if (channelList == null) {
            logger.error("没有拿到道口信息");
            return;
        }
        for (Channel channel : channelList) {
            if (channel.getState() == ChannelStatusEnum.OFF.getStatus()) {
                continue;
            }
            String controlMachIP = channel.getControlMachIP();
            BarrierNettyClient barrierNettyClient = new BarrierNettyClient(channel);
            barrierNettyClientHashMap.put(controlMachIP, barrierNettyClient);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    barrierNettyClient.startCall();
                }
            }).start();

        }
    }

    public BarrierNettyClient getBarrierNettyClientByIp(String ip) {
        return barrierNettyClientHashMap.get(ip);
    }

    public List<BarrierNettyClient> getAllBarrierNettyClients() {
        return new ArrayList<>(barrierNettyClientHashMap.values());
    }
}
