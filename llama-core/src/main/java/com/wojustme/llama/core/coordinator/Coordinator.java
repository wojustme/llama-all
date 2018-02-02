package com.wojustme.llama.core.coordinator;

import com.wojustme.llama.core.constants.ZkPathConstant;
import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.helper.http.HttpConnectServer;
import com.wojustme.llama.core.helper.zk.ZkConfig;
import com.wojustme.llama.core.helper.zk.ZkConnector;
import com.wojustme.llama.core.util.YamlUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;

/**
 * 协调者：任务调度
 *
 * @author xurenhe
 * @date 2017/12/29
 */
public class Coordinator {

    /**
     * 维护zk连接器
     */
    private ZkConnector zkConnector;

    /**
     * 需要上报zk，协调者的状态
     */
    private CoordinatorStatus coordinatorStatus;

    /**
     * 协调者的配置信息
     */
    private CoordinatorConfig coordinatorConfig;

    public Coordinator(CoordinatorConfig coordinatorConfig) {
        this.coordinatorConfig = coordinatorConfig;
        this.zkConnector = new ZkConnector(coordinatorConfig.getZkConfig());
    }

    public void start() {
        // todo xurenhe 未做校验
        init();
    }

    /**
     * coordinator进程启动
     */
    private void init() {
        coordinatorConfig.setCoordinatorEventHandler(new CoordinatorEventHandler());
        // 1. 创建nodes永久节点，存在跳过
        createPathNodes();
        // 2. 创建coordinator临时节点, 并写入数据
        createPathCoordinator();
        // 3. 创建workers永久节点，存在跳过
        createPathWorkers();
        // 4. 创建topologies永久节点，存在跳过
        createPathTopologies();
        // 5. 创建assignments永久节点，存在跳过
        createPathAssignments();
        // 6. 创建HTTP服务器，用于topology的jar包提交
        createTopologyHttpServer();
    }

    private void createPathNodes() {
        zkConnector.createNode(ZkPathConstant.NODES_PATH, StringUtils.EMPTY, CreateMode.PERSISTENT, true);
    }

    private void createPathCoordinator() {
        zkConnector.createNode(ZkPathConstant.COORDINATOR_PATH, coordinatorStatus, CreateMode.EPHEMERAL);
    }

    private void createPathWorkers() {
        zkConnector.createNode(ZkPathConstant.WORKERS_PATH, StringUtils.EMPTY, CreateMode.PERSISTENT, true);
    }

    private void createPathTopologies() {
        zkConnector.createNode(ZkPathConstant.TOPOLOGIES_PATH, StringUtils.EMPTY, CreateMode.PERSISTENT, true);
    }

    private void createPathAssignments() {
        zkConnector.createNode(ZkPathConstant.ASSIGNMENTS_PATH, StringUtils.EMPTY, CreateMode.PERSISTENT, true);
    }

    private void createTopologyHttpServer() {
        HttpConnectServer httpConnectServer = new HttpConnectServer(coordinatorConfig);
        new Thread(httpConnectServer).start();
    }

    public ZkConnector getZkConnector() {
        return zkConnector;
    }


    public static void main(String[] args) throws SerializerException {
        String path = Thread.currentThread().getContextClassLoader().getResource("zkconfig.yaml").getPath();
        ZkConfig zkConfig = YamlUtils.getPropsFromYamlFile(path, ZkConfig.class);

        CoordinatorConfig coordinatorConfig = new CoordinatorConfig();
        coordinatorConfig.setHttpServerPort(10001);
        coordinatorConfig.setUploadFileSavePath("./");
        coordinatorConfig.setZkConfig(zkConfig);


        Coordinator coordinator = new Coordinator(coordinatorConfig);
        coordinator.start();
    }
}
