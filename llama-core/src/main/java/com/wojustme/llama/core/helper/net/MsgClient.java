package com.wojustme.llama.core.helper.net;

import com.wojustme.llama.core.exception.NetException;
import com.wojustme.llama.core.helper.net.bean.NetAddress;
import com.wojustme.llama.core.helper.net.handler.channel.MsgClientChannelHandler;
import com.wojustme.llama.core.helper.net.handler.MsgClientServerHandler;
import com.wojustme.llama.core.helper.net.msg.MsgCoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据传输的客户端
 *
 * @author xurenhe
 * @date 2017/12/29
 */
public class MsgClient {

    /**
     * 请求的网络地址
     */
    private NetAddress netAddress;

    /**
     * 信息处理器
     */
    private MsgClientServerHandler msgClientHandler;

    /**
     * 信息编码器
     */
    private MsgCoder msgCoder;


    public MsgClient(NetAddress netAddress, MsgClientServerHandler msgClientHandler, MsgCoder msgCoder) {
        this.netAddress = netAddress;
        this.msgClientHandler = msgClientHandler;
        this.msgCoder = msgCoder;
    }


    public void connect() throws NetException {

        if (!validate()) {
            throw new NetException("net client error");
        }

        String host = netAddress.getHost();
        int port = netAddress.getPort();

        MsgClientChannelHandler msgClientChannelHandler = new MsgClientChannelHandler(msgClientHandler);

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 自定义的编解码器
                            ch.pipeline().addLast(msgCoder.getToBeanDecoder());
                            ch.pipeline().addLast(msgCoder.getToByteEncoder());
                            // 处理数据
                            ch.pipeline().addLast(msgClientChannelHandler);
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new NetException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

    private boolean validate() {
        if (netAddress == null) {
            return false;
        }
        if (StringUtils.isEmpty(netAddress.getHost())) {
            return false;
        }
        if (netAddress.getPort() < 1024) {
            return false;
        }
        return true;
    }
}
