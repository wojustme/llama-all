package com.wojustme.llama.core.worker;

import com.wojustme.llama.core.helper.zk.ZkConnector;

/**
 * @author xurenhe
 * @date 2018/2/10
 */
public class WorkerData {

    private String workerName;

    private ZkConnector zkConnector;

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public ZkConnector getZkConnector() {
        return zkConnector;
    }

    public void setZkConnector(ZkConnector zkConnector) {
        this.zkConnector = zkConnector;
    }
}
