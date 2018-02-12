package com.wojustme.llama.core.worker;

import com.wojustme.llama.core.constants.ZkPathConstant;
import com.wojustme.llama.core.exception.NetException;
import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.helper.net.MsgServer;
import com.wojustme.llama.core.helper.net.handler.MsgServerHandler;
import com.wojustme.llama.core.helper.net.msg.MsgCoder;
import com.wojustme.llama.core.helper.zk.ZkConfig;
import com.wojustme.llama.core.helper.zk.ZkConnector;
import com.wojustme.llama.core.util.YamlUtils;
import com.wojustme.llama.core.worker.conf.WorkerConfig;
import com.wojustme.llama.core.worker.event.WorkerEvent;
import com.wojustme.llama.core.worker.event.WorkerEventData;
import org.apache.zookeeper.CreateMode;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工作者
 * @author xurenhe
 * @date 2017/12/29
 */
public class Worker {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private WorkerData workerData;


    public Worker(WorkerConfig workerConfig) {
        this.workerData = WorkerData.buildWorkerData(workerConfig);
    }

    public void start() {
        init();
    }
    private void init() {
        EventBus.getDefault().register(this);
        // 创建workers-${workerName}临时节点
        createPathWorker();
        // 创建assignment-${workerName}临时节点
        createPathAssignmentWorker();
        // 启动netty服务器
        startNetMsgServer();
    }

    private void startNetMsgServer() {
        int port = workerData.getNetAddress().getPort();
        MsgServerHandler msgServerHandler = workerData.getMsgServerHandler();
        MsgCoder msgCoder = workerData.getMsgCoder();
        MsgServer msgServer = new MsgServer(port, msgServerHandler, msgCoder);
        try {
            msgServer.start();
        } catch (NetException e) {
            LOGGER.error("start netty msg server error", e);
        }
    }

    private void createPathAssignmentWorker() {
        ZkConnector zkConnector = workerData.getZkConnector();
        String path = ZkPathConstant.ASSIGNMENTS_PATH + "/" + workerData.getWorkerName();
        if (!zkConnector.checkZkNodeIsExist(path)) {
            zkConnector.createNode(path, "", CreateMode.EPHEMERAL);
        }
    }

    private void createPathWorker() {
        ZkConnector zkConnector = workerData.getZkConnector();
        String path = ZkPathConstant.WORKERS_PATH + "/" + workerData.getWorkerName();
        if (!zkConnector.checkZkNodeIsExist(path)) {
            zkConnector.createNode(path, "", CreateMode.EPHEMERAL);
        }
    }

    @Subscribe
    public void recvEvent(WorkerEventData workerEventData) {
        WorkerEvent workerEvent = workerEventData.getWorkerEvent();
        if (workerEvent == WorkerEvent.REVC_DATA) {
            WorkerExecutorManager workerExecutorManager = workerData.getWorkerExecutorManager();
        }
    }

    public static void main(String[] args) throws SerializerException {
        String path = Thread.currentThread().getContextClassLoader().getResource("zkconfig.yaml").getPath();
        ZkConfig zkConfig = YamlUtils.getPropsFromYamlFile(path, ZkConfig.class);
        WorkerConfig workerConfig = new WorkerConfig();
        workerConfig.setZkConfig(zkConfig);
        workerConfig.setPort(11112);
        workerConfig.setSpoutExecutorNum(4);
        workerConfig.setBoltExecutorNum(4);
        workerConfig.setEachBoltOfTaskNum(4);
        workerConfig.setWorkerId("1");
        Worker worker = new Worker(workerConfig);
        worker.start();
    }
}
