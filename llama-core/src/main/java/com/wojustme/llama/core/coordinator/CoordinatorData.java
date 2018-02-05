package com.wojustme.llama.core.coordinator;

import com.wojustme.llama.core.helper.zk.ZkConnector;

/**
 * @author xurenhe
 * @date 2018/2/3
 */
public class CoordinatorData {
    /**
     * 协调者的事件分发器
     */
    private CoordinatorEventDispatch coordinatorEventDispatch;

    /**
     * 协调者的配置信息
     */
    private CoordinatorConfig coordinatorConfig;

    /**
     * 维护zk连接器
     */
    private ZkConnector zkConnector;

    /**
     * 需要上报zk，协调者的状态
     */
    private CoordinatorStatus coordinatorStatus;

    public CoordinatorEventDispatch getCoordinatorEventDispatch() {
        return coordinatorEventDispatch;
    }

    public void setCoordinatorEventDispatch(CoordinatorEventDispatch coordinatorEventDispatch) {
        this.coordinatorEventDispatch = coordinatorEventDispatch;
    }

    public CoordinatorConfig getCoordinatorConfig() {
        return coordinatorConfig;
    }

    public void setCoordinatorConfig(CoordinatorConfig coordinatorConfig) {
        this.coordinatorConfig = coordinatorConfig;
    }

    public ZkConnector getZkConnector() {
        return zkConnector;
    }

    public void setZkConnector(ZkConnector zkConnector) {
        this.zkConnector = zkConnector;
    }

    public CoordinatorStatus getCoordinatorStatus() {
        return coordinatorStatus;
    }

    public void setCoordinatorStatus(CoordinatorStatus coordinatorStatus) {
        this.coordinatorStatus = coordinatorStatus;
    }

    public static CoordinatorData buildCoordinatorData(CoordinatorConfig coordinatorConfig) {
        CoordinatorData coordinatorData = new CoordinatorData();
        coordinatorData.setCoordinatorConfig(coordinatorConfig);
        coordinatorData.setZkConnector(new ZkConnector(coordinatorConfig.getZkConfig()));
        coordinatorData.setCoordinatorEventDispatch(CoordinatorEventDispatch.getInstance());
        return coordinatorData;
    }
}
