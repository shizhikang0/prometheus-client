package com.shi.prometheus.business.barrier.service;

import com.alibaba.fastjson.JSONObject;
import com.shi.prometheus.business.barrier.entity.BarrierCSTStat;
import com.shi.prometheus.business.barrier.netty.BarrierNettyClient;
import com.shi.prometheus.business.park.cache.CurrentParkCache;
import com.shi.prometheus.business.park.entity.Park;
import com.shi.prometheus.common.*;
import com.shi.prometheus.protobuf.MessageDTO;
import com.shi.prometheus.service.AbstractBarrierEquipService;
import com.shi.prometheus.utils.PrometheusTaskExecutor;

import java.util.Date;

public class WJBarrierEquipServiceImpl extends AbstractBarrierEquipService {

	private BarrierNettyClient barrierNettyClient;

	public WJBarrierEquipServiceImpl(BarrierNettyClient barrierNettyClient) {
		this.barrierNettyClient = barrierNettyClient;
	}

	@Override
	public String getName() {
		return "威捷CB01SV-I";
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public BarrierNettyClient getBarrierNettyClient() {
		return barrierNettyClient;
	}

	@Override
	public void open() {
		barrierNettyClient.getFuture().channel().writeAndFlush("open");
	}

	@Override
	public void close() {
		barrierNettyClient.getFuture().channel().writeAndFlush("close");
	}

	@Override
	public void getCST() {
		barrierNettyClient.getFuture().channel().writeAndFlush("cst");
	}

	@Override
	public void getRDG() {
		barrierNettyClient.getFuture().channel().writeAndFlush("RDG");
	}

	@Override
	public void lock() {
		barrierNettyClient.getFuture().channel().writeAndFlush("lock");
	}

	@Override
	public void unLock() {
		barrierNettyClient.getFuture().channel().writeAndFlush("unlock");
	}

	@Override
	public void solve(String sign) {
		int code = BarrierCSTStatusConstants.BarrierCSTStatusEnum.getCodeBySign(sign);
		if (code == BarrierCSTStatusConstants.ILLEGAL_CODE) {
			//TODO 判断是否要报警
			return;
		}

		BarrierCSTStat barrierCSTStat = new BarrierCSTStat(null, barrierNettyClient.getIp(), barrierNettyClient.getChannelName(), new Date(), code);
		boolean upload = barrierNettyClient.isUpload();
		if (upload) {
			Integer parkId = getBarrierNettyClient().getParkId();
			Park park = CurrentParkCache.getInstance().getParkById(parkId);
			String channelName = getBarrierNettyClient().getChannelName();
			Integer channelNo = getBarrierNettyClient().getChannelNo();
			String ip = getBarrierNettyClient().getIp();
			EquipRealTimeStatusMsg equipRealTimeStatusMsg = EquipRealTimeStatusMsg.builder().parkName(park.getName()).parkNo(park.getParkNo()).channelName(channelName)
					.channelNo(channelNo).equipType(MessageTypeConstants.EQUIP_BARRIER).equipIp(ip).statusCode(ConnectStatusConstants.SUCCESS).build();
			MessageDTO.Message msg = MessageDTO.Message.newBuilder().setEquip(MessageTypeConstants.EQUIP_BARRIER).setType(MessageTypeConstants.TYPE_EQUIP_STATUS).setLevel(MessageTypeConstants.LEVEL_MILD)
					.setJson(JSONObject.toJSON(equipRealTimeStatusMsg).toString())
					.build();
			PrometheusTaskExecutor.execute(new PersistenceTask<BarrierCSTStat>(BarrierCSTStat.class, barrierCSTStat, true, msg));
			barrierNettyClient.setUpload(false);
		} else {
			PrometheusTaskExecutor.execute(new PersistenceTask<BarrierCSTStat>(BarrierCSTStat.class, barrierCSTStat, false, null));
		}


	}

	@Override
	public void special(String sign) {
		barrierNettyClient.getFuture().channel().writeAndFlush(sign);
	}
}
