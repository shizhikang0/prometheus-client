package com.shi.prometheus.business.barrier.netty;

import com.alibaba.fastjson.JSONObject;
import com.shi.prometheus.business.barrier.service.WJBarrierEquipServiceImpl;
import com.shi.prometheus.business.park.cache.CurrentParkCache;
import com.shi.prometheus.business.park.entity.Park;
import com.shi.prometheus.common.*;
import com.shi.prometheus.common.entity.EquipConnect;
import com.shi.prometheus.protobuf.MessageDTO;
import com.shi.prometheus.service.AbstractBarrierEquipService;
import com.shi.prometheus.utils.ClientConfig;
import com.shi.prometheus.utils.PrometheusTaskExecutor;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29 16:13
 */
public class BarrierNettyClient {

    public static final Logger logger = LogManager.getLogger(BarrierNettyClient.class);

    private Integer lastFailTimes = 0;

    private Bootstrap bootstrap;

    private EventLoopGroup worker;

    private ChannelFuture future;

    private com.shi.prometheus.business.channel.entity.Channel channel;

    private AbstractBarrierEquipService barrierEquipService;

    private BarrierClientHandler barrierClientHandler;

    private boolean upload = false;

    public BarrierNettyClient(com.shi.prometheus.business.channel.entity.Channel channel) {
        this.channel = channel;
    }

    public void startCall() {
        bootstrap = new Bootstrap();
        worker = new NioEventLoopGroup();
        barrierEquipService = new WJBarrierEquipServiceImpl(this);
        barrierClientHandler = new BarrierClientHandler(barrierEquipService);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(worker)
                .handler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new StringEncoder());
                channel.pipeline().addLast(new StringDecoder());
                channel.pipeline().addLast(new IdleStateHandler(20, 20, 30, TimeUnit.SECONDS));
                channel.pipeline().addLast(barrierClientHandler);
            }
        });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        doConnect();
    }

    public void doConnect() {
        if (future != null) {
            try {
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        future = bootstrap.connect(channel.getControlMachIP(), channel.getControlMachPort() != null ? channel.getControlMachPort() : 5000);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    EquipConnect equipConnect = new EquipConnect();
                    equipConnect.setEquipType(EquipTypeEnum.BARRIER.getType());
                    equipConnect.setConnectTime(new Date());
                    equipConnect.setStatus(ConnectStatusConstants.SUCCESS);
                    Integer parkId = getParkId();
                    Park park = CurrentParkCache.getInstance().getParkById(parkId);
                    EquipConnectMsg equipConnectMsg = EquipConnectMsg.builder().parkName(park.getName()).parkNo(park.getParkNo()).channelName(getChannelName()).channelNo(getChannelNo()).equipType(MessageTypeConstants.EQUIP_BARRIER).equipIp(getIp()).connectStatus(ConnectStatusConstants.SUCCESS).build();
                    MessageDTO.Message msg = MessageDTO.Message.newBuilder().setEquip(MessageTypeConstants.EQUIP_BARRIER).setType(MessageTypeConstants.TYPE_CONNECT).setLevel(MessageTypeConstants.LEVEL_MILD)
                            .setJson(JSONObject.toJSON(equipConnectMsg).toString())
                            .build();
                    PrometheusTaskExecutor.execute(new PersistenceTask<EquipConnect>(EquipConnect.class, equipConnect, true, msg));
                    lastFailTimes = 0;
                } else {
                    logger.error("启动时连接道闸失败，重连");
                    lastFailTimes++;
                    EquipConnect equipConnect = new EquipConnect();
                    equipConnect.setEquipType(EquipTypeEnum.BARRIER.getType());
                    equipConnect.setConnectTime(new Date());
                    equipConnect.setStatus(ConnectStatusConstants.FAIL);
                    Integer parkId = getParkId();
                    Park park = CurrentParkCache.getInstance().getParkById(parkId);
                    EquipConnectMsg equipConnectMsg = EquipConnectMsg.builder().parkName(park.getName()).parkNo(park.getParkNo()).channelName(getChannelName()).channelNo(getChannelNo()).equipType(MessageTypeConstants.EQUIP_BARRIER).equipIp(getIp()).connectStatus(ConnectStatusConstants.FAIL).build();
                    int level = MessageTypeConstants.LEVEL_MILD;
                    if (lastFailTimes > ClientConfig.BARRIER_CONNECT_FAIL_ALARM_L3_CONTROL) {
                        level = MessageTypeConstants.LEVEL_CRITICAL;
                    } else if (lastFailTimes > ClientConfig.BARRIER_CONNECT_FAIL_ALARM_L2_CONTROL) {
                        level = MessageTypeConstants.LEVEL_MODERATE;
                    }
                    MessageDTO.Message msg = MessageDTO.Message.newBuilder().setEquip(MessageTypeConstants.EQUIP_BARRIER).setType(MessageTypeConstants.TYPE_CONNECT).setLevel(level)
                            .setJson(JSONObject.toJSON(equipConnectMsg).toString())
                            .build();
                    PrometheusTaskExecutor.execute(new PersistenceTask<EquipConnect>(EquipConnect.class, equipConnect, true, msg));
                    EventLoop loop = channelFuture.channel().eventLoop();
                    loop.schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, 10L, TimeUnit.SECONDS);
                }
            }
        });
    }

    public ChannelFuture getFuture() {
        return future;
    }

    public AbstractBarrierEquipService getBarrierEquipService() {
        return barrierEquipService;
    }

    public Integer getChannelNo() {
        return channel.getChannelNo();
    }

    public String getChannelName() {
        return channel.getChannelName();
    }

    public Integer getChannelProp() {
        return channel.getChannelProp();
    }

    public String getIp() {
        return channel.getControlMachIP();
    }

    public Integer getPort() {
        return channel.getControlMachPort();
    }

    public Integer getParkId() {
        return channel.getParkId();
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }
}
