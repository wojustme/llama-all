package com.wojustme.llama.core.coordinator;

import com.wojustme.llama.core.constants.ZkPathConstant;
import com.wojustme.llama.core.helper.zk.ZkConnector;
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

    private CoordinatorStatus coordinatorStatus;

    public Coordinator() {
        init();
    }

    /**
     * coordinator进程启动
     */
    private void init() {
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
    }

    public ZkConnector getZkConnector() {
        return zkConnector;
    }
}