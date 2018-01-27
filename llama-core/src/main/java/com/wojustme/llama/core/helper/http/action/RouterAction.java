package com.wojustme.llama.core.helper.http.action;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpContent;

/**
 * @author xurenhe
 * @date 2018/1/27
 */
public abstract class RouterAction {
    public abstract void hanlderHttpContent(ChannelHandlerContext ctx, HttpContent httpContent) throws Exception;
}
