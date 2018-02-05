package com.wojustme.llama.core.helper.http.action;

import com.wojustme.llama.core.coordinator.CoordinatorConfig;
import com.wojustme.llama.core.coordinator.CoordinatorData;
import com.wojustme.llama.core.coordinator.CoordinatorEventDispatch;
import com.wojustme.llama.core.helper.http.HttpUtils;
import com.wojustme.llama.core.helper.http.bean.ResponseContentTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xurenhe
 * @date 18/2/1
 */
public class ApiRouterAction extends RouterAction {


    private CoordinatorData coordinatorData;

    private HttpRequest httpRequest;

    public ApiRouterAction(CoordinatorData coordinatorData, HttpRequest httpRequest) {
        this.coordinatorData = coordinatorData;
        this.httpRequest = httpRequest;
    }

    @Override
    public void hanlderHttpContent(ChannelHandlerContext ctx, HttpContent httpContent) throws Exception {

        Map<String, String> rsMap = new HashMap<>();
        rsMap.put("rs", "ok");
        FullHttpResponse res = HttpUtils.buildResponse(rsMap, ResponseContentTypeEnum.JSON, httpRequest);

        ctx.writeAndFlush(res);
        ctx.close();
    }
}
