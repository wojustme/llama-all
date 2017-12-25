package com.wojustme.llama.core.zk.single;

import com.wojustme.llama.core.util.JsonUtils;
import com.wojustme.llama.core.helper.serializer.Person;
import com.wojustme.llama.core.util.ProtoStuffUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

/**
 * @author xurenhe
 * @date 2017/12/7
 */
public class CuratorTest {
    private final String zk_addr = "localhost:2181";
    private final String base_path = "llama_test";
    private CuratorFramework zkClient;

    @Before
    public void setUp() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        zkClient =CuratorFrameworkFactory.builder()
                .connectString(zk_addr)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace(base_path)
                .build();
    }

    @Test
    public void test_createNode() throws Exception {
        zkClient.start();
        Person person = new Person();
        person.setName("xurenhe");
        person.setAge(10);
//        zkClient.create().forPath("/person", ProtoStuffUtils.toByteArr(person));
        zkClient.create().withMode(CreateMode.EPHEMERAL).forPath("/person123", JsonUtils.toJsonStr(person).getBytes());
    }

    @Test
    public void test_getData() throws Exception {
        zkClient.start();
        byte[] bytes = zkClient.getData().forPath("/person");
        byte[] bytes2 = zkClient.getData().forPath("/person123");
        Person person = ProtoStuffUtils.toBeanObj(bytes, Person.class);
        System.out.println(person);
    }

    @Test
    public void test_writeData() {
        
    }
}
