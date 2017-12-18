package com.wojustme.llama.core.zk;

import com.wojustme.llama.core.util.YamlUtils;
import com.wojustme.llama.core.helper.zk.ZkConfig;
import org.junit.Test;

/**
 * @author xurenhe
 * @date 2017/12/15
 */
public class ZkConfigTest {

    @Test
    public void test_readConfigFromYaml() throws Exception {
        String path = Thread.currentThread().getContextClassLoader().getResource("zkconfig.yaml").getPath();
        ZkConfig zkConfig = YamlUtils.getPropsFromYamlFile(path, ZkConfig.class);
        System.out.println(zkConfig);
    }
}
