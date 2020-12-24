package com.shi.prometheus.business.barrier.netty;

import com.alibaba.fastjson.JSONObject;
import com.shi.prometheus.business.cloud.netty.CloudNettyClient;
import com.shi.prometheus.business.park.cache.CurrentParkCache;
import com.shi.prometheus.business.park.entity.Park;
import com.shi.prometheus.common.EquipConnectMsg;
import com.shi.prometheus.common.ConnectStatusConstants;
import com.shi.prometheus.common.MessageTypeConstants;
import com.shi.prometheus.protobuf.MessageDTO;
import com.shi.prometheus.service.AbstractBarrierEquipService;
import com.shi.prometheus.utils.PrometheusTaskExecutor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class BarrierClientHandler extends SimpleChannelInboundHandler<String> {

	public static final Logger logger = LogManager.getLogger(BarrierClientHandler.class);

	private AbstractBarrierEquipService barrierEquipService;

	public BarrierClientHandler(AbstractBarrierEquipService barrierEquipService) {
		this.barrierEquipService = barrierEquipService;
	}

	protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
		barrierEquipService.solve(s);
    }

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channelActive");
		super.channelActive(ctx);
		Integer parkId = barrierEquipService.getBarrierNettyClient().getParkId();
		Park park = CurrentParkCache.getInstance().getParkById(parkId);
		String channelName = barrierEquipService.getBarrierNettyClient().getChannelName();
		Integer channelNo = barrierEquipService.getBarrierNettyClient().getChannelNo();
		String ip = barrierEquipService.getBarrierNettyClient().getIp();
		EquipConnectMsg equipConnectMsg = EquipConnectMsg.builder().parkName(park.getName()).parkNo(park.getParkNo()).channelName(channelName)
				.channelNo(channelNo).equipType(MessageTypeConstants.EQUIP_BARRIER).equipIp(ip).connectStatus(ConnectStatusConstants.SUCCESS).build();
		MessageDTO.Message msg = MessageDTO.Message.newBuilder().setEquip(MessageTypeConstants.EQUIP_BARRIER).setType(MessageTypeConstants.TYPE_CONNECT).setLevel(MessageTypeConstants.LEVEL_MILD)
				.setJson(JSONObject.toJSON(equipConnectMsg).toString())
				.build();
//		PrometheusTaskExecutor.execute(new Runnable() {
//			@Override
//			public void run() {
//				CloudNettyClient.getInstance().sendMsg(msg);
//			}
//		});
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channelInactive");
		EventLoop loop = ctx.channel().eventLoop();
		loop.schedule(new Runnable() {
			@Override
			public void run() {
				logger.error("运行时与道闸断开，重连");
				barrierEquipService.getBarrierNettyClient().doConnect();
			}
		}, 10L, TimeUnit.SECONDS);
		super.channelInactive(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		logger.info("userEventTriggered");
		super.userEventTriggered(ctx, evt);
		if (evt instanceof IdleStateEvent) {
			ctx.channel().writeAndFlush("cst");
		}
	}

}
