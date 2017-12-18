package com.wojustme.llama.core.helper.zk;

import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.exception.ZkException;
import com.wojustme.llama.core.helper.serializer.*;
import com.wojustme.llama.core.util.JsonUtils;
import com.wojustme.llama.core.util.ProtoStuffUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * ZK连接器，封装zkClient
 * @author xurenhe
 * @date 2017/11/29
 */
public class ZkConnector {

    /**
     * 真实的zk客户端
     */
    private CuratorFramework zkClient;
    /**
     * 是否该客户端为启动装填
     */
    private boolean isStart;

    /**
     * 该zk连接器维护序列化器
     */
    private LlamaSerializer llamaSerializer;

    public ZkConnector(ZkConfig zkConfig) {
        init(zkConfig);
    }

    private void start() {
        if (isStart) {
            return;
        }
        zkClient.start();
        this.isStart = true;
    }

    /**
     * 初始化ZK相关信息
     * @param zkConfig
     */
    private void init(ZkConfig zkConfig) {
        if (zkConfig == null) {
            throw new ZkException("error connect zk");
        }
        if (StringUtils.isEmpty(zkConfig.getZkConnectAddr())) {
            throw new ZkException("zk address error");
        }
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkConfig.getRetrySleepTimeMs(), zkConfig.getRetryNum());
        this.zkClient = CuratorFrameworkFactory.builder()
                .connectString(zkConfig.getZkConnectAddr())
                .sessionTimeoutMs(zkConfig.getSessionTimeoutMs())
                .connectionTimeoutMs(zkConfig.getConnectionTimeoutMs())
                .retryPolicy(retryPolicy)
                .namespace(zkConfig.getBaseNameSpace())
                .build();
        this.llamaSerializer = SerializerTypeEnum.getSerializer(zkConfig.getSerializerType());
    }

    // 1. 创建节点
    private <T> boolean createNode(String path, T data, CreateMode nodeMode, SerializerTypeEnum serilaizerKind) throws ZkException {
        boolean rs = false;
        byte[] bytes;
        switch (serilaizerKind) {
            case PROTO:
                bytes = ProtoStuffUtils.toByteArr(data);
                break;
            case JSON:
                String jsonStr;
                try {
                    jsonStr = JsonUtils.toJsonStr(data);
                } catch (SerializerException e) {
                    throw new ZkException("to json string error, data: " + data, e);
                }
                bytes = jsonStr.getBytes();
                break;
        }
        return rs;
    }
    // 2. 重新设置节点数据
    // 3. 获取节点数据
    // 4. 删除节点数据
    // 5. 监听该节点变动
    // 6. 监听子节点变动

}
