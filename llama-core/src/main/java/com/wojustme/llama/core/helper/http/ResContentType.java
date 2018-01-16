package com.wojustme.llama.core.helper.http;

/**
 * @author xurenhe
 * @date 2018/1/16
 */
public enum ResContentType {
    TEXT("text/plain"),
    HTML("text/html"),
    XHTML("application/xhtml+xml"),
    XML("application/xml"),
    JSON("application/json");

    public String value;

    ResContentType(String value) {
        this.value = value;
    }
}
