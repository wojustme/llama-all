package com.wojustme.llama.core.helper.zk;

import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.exception.ZkException;
import com.wojustme.llama.core.helper.serializer.*;
import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * ZK连接器，封装zkClient
 *
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
     *
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

    /**
     * 序列化对应的数据
     *
     * @param data
     * @param <T>
     * @return
     * @throws SerializerException
     */
    private <T> byte[] prepareDataBytes(T data) throws SerializerException {
        byte[] bytes;
        if (data != null) {
            bytes = llamaSerializer.serialize(data);
        } else {
            bytes = llamaSerializer.serialize(StringUtils.EMPTY);
        }
        return bytes;
    }

    /**
     * 1. 创建节点
     *
     * @param path
     * @param data
     * @param nodeMode
     * @param <T>
     * @return
     * @throws ZkException
     */
    public <T> boolean createNode(String path, T data, CreateMode nodeMode) throws ZkException {
        boolean rs;
        try {
            byte[] bytes = prepareDataBytes(data);
            start();
            zkClient.create().withMode(nodeMode).forPath(path, bytes);
            rs = true;
        } catch (SerializerException e) {
            throw new ZkException(e);
        } catch (Exception e) {
            throw new ZkException(e);
        }
        return rs;
    }

    /**
     * 2. 读取节点上数据
     *
     * @param path
     * @param clazz
     * @param <T>
     * @return
     * @throws ZkException
     */
    public <T> T readNodeData(String path, Class<T> clazz) throws ZkException {
        T rsData;
        start();
        try {
            byte[] bytes = zkClient.getData().forPath(path);
            rsData = llamaSerializer.deserialize(bytes, clazz);
        } catch (SerializerException e) {
            throw new ZkException(e);
        } catch (Exception e) {
            throw new ZkException(e);
        }
        return rsData;
    }

    /**
     * 3. 更新节点数据
     *
     * @param path
     * @param data
     * @param <T>
     * @return
     * @throws ZkException
     */
    public <T> boolean updateNodeData(String path, T data) throws ZkException {
        boolean rs;
        try {
            byte[] bytes = prepareDataBytes(data);
            start();
            zkClient.setData().forPath(path, bytes);
            rs = true;
        } catch (SerializerException e) {
            throw new ZkException(e);
        } catch (Exception e) {
            throw new ZkException(e);
        }
        return rs;
    }

    /**
     * 4. 删除节点数据
     *
     * @param path
     * @return
     * @throws ZkException
     */
    public boolean deleteNodeData(String path) throws ZkException {
        boolean rs;
        try {
            start();
            zkClient.delete().forPath(path);
            rs = true;
        } catch (Exception e) {
            throw new ZkException(e);
        }
        return rs;
    }

    /**
     * 5. 判断某个路径是佛存在
     *
     * @param path
     * @return
     */
    public boolean checkZkNodeIsExist(String path) {
        boolean rs = false;
        try {
            start();
            Stat stat = zkClient.checkExists().forPath(path);
            if (stat != null) {
                rs = true;
            }
        } catch (Exception e) {
            throw new ZkException(e);
        }
        return rs;
    }

    /**
     * 6. 监听该节点变动(事件通知器模式)
     *
     * @param path
     * @param zkEventUpdater
     * @throws ZkException
     */
    public void listernOnSelfNodeWithDefault(String path, ZkEventUpdater zkEventUpdater) throws ZkException {
        start();
        NodeCache nodeCache = new NodeCache(zkClient, path);
        try {
            nodeCache.start();
        } catch (Exception e) {
            throw new ZkException("listen to node error, path: " + path, e);
        }
        nodeCache.getListenable().addListener(() -> {
            ChildData currentData = nodeCache.getCurrentData();
            if (currentData == null) {
                zkEventUpdater.doUpdate(new ZkNodeEvent(ZkNodeStatusEnum.SELF_DELETE, null));
            } else {
                zkEventUpdater.doUpdate(new ZkNodeEvent(ZkNodeStatusEnum.SELF_CHANGE, currentData.getData()));
            }
        });
    }

    /**
     * 6. 监听该节点变动(rx接口模式)
     *
     * @param path
     * @return
     * @throws ZkException
     */
    public Observable<ZkNodeEvent> listernOnSelfNodeWithRx(String path) throws ZkException {

        start();
        NodeCache nodeCache = new NodeCache(zkClient, path);
        try {
            nodeCache.start();
        } catch (Exception e) {
            throw new ZkException("listen to node error, path: " + path, e);
        }
        return Observable.create(observableEmitter -> nodeCache.getListenable().addListener(() -> {
            ChildData currentData = nodeCache.getCurrentData();
            ZkNodeEvent zkNodeEvent;
            if (currentData == null) {
                zkNodeEvent = new ZkNodeEvent(ZkNodeStatusEnum.SELF_DELETE, null);
            } else {
                zkNodeEvent = new ZkNodeEvent(ZkNodeStatusEnum.SELF_CHANGE, currentData.getData());
            }
            observableEmitter.onNext(zkNodeEvent);
        }));
    }

    /**
     * 7. 监听子节点变动(事件通知器模式)
     *
     * @param path
     * @param zkEventUpdater
     */
    public void listernOnNodeChildrenWithDefault(String path, ZkEventUpdater zkEventUpdater) {
        start();
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, path, true);
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            throw new ZkException("listen to node's children error, path: " + path, e);
        }
        pathChildrenCache.getListenable().addListener((client, event) -> {
            ChildData childData = event.getData();
            switch (event.getType()) {
                case CHILD_ADDED:
                    zkEventUpdater.doUpdate(new ZkNodeEvent(ZkNodeStatusEnum.CHILD_ADD, childData.getData()));
                    break;
                case CHILD_REMOVED:
                    zkEventUpdater.doUpdate(new ZkNodeEvent(ZkNodeStatusEnum.CHILD_REMOVE, null));
                    break;
                case CHILD_UPDATED:
                    zkEventUpdater.doUpdate(new ZkNodeEvent(ZkNodeStatusEnum.CHILD_UPDATE, childData.getData()));
                    break;
                default:
                    zkEventUpdater.doUpdate(new ZkNodeEvent(ZkNodeStatusEnum.CHILD_UPDATE, null));
            }
        });
    }

    /**
     * 7. 监听子节点变动(rx接口模式)
     *
     * @param path
     */
    public Observable<ZkNodeEvent> listernOnNodeChildrenWithRx(String path) {
        start();
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, path, true);
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            throw new ZkException("listen to node's children error, path: " + path, e);
        }
        return Observable.create(observableEmitter -> pathChildrenCache.getListenable().addListener((client, event) -> {
            ChildData childData = event.getData();
            ZkNodeEvent zkNodeEvent;
            switch (event.getType()) {
                case CHILD_ADDED:
                    zkNodeEvent = new ZkNodeEvent(ZkNodeStatusEnum.CHILD_ADD, childData.getData());
                    break;
                case CHILD_REMOVED:
                    zkNodeEvent = new ZkNodeEvent(ZkNodeStatusEnum.CHILD_REMOVE, null);
                    break;
                case CHILD_UPDATED:
                    zkNodeEvent = new ZkNodeEvent(ZkNodeStatusEnum.CHILD_UPDATE, childData.getData());
                    break;
                default:
                    zkNodeEvent = new ZkNodeEvent(ZkNodeStatusEnum.CHILD_UPDATE, null);
            }
            observableEmitter.onNext(zkNodeEvent);
        }));

    }

    public LlamaSerializer getLlamaSerializer() {
        return llamaSerializer;
    }
}
