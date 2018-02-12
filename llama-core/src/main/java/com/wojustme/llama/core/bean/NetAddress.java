package com.wojustme.llama.core.bean;

/**
 * @author xurenhe
 * @date 2018/2/10
 */
public class NetAddress {
    /**
     * ip或者host名
     */
    private final String host;
    /**
     * 端口
     */
    private final int port;

    public NetAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
