package com.wojustme.llama.core.bean;

import com.google.common.base.Objects;

/**
 * 网络地址
 * @author xurenhe
 * @date 2017/12/29
 */
public class NetAddress {

    /**
     * 地址
     */
    private String host;

    /**
     * 端口
     */
    private int port;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetAddress that = (NetAddress) o;
        return port == that.port &&
                Objects.equal(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(host, port);
    }
}
