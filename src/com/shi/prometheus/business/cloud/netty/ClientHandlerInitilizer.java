package com.shi.prometheus.business.cloud.netty;

import com.shi.prometheus.protobuf.MessageDTO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientHandlerInitilizer extends ChannelInitializer<Channel> {

    private CloudNettyClient cloudNettyClient;

    public ClientHandlerInitilizer(CloudNettyClient cloudNettyClient) {
        this.cloudNettyClient = cloudNettyClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new IdleStateHandler(60, 60, 120))
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(MessageDTO.Message.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(new HeartbeatHandler(cloudNettyClient))
                .addLast(new NettyClientHandler());
    }
}
