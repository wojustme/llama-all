package com.wojustme.llama.core.worker.event;


/**
 * @author xurenhe
 * @date 2018/2/3
 */
public class WorkerEventData<T> {

    /**
     * 工作者事件
     */
    private WorkerEvent workerEvent;

    /**
     * 事件数据
     */
    private T msg;

    public WorkerEventData(WorkerEvent workerEvent, T msg) {
        this.workerEvent = workerEvent;
        this.msg = msg;
    }

    public WorkerEvent getWorkerEvent() {
        return workerEvent;
    }

    public void setWorkerEvent(WorkerEvent workerEvent) {
        this.workerEvent = workerEvent;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
