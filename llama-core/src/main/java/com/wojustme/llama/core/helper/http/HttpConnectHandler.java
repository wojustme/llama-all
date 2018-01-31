package com.wojustme.llama.core.helper.http;

import com.wojustme.llama.core.coordinator.CoordinatorConfig;
import com.wojustme.llama.core.helper.http.action.ErrorRouterAction;
import com.wojustme.llama.core.helper.http.action.RouterAction;
import com.wojustme.llama.core.helper.http.action.UploadRouterAction;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author xurenhe
 * @date 2018/1/27
 */
public class HttpConnectHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final CoordinatorConfig coordinatorConfig;

    public HttpConnectHandler(CoordinatorConfig coordinatorConfig) {
        this.coordinatorConfig = coordinatorConfig;
    }

    /**
     * 路由控制器
     */
    private RouterAction routerAction;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) httpObject;
            handlerRequest(ctx, httpRequest);
        } else if (httpObject instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) httpObject;
            handlerContent(ctx, httpContent);
        }
    }

    private void handlerContent(ChannelHandlerContext ctx, HttpContent httpContent) throws Exception {
        if (routerAction == null || routerAction instanceof ErrorRouterAction) {
            ctx.writeAndFlush(new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            ctx.close();
            return;
        }
        routerAction.hanlderHttpContent(ctx, httpContent);
    }

    private void handlerRequest(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        choseRouterAction(httpRequest);
    }

    private void choseRouterAction(HttpRequest httpRequest) {
        String uri = httpRequest.uri();
        if (StringUtils.isEmpty(uri)) {
            this.routerAction = new ErrorRouterAction();
        }
        if (uri.startsWith("/upload")) {
            this.routerAction = new UploadRouterAction(coordinatorConfig, httpRequest);
            return;
        }
        this.routerAction = new ErrorRouterAction();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        boolean active = ctx.channel().isActive();
        if (active) {
            ctx.writeAndFlush(new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            ctx.close();
        }

    }
}
