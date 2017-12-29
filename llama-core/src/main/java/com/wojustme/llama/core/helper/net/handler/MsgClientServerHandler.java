package com.wojustme.llama.core.helper.net.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * 信息处理器
 * @author xurenhe
 * @date 2017/12/29
 */
public interface MsgClientServerHandler<T> extends MsgServerHandler<T> {

    /**
     * 客户端发送初始化数据
     * @param ctx
     */
    void sendInitMsg(ChannelHandlerContext ctx);
}
