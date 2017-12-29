package com.wojustme.llama.core.helper.net.handler.channel;

import com.wojustme.llama.core.exception.ZkException;
import com.wojustme.llama.core.helper.net.handler.MsgServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 封装netty通道处理，面向于服务端
 * @author xurenhe
 * @date 2017/12/29
 */
public class MsgServerChannelHandler<T> extends ChannelInboundHandlerAdapter {

    private final MsgServerHandler<T> msgServerHandler;

    public MsgServerChannelHandler(MsgServerHandler msgServerHandler) {
        this.msgServerHandler = msgServerHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws ZkException {
        try {
            msgServerHandler.handleMsg(ctx, (T) msg);
        } catch (Exception e) {
            throw new ZkException("handle data error", e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        msgServerHandler.handleError(ctx, cause);
    }
}
