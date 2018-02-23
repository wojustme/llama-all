package com.wojustme.llama.core.worker.net;

import com.wojustme.llama.core.bean.NetAddress;

/**
 * @author xurenhe
 * @date 2018/2/23
 */
public class DownstreamNetNode {
    /**
     * 下游任务名
     */
    private final String taskName;
    /**
     * 下游节点主机
     */
    private final NetAddress netAddress;

    public DownstreamNetNode(String taskName, NetAddress netAddress) {
        this.taskName = taskName;
        this.netAddress = netAddress;
    }

    public String getTaskName() {
        return taskName;
    }

    public NetAddress getNetAddress() {
        return netAddress;
    }
}
