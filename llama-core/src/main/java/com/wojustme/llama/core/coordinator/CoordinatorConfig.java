package com.wojustme.llama.core.coordinator;

import com.wojustme.llama.core.helper.zk.ZkConfig;

/**
 * @author xurenhe
 * @date 2018/1/30
 */
public class CoordinatorConfig {

    /**
     * 启动端口
     */
    private int httpServerPort;

    /**
     * 上传文件的根路径
     */
    private String uploadFileSavePath;

    /**
     * zk相关配置信息
     */
    private ZkConfig zkConfig;

    /**
     * 事件统一处理器
     */
    private CoordinatorEventHandler coordinatorEventHandler;

    public int getHttpServerPort() {
        return httpServerPort;
    }

    public void setHttpServerPort(int httpServerPort) {
        this.httpServerPort = httpServerPort;
    }

    public String getUploadFileSavePath() {
        return uploadFileSavePath;
    }

    public void setUploadFileSavePath(String uploadFileSavePath) {
        this.uploadFileSavePath = uploadFileSavePath;
    }

    public ZkConfig getZkConfig() {
        return zkConfig;
    }

    public void setZkConfig(ZkConfig zkConfig) {
        this.zkConfig = zkConfig;
    }

    public CoordinatorEventHandler getCoordinatorEventHandler() {
        return coordinatorEventHandler;
    }

    public void setCoordinatorEventHandler(CoordinatorEventHandler coordinatorEventHandler) {
        this.coordinatorEventHandler = coordinatorEventHandler;
    }
}
