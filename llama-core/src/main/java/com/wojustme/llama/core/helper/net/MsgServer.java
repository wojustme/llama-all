package com.wojustme.llama.core.helper.net;

import com.wojustme.llama.core.exception.NetException;
import com.wojustme.llama.core.helper.net.handler.MsgServerHandler;
import com.wojustme.llama.core.helper.net.handler.channel.MsgServerChannelHandler;
import com.wojustme.llama.core.helper.net.msg.MsgCoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 数据传输的服务器
 * @author xurenhe
 * @date 2017/12/29
 */
public class MsgServer {

    /**
     * 启动端口
     */
    private int port;

    /**
     * 信息处理器
     */
    private MsgServerHandler msgServerHandler;

    /**
     * 信息编码器
     */
    private MsgCoder msgCoder;

    public MsgServer(int port, MsgServerHandler msgServerHandler, MsgCoder msgCoder) {
        this.port = port;
        this.msgServerHandler = msgServerHandler;
        this.msgCoder = msgCoder;
    }

    public void start() throws NetException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        MsgServerChannelHandler msgServerChannelHandler = new MsgServerChannelHandler(msgServerHandler);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 自定义的编解码器
                            ch.pipeline().addLast(msgCoder.getToBeanDecoder());
                            ch.pipeline().addLast(msgCoder.getToByteEncoder());
                            // 处理数据
                            ch.pipeline().addLast(msgServerChannelHandler);
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new NetException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
