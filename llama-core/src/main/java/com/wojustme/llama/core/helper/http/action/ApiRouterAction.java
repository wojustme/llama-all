package com.wojustme.llama.core.helper.http.action;

import com.wojustme.llama.core.coordinator.CoordinatorConfig;
import com.wojustme.llama.core.coordinator.CoordinatorEventHandler;
import com.wojustme.llama.core.helper.http.HttpUtils;
import com.wojustme.llama.core.helper.http.bean.ResponseContentTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author xurenhe
 * @date 18/2/1
 */
public class ApiRouterAction extends RouterAction {


    private CoordinatorConfig coordinatorConfig;

    private HttpRequest httpRequest;

    public ApiRouterAction(CoordinatorConfig coordinatorConfig, HttpRequest httpRequest) {
        this.coordinatorConfig = coordinatorConfig;
        this.httpRequest = httpRequest;
    }

    @Override
    public void hanlderHttpContent(ChannelHandlerContext ctx, HttpContent httpContent) throws Exception {

        EventBus.getDefault().register(new CoordinatorEventHandler());
        EventBus.getDefault().post("hello");
        Map<String, String> rsMap = new HashMap<>();
        rsMap.put("rs", "ok");
        FullHttpResponse res = HttpUtils.buildResponse(rsMap, ResponseContentTypeEnum.JSON, httpRequest);

        ctx.writeAndFlush(res);
        ctx.close();
    }
}
