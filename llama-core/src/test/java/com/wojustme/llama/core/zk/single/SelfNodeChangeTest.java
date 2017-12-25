package com.wojustme.llama.core.zk.single;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

/**
 * 监听一个节点自身变化
 * @author xurenhe
 * @date 2017/12/22
 */
public class SelfNodeChangeTest {

    private final String ZK_ADDR = "localhost:2181";
    private final String BASE_PATH = "llama_test";

    private CuratorFramework zkClient;

    @Before
    public void init_ZkClient() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1);
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(ZK_ADDR)
                .sessionTimeoutMs(1000)
                .connectionTimeoutMs(1000)
                .retryPolicy(retryPolicy)
                .namespace(BASE_PATH)
                .build();
    }

    @Test
    public void test_createNode() {
        zkClient.start();
        NodeCache nodeCache = new NodeCache(zkClient, "/self_node_create");
        try {
            nodeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(nodeCache.getCurrentData() == null);

        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("create...");
                System.out.println(nodeCache.getCurrentData() == null);
                System.out.println("data will be: " + new String(nodeCache.getCurrentData().getData()));
            }
        });
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_changeData() throws Exception {
        zkClient.start();
        NodeCache nodeCache = new NodeCache(zkClient, "/self_node_change");
        nodeCache.start();
        ChildData currentData = nodeCache.getCurrentData();
        System.out.println(currentData == null);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("changed data...");
                System.out.println(currentData == null);
                System.out.println("data will be: " + new String(currentData.getData()));
                System.out.println("stat will be: " + currentData.getStat() == null);
            }
        });
        Thread.sleep(30 * 1000);
    }

    @Test
    public void test_deleteNode() throws Exception {
        zkClient.start();
        NodeCache nodeCache = new NodeCache(zkClient, "/self_node_del");
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("del node...");
                System.out.println("data will be: " + new String(nodeCache.getCurrentData().getData()));
            }
        });
        Thread.sleep(60 * 1000);
    }
}
