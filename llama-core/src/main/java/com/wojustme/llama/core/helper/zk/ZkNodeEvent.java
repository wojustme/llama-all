package com.wojustme.llama.core.helper.zk;

/**
 * @author xurenhe
 * @date 2017/12/22
 */
public class ZkNodeEvent {

    /**
     * 节点变化状态
     */
    private ZkNodeStatusEnum zkNodeStatusEnum;
    /**
     * 节点变化数据
     */
    private byte[] dateBytes;

    public ZkNodeEvent(ZkNodeStatusEnum zkNodeStatusEnum, byte[] dateBytes) {
        this.zkNodeStatusEnum = zkNodeStatusEnum;
        this.dateBytes = dateBytes;
    }

    public ZkNodeStatusEnum getZkNodeStatusEnum() {
        return zkNodeStatusEnum;
    }

    public void setZkNodeStatusEnum(ZkNodeStatusEnum zkNodeStatusEnum) {
        this.zkNodeStatusEnum = zkNodeStatusEnum;
    }

    public byte[] getDateBytes() {
        return dateBytes;
    }

    public void setDateBytes(byte[] dateBytes) {
        this.dateBytes = dateBytes;
    }
}
