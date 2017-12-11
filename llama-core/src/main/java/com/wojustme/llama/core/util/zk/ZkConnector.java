package com.wojustme.llama.core.util.zk;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * zk的连接器
 * @author xurenhe
 * @date 2017/12/7
 */
public class ZkConnector {

    // zk的连接地址信息
    private String zkConnectAddr;
    // 基础的命名空间
    private String baseNameSpace;

    // 各个配置属性
    // 会话超时设置
    private int sessionTimeoutMs;
    // 连接超时参数
    private int connectionTimeoutMs;
    // 重试睡眠时间
    private int retrySleepTimeMs;
    // 重试个数
    private int retryNum;

    private volatile ZkClient zkClient;

    // 获得zk的
    public ZkClient getZkClient() throws ZkException {
        if (zkClient == null) {
            synchronized (this) {
                if (zkClient == null) {
                    zkClient = generateZkClient();
                }
            }
        }
        return zkClient;
    }

    private ZkClient generateZkClient() throws ZkException {
        if (StringUtils.isEmpty(zkConnectAddr)) {
            throw new ZkException("input illegal zkConnectAddr");
        }
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(retrySleepTimeMs, retryNum);
        CuratorFramework _realZkClient = CuratorFrameworkFactory.builder()
                .connectString(zkConnectAddr)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace(baseNameSpace)
                .build();
        return new ZkClient(_realZkClient);
    }
}
