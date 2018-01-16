package com.wojustme.llama.core.helper.http;

import com.wojustme.llama.core.exception.HttpException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 基于netty封装http协议
 * 1. /api => restful协议的请求
 * 2. /upload => 上传文件
 * 3. /download => 下载文件
 * 4. /ws => websocket协议请求
 * 5. /static => 静态资源（HTML、CSS、JavaScript）
 * @author xurenhe
 * @date 2018/1/13
 */
public class HttpServer {

    /**
     * 启动端口
     */
    private int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() throws HttpException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpRequestDecoder());
                            pipeline.addLast(new HttpResponseEncoder());
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpConnectHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new HttpException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {
        new HttpServer(12138).start();
    }
}
