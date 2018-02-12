package com.wojustme.llama.core.worker;

import com.wojustme.llama.api.Bolt;
import com.wojustme.llama.api.OutputCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * bolt类型组件的工作线程
 * @author xurenhe
 * @date 2018/2/12
 */
public class BoltExecutor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoltExecutor.class);

    /**
     * bolt线程名
     */
    private String executorName;
    /**
     * 该线程中包含bolt任务映射
     * boltTaskName => boltInstance
     */
    private Map<String, Bolt> boltInstanceMap;
    /**
     * 该线程中包含bolt任务中网络映射
     * boltTaskName => outputCollector
     */
    private Map<String, OutputCollector> outputCollectorMap;
    /**
     * 用于数据接受
     */
    private BlockingQueue<TaskTransferData> taskDataQueue;


    public BoltExecutor(String executorName, BlockingQueue<TaskTransferData> taskDataQueue) {
        this.executorName = executorName;
        this.taskDataQueue = taskDataQueue;
        this.boltInstanceMap = new HashMap<>();
        this.outputCollectorMap = new HashMap<>();
    }

    public void addBoltTask(String boltTaskName, Bolt boltInstance, OutputCollector outputCollector) {
        boltInstance.init(outputCollector);
        boltInstanceMap.put(boltTaskName, boltInstance);
        outputCollectorMap.put(boltTaskName, outputCollector);
    }


    public BlockingQueue<TaskTransferData> getTaskDataQueue() {
        return taskDataQueue;
    }

    @Override
    public void run() {
        while (true) {
            TaskTransferData taskTransferData = null;
            try {
                taskTransferData = taskDataQueue.take();
            } catch (InterruptedException e) {
                LOGGER.error("bolt executor recv data error", e);
            }
            if (taskTransferData != null) {
                String taskName = taskTransferData.getTaskName();
                Object data = taskTransferData.getData();
                Bolt boltInstance = boltInstanceMap.get(taskName);
                boltInstance.execute(data);
            }
        }
    }
}
