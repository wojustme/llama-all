package com.wojustme.llama.core.helper.http;


import com.wojustme.llama.core.exception.HttpException;
import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.helper.http.bean.ResponseContentTypeEnum;
import com.wojustme.llama.core.util.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;

/**
 * @author xurenhe
 * @date 2018/1/27
 */
public final class HttpUtils {


    private static final String CHARSET = "UTF-8";

    /**
     * 构建http回应码
     * @param data
     * @param contentTypeEnum
     * @param httpRequest
     * @return
     * @throws Exception
     */
    public static FullHttpResponse buildResponse(Object data, ResponseContentTypeEnum contentTypeEnum, HttpRequest httpRequest) throws Exception {

        ByteBuf byteBuf = null;
        switch (contentTypeEnum) {
            case JSON:
                byteBuf = generateJsonByteBuf(data);
                break;
            case TEXT:
                byteBuf = generateTextByteBuf(data);
                break;
            default:
                byteBuf = generateJsonByteBuf(data);
        }

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentTypeEnum.value);
        // 允许跨域
        response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

        if (HttpUtil.isKeepAlive(httpRequest)) {
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
        }
        return response;
    }


    /**
     * 生成json格式
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     * @throws HttpException
     */
    private static ByteBuf generateTextByteBuf(Object data) throws UnsupportedEncodingException, HttpException {
        if (!(data instanceof String)) {
            throw new HttpException("build resp error, data is not a string");
        }
        return Unpooled.wrappedBuffer(((String) data).getBytes(CHARSET));

    }

    /**
     * 生成文本格式
     * @param data
     * @return
     * @throws SerializerException
     * @throws UnsupportedEncodingException
     */
    private static ByteBuf generateJsonByteBuf(Object data) throws SerializerException, UnsupportedEncodingException {
        String jsonStr = JsonUtils.toJsonStr(data);

        return Unpooled.wrappedBuffer(jsonStr.getBytes(CHARSET));
    }
}
