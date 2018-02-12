package com.wojustme.llama.core.worker.conf;

import com.wojustme.llama.core.helper.zk.ZkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xurenhe
 * @date 2018/2/11
 */
public class WorkerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerConfig.class);
    /**
     * 工作进程id
     */
    private String workerId;
    /**
     * 设置的spout进程数目
     */
    private int spoutExecutorNum;
    /**
     * 设置的bolt进程数目
     */
    private int boltExecutorNum;
    /**
     * 每一个bolt进程可容纳任务数目
     */
    private int eachBoltOfTaskNum;
    /**
     * 网络通信ip地址
     */
    private int port;

    private ZkConfig zkConfig;


    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public int getSpoutExecutorNum() {
        return spoutExecutorNum;
    }

    public void setSpoutExecutorNum(int spoutExecutorNum) {
        this.spoutExecutorNum = spoutExecutorNum;
    }

    public int getBoltExecutorNum() {
        return boltExecutorNum;
    }

    public void setBoltExecutorNum(int boltExecutorNum) {
        this.boltExecutorNum = boltExecutorNum;
    }

    public int getEachBoltOfTaskNum() {
        return eachBoltOfTaskNum;
    }

    public void setEachBoltOfTaskNum(int eachBoltOfTaskNum) {
        this.eachBoltOfTaskNum = eachBoltOfTaskNum;
    }

    public String getHostName() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error("get host name error", e);
        }
        return hostName;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ZkConfig getZkConfig() {
        return zkConfig;
    }

    public void setZkConfig(ZkConfig zkConfig) {
        this.zkConfig = zkConfig;
    }
}
