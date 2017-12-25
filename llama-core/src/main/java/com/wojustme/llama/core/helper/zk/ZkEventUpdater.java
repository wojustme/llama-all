package com.wojustme.llama.core.helper.zk;

/**
 * @author xurenhe
 * @date 2017/12/25
 */
public interface ZkEventUpdater {
    void doUpdate(ZkNodeEvent zkNodeEvent);
}
