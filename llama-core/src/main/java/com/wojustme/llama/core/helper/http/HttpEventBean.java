package com.wojustme.llama.core.helper.http;

import com.google.common.base.Objects;
import io.netty.handler.codec.http.HttpMethod;

/**
 * @author xurenhe
 * @date 2018/1/16
 */
public class HttpEventBean {

    /**
     * 请求方法
     */
    private HttpMethod method;
    /**
     * 请求路径
     */
    private String url;


    public HttpEventBean(HttpMethod method, String url) {
        this.method = method;
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpEventBean that = (HttpEventBean) o;
        return Objects.equal(method, that.method) &&
                Objects.equal(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(method, url);
    }
}
