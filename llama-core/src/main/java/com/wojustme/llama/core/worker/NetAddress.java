package com.wojustme.llama.core.worker;

/**
 * @author xurenhe
 * @date 2018/2/10
 */
public class NetAddress {
    /**
     * ip或者host名
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
}
