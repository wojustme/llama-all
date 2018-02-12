package com.wojustme.llama.core.worker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xurenhe
 * @date 2018/2/10
 */
public class WorkerStatus {

    /**
     * 总共申请了spout线程数量
     */
    private final int allSpoutExecutorNum;
    /**
     * 正在使用的spout线程容器
     */
    private List<String> activeSpoutList;

    /**
     * 总共申请了bolt线程数量
     */
    private final int allBoltExecutorNum;
    /**
     * 正在使用的bolt线程数量
     */
    private List<ActiveBoltExecutorStat> activeBoltList;
    /**
     * 每一个bolt线程可以容纳任务的数量
     */
    private final int boltTaskNumEachExecutor;

    public WorkerStatus(int allSpoutExecutorNum, int allBoltExecutorNum, int boltTaskNumEachExecutor) {
        this.allSpoutExecutorNum = allSpoutExecutorNum;
        this.allBoltExecutorNum = allBoltExecutorNum;
        this.boltTaskNumEachExecutor = boltTaskNumEachExecutor;

        this.activeSpoutList = new ArrayList<>();
        this.activeBoltList = new ArrayList<>();
    }

    public int getAllSpoutExecutorNum() {
        return allSpoutExecutorNum;
    }

    public List<String> getActiveSpoutList() {
        return activeSpoutList;
    }

    public int getAllBoltExecutorNum() {
        return allBoltExecutorNum;
    }

    public List<ActiveBoltExecutorStat> getActiveBoltList() {
        return activeBoltList;
    }

    public int getBoltTaskNumEachExecutor() {
        return boltTaskNumEachExecutor;
    }
}
