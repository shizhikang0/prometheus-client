package com.shi.prometheus.business.cloud.netty;

import com.alibaba.fastjson.JSONObject;
import com.shi.prometheus.business.barrier.boot.BarrierClientBoot;
import com.shi.prometheus.business.barrier.netty.BarrierNettyClient;
import com.shi.prometheus.business.park.cache.CurrentParkCache;
import com.shi.prometheus.business.park.entity.Park;
import com.shi.prometheus.common.ConnectStatusConstants;
import com.shi.prometheus.common.EquipConnectMsg;
import com.shi.prometheus.common.MessageTypeConstants;
import com.shi.prometheus.common.MonitorClientMsg;
import com.shi.prometheus.protobuf.MessageDTO;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/10
 */
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<MessageDTO.Message> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与云端断开连接执行");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与云端连接成功执行");
        Park rootPark = CurrentParkCache.getInstance().getRootPark();
        if (rootPark == null) {
            MessageDTO.Message msg = MessageDTO.Message.newBuilder().setEquip(MessageTypeConstants.EQUIP_MONITOR).setType(MessageTypeConstants.TYPE_CONNECT).setLevel(MessageTypeConstants.LEVEL_MILD)
                    .build();
            CloudNettyClient.getInstance().sendMsg(msg);
        } else {
            MonitorClientMsg clientMsg = MonitorClientMsg.builder().parkName(rootPark.getName()).parkNo(rootPark.getParkNo()).connectStatus(ConnectStatusConstants.SUCCESS).build();
            MessageDTO.Message msg = MessageDTO.Message.newBuilder().setEquip(MessageTypeConstants.EQUIP_MONITOR).setType(MessageTypeConstants.TYPE_CONNECT).setLevel(MessageTypeConstants.LEVEL_MILD)
                    .setJson(JSONObject.toJSON(clientMsg).toString())
                    .build();
            CloudNettyClient.getInstance().sendMsg(msg);
        }

    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("抛出异常执行");
    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//        System.out.println(s);
//        if (s.equals("status")) {
//            List<BarrierNettyClient> allBarrierNettyClients =
//                    BarrierClientBoot.getInstance().getAllBarrierNettyClients();
//
//            for (BarrierNettyClient client : allBarrierNettyClients) {
//                client.setUpload(true);
//                client.getFuture().channel().writeAndFlush("cst");
//            }
//        }
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageDTO.Message message) throws Exception {

    }
}
