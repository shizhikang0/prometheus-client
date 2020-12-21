package com.shi.prometheus.business.cloud.netty;

import com.shi.prometheus.business.barrier.boot.BarrierClientBoot;
import com.shi.prometheus.business.barrier.netty.BarrierNettyClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    private final CloudNettyClient nettyClient;

    public HeartbeatHandler(CloudNettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                List<BarrierNettyClient> allBarrierNettyClients =
                        BarrierClientBoot.getInstance().getAllBarrierNettyClients();

                for (BarrierNettyClient client : allBarrierNettyClients) {
                    client.setUpload(true);
                    client.getFuture().channel().writeAndFlush("cst");
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //如果运行过程中服务端挂了,执行重连机制
        EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> nettyClient.connectCloud(), 10L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("捕获的异常" + cause.getMessage());
        ctx.channel().close();
    }
}
