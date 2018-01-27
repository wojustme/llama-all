package com.wojustme.llama.core.helper.http.bean;

import io.netty.handler.codec.http.HttpMethod;

/**
 * @author xurenhe
 * @date 2018/1/16
 */
public enum HttpMethodEnum {

    NONE,
    GET,
    POST,
    DELETE,
    PUT;


    public static final HttpMethodEnum initByNettyHttpMethod(HttpMethod httpMethod) {
        final String name = httpMethod.name();
        switch (name) {
            case "GET":
                return HttpMethodEnum.GET;
            case "POST":
                return HttpMethodEnum.POST;
            case "DELETE":
                return HttpMethodEnum.DELETE;
            case "PUT":
                return HttpMethodEnum.PUT;
            default:
                return HttpMethodEnum.NONE;
        }
    }

}
