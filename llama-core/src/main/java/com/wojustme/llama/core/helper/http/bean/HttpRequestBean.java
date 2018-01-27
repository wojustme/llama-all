package com.wojustme.llama.core.helper.http.bean;

import com.google.common.base.Objects;

/**
 * @author xurenhe
 * @date 2018/1/16
 */
public class HttpRequestBean {

    /**
     * 请求方法
     */
    private HttpMethodEnum method;
    /**
     * 请求路径
     */
    private String url;

    public HttpRequestBean(HttpMethodEnum method, String url) {
        this.method = method;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestBean that = (HttpRequestBean) o;
        return method == that.method &&
                Objects.equal(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(method, url);
    }
}
