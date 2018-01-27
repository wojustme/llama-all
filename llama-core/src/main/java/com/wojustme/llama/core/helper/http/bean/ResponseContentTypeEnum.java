package com.wojustme.llama.core.helper.http.bean;

/**
 * @author xurenhe
 * @date 2018/1/16
 */
public enum ResponseContentTypeEnum {
    TEXT("text/plain"),
    HTML("text/html"),
    XHTML("application/xhtml+xml"),
    XML("application/xml"),
    JSON("application/json");

    public String value;

    ResponseContentTypeEnum(String value) {
        this.value = value;
    }
}
