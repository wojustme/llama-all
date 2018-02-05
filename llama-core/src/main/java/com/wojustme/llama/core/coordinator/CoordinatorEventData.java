package com.wojustme.llama.core.coordinator;

/**
 * @author xurenhe
 * @date 2018/2/3
 */
public class CoordinatorEventData<T> {

    /**
     * 协调者事件
     */
    private CoordinatorEvent coordinatorEvent;

    /**
     * 事件数据
     */
    private T msg;

    public CoordinatorEventData(CoordinatorEvent coordinatorEvent, T msg) {
        this.coordinatorEvent = coordinatorEvent;
        this.msg = msg;
    }

    public CoordinatorEvent getCoordinatorEvent() {
        return coordinatorEvent;
    }

    public void setCoordinatorEvent(CoordinatorEvent coordinatorEvent) {
        this.coordinatorEvent = coordinatorEvent;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
