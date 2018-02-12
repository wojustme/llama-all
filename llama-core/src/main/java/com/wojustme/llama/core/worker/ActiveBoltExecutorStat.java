package com.wojustme.llama.core.worker;

import java.util.List;

/**
 * @author xurenhe
 * @date 2018/2/11
 */
public class ActiveBoltExecutorStat {
    /**
     * bolt线程名
     */
    private String executorName;
    /**
     * 允许任务数量最大
     */
    private int allowTaskTotalNum;
    /**
     * 正在运行任务数量名
     */
    private List<String> activeTaskList;


}
