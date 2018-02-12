package com.wojustme.llama.core.worker;

import com.wojustme.llama.core.worker.net.NetRecvDataBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * 工作线程管理器
 * @author xurenhe
 * @date 2018/2/12
 */
public class WorkerExecutorManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerExecutorManager.class);

    /**
     * 正在运行的spout的任务容器
     * spoutTaskName => spoutExecutor
     */
    private Map<String, SpoutExecutor> runSpoutTaskMap;
    /**
     * 正在运行的spout的任务容器
     * boltTaskName => boltExecutor
     */
    private Map<String, BoltExecutor> runBoltTaskMap;


    public WorkerExecutorManager() {
    }

    public void addSpoutTask() {

    }

    public void addBoltTask() {

    }

    public void receiveData(NetRecvDataBean netRecvDataBean) {
        String taskName = netRecvDataBean.getTaskName();
        BoltExecutor boltExecutor = runBoltTaskMap.get(taskName);
        if (boltExecutor != null) {
            BlockingQueue<TaskTransferData> taskDataQueue = boltExecutor.getTaskDataQueue();
            TaskTransferData taskTransferData = new TaskTransferData();
            taskTransferData.setTaskName(taskName);
            taskTransferData.setData(netRecvDataBean.getTransferData());
            taskDataQueue.add(taskTransferData);
        }
    }
}
