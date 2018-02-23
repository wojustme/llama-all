package com.wojustme.llama.core.worker.net;

/**
 * 网络数据的封装
 * @author xurenhe
 * @date 2018/2/12
 */
public class NetDataBean {
    /**
     * 任务名
     */
    private String taskName;
    /**
     * 传说的数据
     */
    private byte[] transferData;

    public NetDataBean(String taskName, byte[] transferData) {
        this.taskName = taskName;
        this.transferData = transferData;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public byte[] getTransferData() {
        return transferData;
    }

    public void setTransferData(byte[] transferData) {
        this.transferData = transferData;
    }
}
