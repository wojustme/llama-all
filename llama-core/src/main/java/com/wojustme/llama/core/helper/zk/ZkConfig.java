package com.wojustme.llama.core.helper.zk;

import com.wojustme.llama.core.helper.serializer.SerializerTypeEnum;

/**
 * zk的配置信息
 * @author xurenhe
 * @date 2017/12/7
 */
public class ZkConfig {

    /**
     * zk的连接地址信息
     */
    private String zkConnectAddr;
    /**
     * 基础的命名空间
     */
    private String baseNameSpace;
    /**
     * 会话超时设置
     */
    private int sessionTimeoutMs = -10;
    /**
     * 连接超时参数
     */
    private int connectionTimeoutMs;
    /**
     * 重试睡眠时间
     */
    private int retrySleepTimeMs;
    /**
     * 重试个数
     */
    private int retryNum;
    /**
     * 序列化类型
     * @see com.wojustme.llama.core.helper.serializer.SerializerTypeEnum
     */
    private SerializerTypeEnum serializerType;

    public String getZkConnectAddr() {
        return zkConnectAddr;
    }

    public void setZkConnectAddr(String zkConnectAddr) {
        this.zkConnectAddr = zkConnectAddr;
    }

    public String getBaseNameSpace() {
        return baseNameSpace;
    }

    public void setBaseNameSpace(String baseNameSpace) {
        this.baseNameSpace = baseNameSpace;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public int getRetrySleepTimeMs() {
        return retrySleepTimeMs;
    }

    public void setRetrySleepTimeMs(int retrySleepTimeMs) {
        this.retrySleepTimeMs = retrySleepTimeMs;
    }

    public int getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(int retryNum) {
        this.retryNum = retryNum;
    }

    public SerializerTypeEnum getSerializerType() {
        return serializerType;
    }

    public void setSerializerType(SerializerTypeEnum serializerType) {
        this.serializerType = serializerType;
    }

    @Override
    public String toString() {
        return "ZkConfig{" +
                "zkConnectAddr='" + zkConnectAddr + '\'' +
                ", baseNameSpace='" + baseNameSpace + '\'' +
                ", sessionTimeoutMs=" + sessionTimeoutMs +
                ", connectionTimeoutMs=" + connectionTimeoutMs +
                ", retrySleepTimeMs=" + retrySleepTimeMs +
                ", retryNum=" + retryNum +
                ", serializerType='" + serializerType + '\'' +
                '}';
    }
}
