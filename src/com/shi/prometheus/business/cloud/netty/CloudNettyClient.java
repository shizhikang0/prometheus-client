package com.shi.prometheus.business.cloud.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/10
 */
public class CloudNettyClient  {

    public static int time = 2 * 60 * 1000;

    private int port = 9091;

    private String host = "192.168.0.142";
    private SocketChannel socketChannel;

    private CloudNettyClient() {
    }

    public void connectCloud()  {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ClientHandlerInitilizer(this));
        ChannelFuture future = bootstrap.connect();
        //客户端断线重连逻辑
        future.addListener((ChannelFutureListener) future1 -> {
            if (! future1.isSuccess()) {
                future1.channel().eventLoop().schedule(() -> connectCloud(), 20, TimeUnit.SECONDS);
            }
        });
        socketChannel = (SocketChannel) future.channel();
    }

    public void startMonitor() {
        connectCloud();
    }

    public void sendMsg(Object msg) {
        socketChannel.writeAndFlush(msg);
    }

    private static class LazyLoader {
        public static CloudNettyClient instance = new CloudNettyClient();
    }

    public static CloudNettyClient getInstance() {
        return LazyLoader.instance;
    }
}

