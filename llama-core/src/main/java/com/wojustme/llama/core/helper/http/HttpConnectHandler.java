package com.wojustme.llama.core.helper.http;

import com.wojustme.llama.core.util.JsonUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author xurenhe
 * @date 2018/1/16
 */
public class HttpConnectHandler extends SimpleChannelInboundHandler<HttpObject> {


    private Map<String, HttpRouterHandler> httpEventBeanMap;

    /**
     * http请求方法
     */
    private HttpMethodEnum httpMethod;

    /**
     * http请求路径
     */
    private String url;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) httpObject;
            initMetaData(httpRequest);
            System.out.println(this);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("key", "hello");
            String res = JsonUtils.toJsonStr(map);
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                    OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE, ResContentType.JSON);
            ctx.writeAndFlush(response);
            ctx.close();
        }

    }


    private void initMetaData(HttpRequest httpRequest) {
        this.httpMethod = HttpMethodEnum.initByNettyHttpMethod(httpRequest.method());
        this.url = httpRequest.uri();
    }
}
