package com.wojustme.llama.core.helper.net.handler.channel;

import com.wojustme.llama.core.exception.ZkException;
import com.wojustme.llama.core.helper.net.handler.MsgClientServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 封装netty通道处理，面向于客户端
 * @author xurenhe
 * @date 2017/12/29
 */
public class MsgClientChannelHandler<T> extends ChannelInboundHandlerAdapter {

    private final MsgClientServerHandler<T> msgClientHandler;

    public MsgClientChannelHandler(MsgClientServerHandler msgClientHandler) {
        this.msgClientHandler = msgClientHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws ZkException {
        try {
            msgClientHandler.sendInitMsg(ctx);
        } catch (Exception e) {
            throw new ZkException("client send init data error", e);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws ZkException {
        try {
            msgClientHandler.handleMsg(ctx, (T) msg);
        } catch (Exception e) {
            throw new ZkException("handle data error", e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        msgClientHandler.handleError(ctx, cause);
    }
}
