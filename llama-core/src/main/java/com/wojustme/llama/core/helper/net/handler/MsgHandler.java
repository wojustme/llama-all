package com.wojustme.llama.core.helper.net.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * 信息处理器
 * @author xurenhe
 * @date 2017/12/29
 */
public interface MsgHandler<T> {

    /**
     * 处理数据
     * @param ctx
     * @param data
     */
    void handleMsg(ChannelHandlerContext ctx, T data);

    /**
     * 处理异常
     * @param ctx
     * @param cause
     */
    void handleError(ChannelHandlerContext ctx, Throwable cause);

    ChannelHandlerContext getChannelHandlerContext();
}
