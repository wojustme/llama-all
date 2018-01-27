package com.wojustme.llama.core.helper.http.action;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpContent;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author xurenhe
 * @date 2018/1/27
 */
public class ErrorRouterAction extends RouterAction {
    @Override
    public void hanlderHttpContent(ChannelHandlerContext ctx, HttpContent httpContent) throws Exception {
        ctx.writeAndFlush(new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
        ctx.close();
    }
}
