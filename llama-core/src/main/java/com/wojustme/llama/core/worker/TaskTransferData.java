package com.wojustme.llama.core.worker;

/**
 * 主线程与工作线程通信数据封装
 * @author xurenhe
 * @date 2018/2/12
 */
public class TaskTransferData {
    /**
     * 数据阻塞数据最大量
     */
    public static final int MAX_CAPACITY = 1000;
    /**
     * 任务名
     */
    private String taskName;
    /**
     * 真实需要传输的数据
     */
    private Object data;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
