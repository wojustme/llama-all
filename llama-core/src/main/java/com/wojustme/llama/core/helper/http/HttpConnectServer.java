package com.wojustme.llama.core.helper.http;

import com.wojustme.llama.core.coordinator.CoordinatorConfig;
import com.wojustme.llama.core.coordinator.CoordinatorEventHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * HTTP的server
 * @author xurenhe
 * @date 2018/1/27
 */
public class HttpConnectServer implements Runnable {

    private final CoordinatorConfig coordinatorConfig;


    public HttpConnectServer(CoordinatorConfig coordinatorConfig) {
        this.coordinatorConfig = coordinatorConfig;
    }

    @Override
    public void run() {
        start();
    }

    public void start() {

        int port = coordinatorConfig.getHttpServerPort();
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel> () {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpRequestDecoder());
                            pipeline.addLast(new HttpResponseEncoder());
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpConnectHandler(coordinatorConfig));
                        }
                    });
            ChannelFuture sync = serverBootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
