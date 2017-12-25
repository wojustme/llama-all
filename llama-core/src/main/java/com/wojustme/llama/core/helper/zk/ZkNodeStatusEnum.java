package com.wojustme.llama.core.helper.zk;

/**
 * @author xurenhe
 * @date 2017/12/22
 */
public enum ZkNodeStatusEnum {

    /**
     * 创建节点
     */
    SELF_CREATE,

    /**
     * 更新节点
     */
    SELF_CHANGE,

    /**
     * 节点删除
     */
    SELF_DELETE,

    /**
     * 节点未知
     */
    SELF_UNKNOW,

    /**
     * 子节点添加
     */
    CHILD_ADD,

    /**
     * 子节点移除
     */
    CHILD_REMOVE,

    /**
     * 子节点更新
     */
    CHILD_UPDATE,

    /**
     * 子节点未知
     */
    CHILD_UNKNOW
}
