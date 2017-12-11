package com.wojustme.llama.core.util.zk;

import com.wojustme.llama.core.util.serializer.JsonUtils;
import com.wojustme.llama.core.util.serializer.ProtoStuffUtils;
import com.wojustme.llama.core.util.serializer.SerializerException;
import com.wojustme.llama.core.util.serializer.SerializerKindEnum;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author xurenhe
 * @date 2017/11/29
 */
public class ZkClient {

    // 真实的zk客户端连接器
    private CuratorFramework realZkClient;
    // 是否该客户端为启动装填
    private boolean isStart;

    public ZkClient(CuratorFramework realZkClient) {
        this.realZkClient = realZkClient;
    }

    public void start() {
        if (isStart) {
            return;
        }
        realZkClient.start();
        this.isStart = true;
    }

    // 1. 创建节点
    private <T> boolean createNode(String path, T data, CreateMode nodeMode, SerializerKindEnum serilaizerKind) throws ZkException {
        boolean rs = false;
        byte[] bytes;
        switch (serilaizerKind) {
            case PROTO:
                bytes = ProtoStuffUtils.serializer(data);
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
