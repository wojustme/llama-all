package com.wojustme.llama.core.worker;

import com.wojustme.llama.api.OutputCollector;
import com.wojustme.llama.api.Spout;

/**
 * spout类型组件的工作线程
 * @author xurenhe
 * @date 2018/2/12
 */
public class SpoutExecutor implements Runnable {

    /**
     * spout线程名
     */
    private String executorName;
    /**
     * 当前spout运行线程
     * 一个SpoutExecutor只运行一个spout实例
     */
    private Spout spoutInstance;
    /**
     * 该spout执行线程维护的网络引用
     */
    private OutputCollector outputCollector;


    public SpoutExecutor(String executorName, Spout spoutInstance, OutputCollector outputCollector) {
        this.executorName = executorName;
        this.spoutInstance = spoutInstance;
        this.outputCollector = outputCollector;
        // 设置运行实体的网络引用
        this.spoutInstance.init(outputCollector);
    }

    @Override
    public void run() {
        spoutInstance.nextTuple();
    }
}
