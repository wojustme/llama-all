package com.wojustme.llama.core.worker;

/**
 * @author xurenhe
 * @date 2018/2/10
 */
public class WorkStatus {

    /**
     * 工作进程名
     */
    private String workerName;
    /**
     * 总共申请了spout线程数量
     */
    private int allSpoutExecutorNum;
    /**
     * 正在使用的spout线程数量
     */
    private int activeSpoutExecutorNum;

}
