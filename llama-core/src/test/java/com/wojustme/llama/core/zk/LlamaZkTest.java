package com.wojustme.llama.core.zk;

import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.helper.serializer.LlamaSerializer;
import com.wojustme.llama.core.helper.zk.*;
import com.wojustme.llama.core.util.YamlUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

/**
 * @author xurenhe
 * @date 2017/12/22
 */
public class LlamaZkTest {

    private ZkConfig zkConfig;

    private ZkConnector zkConnector;


    @Before
    public void init_zk() throws Exception {
        String path = Thread.currentThread().getContextClassLoader().getResource("zkconfig.yaml").getPath();
        this.zkConfig = YamlUtils.getPropsFromYamlFile(path, ZkConfig.class);
        this.zkConnector = new ZkConnector(zkConfig);
    }


    @Test
    public void test_getZkConfig() throws Exception {
        System.out.println(zkConfig.getBaseNameSpace());
    }

    @Test
    public void test_createZkNode() throws Exception {
        zkConnector.createNode("/hello/123", "hello", CreateMode.PERSISTENT);
    }


    @Test
    public void test_getZkNodeData() throws Exception {
        String s = zkConnector.readNodeData("/hello", String.class);
        System.out.println(s);
    }

    @Test
    public void test_updateZkNodeData() throws Exception {
        zkConnector.updateNodeData("/hello", "xurenhe");
        zkConnector.deleteNodeData("/hello");
    }

    @Test
    public void test_WatchSelfNode() throws Exception {
        LlamaSerializer llamaSerializer = zkConnector.getLlamaSerializer();
        String path = "/hello";
        ZkEventUpdater zkEventUpdater = zkNodeEvent -> {
            System.out.println(zkNodeEvent.getZkNodeStatusEnum());
            printData(llamaSerializer, zkNodeEvent);
        };
        zkConnector.listernNodeSelf(path, zkEventUpdater);
        Thread.sleep(60 * 1000);
    }


    @Test
    public void test_WatchChildNode() throws Exception {
        LlamaSerializer llamaSerializer = zkConnector.getLlamaSerializer();
        String path = "/hello";
        ZkEventUpdater zkEventUpdater = zkNodeEvent -> {
            System.out.println(zkNodeEvent.getZkNodeStatusEnum());
            printData(llamaSerializer, zkNodeEvent);
        };

        zkConnector.listernNodeChildren(path, zkEventUpdater);
        Thread.sleep(60 * 1000);
    }

    private void printData(LlamaSerializer llamaSerializer, ZkNodeEvent zkNodeEvent) {
        if (zkNodeEvent.getDateBytes() != null) {
            byte[] dateBytes = zkNodeEvent.getDateBytes();
            try {
                System.out.println(llamaSerializer.deserialize(dateBytes, String.class));
            } catch (SerializerException e) {
                e.printStackTrace();
            }
        }
    }
}
