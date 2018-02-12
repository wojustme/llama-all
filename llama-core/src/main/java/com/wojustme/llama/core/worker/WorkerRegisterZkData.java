package com.wojustme.llama.core.worker;

import java.util.List;

/**
 * @author xurenhe
 * @date 2018/2/11
 */
public class WorkerRegisterZkData {
    /**
     * 允许运行spout任务总数量
     */
    private int allowSpoutTaskNum;
    /**
     * 允许运行bolt任务总数量
     */
    private int allowBoltTaskNum;
    /**
     * 增加运行的spout任务列表
     */
    private List<String> activeSpoutTaskNum;
    /**
     * 增加运行的bolt任务列表
     */
    private List<String> activeBoltTaskNum;
}
