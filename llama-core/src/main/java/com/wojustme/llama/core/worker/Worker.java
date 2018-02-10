package com.wojustme.llama.core.worker;

import com.wojustme.llama.core.constants.ZkPathConstant;
import com.wojustme.llama.core.helper.zk.ZkConnector;
import org.apache.zookeeper.CreateMode;

/**
 * 工作者
 * @author xurenhe
 * @date 2017/12/29
 */
public class Worker {

    private WorkerData workerData;

    private void init() {
        // 创建workers-${workerName}临时节点
        createPathWorker();
        // 创建assignment-${workerName}临时节点
        createPathAssignmentWorker();
    }

    private void createPathAssignmentWorker() {
        ZkConnector zkConnector = workerData.getZkConnector();
        String path = ZkPathConstant.ASSIGNMENTS_PATH + "/" + workerData.getWorkerName();
        if (!zkConnector.checkZkNodeIsExist(path)) {
            zkConnector.createNode(path, "", CreateMode.EPHEMERAL);
        }
    }

    private void createPathWorker() {
        ZkConnector zkConnector = workerData.getZkConnector();
        String path = ZkPathConstant.WORKERS_PATH + "/" + workerData.getWorkerName();
        if (!zkConnector.checkZkNodeIsExist(path)) {
            zkConnector.createNode(path, "", CreateMode.EPHEMERAL);
        }
    }
}
