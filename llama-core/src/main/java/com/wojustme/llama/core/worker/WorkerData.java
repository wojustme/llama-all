package com.wojustme.llama.core.worker;

import com.wojustme.llama.core.bean.NetAddress;
import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.helper.net.handler.MsgServerHandler;
import com.wojustme.llama.core.helper.net.msg.MsgBean;
import com.wojustme.llama.core.helper.net.msg.MsgBeanCoder;
import com.wojustme.llama.core.helper.net.msg.MsgCoder;
import com.wojustme.llama.core.helper.net.msg.MsgType;
import com.wojustme.llama.core.helper.serializer.HessianSerializer;
import com.wojustme.llama.core.helper.serializer.LlamaSerializer;
import com.wojustme.llama.core.helper.zk.ZkConnector;
import com.wojustme.llama.core.worker.conf.WorkerConfig;
import com.wojustme.llama.core.worker.event.WorkerEvent;
import com.wojustme.llama.core.worker.event.WorkerEventData;
import com.wojustme.llama.core.worker.net.NetDataBean;
import io.netty.channel.ChannelHandlerContext;
import org.greenrobot.eventbus.EventBus;

/**
 * @author xurenhe
 * @date 2018/2/10
 */
public class WorkerData {

    /**
     * 工作进程名
     */
    private String workerName;

    /**
     * zk连接器
     */
    private ZkConnector zkConnector;

    /**
     * 当前work状态数据
     */
    private WorkerStatus workerStatus;

    /**
     * 开放的网络信息
     */
    private NetAddress netAddress;

    /**
     * 网络数据分发器
     */
    private NetDataDispatcher netDataDispatcher;
    /**
     * 网络数据处理器
     */
    private MsgServerHandler msgServerHandler;
    /**
     * 网络数据序列化器
     */
    private LlamaSerializer netSerializer = new HessianSerializer();
    /**
     * 任务线程管理器
     */
    private WorkerExecutorManager workerExecutorManager;

    public WorkerData(WorkerConfig workerConfig) {
        this.workerName = buildWorkerName(workerConfig);
        this.workerStatus = buildWorkerStatus(workerConfig);
        this.netAddress = buildNetAddress(workerConfig);
        this.workerExecutorManager = new WorkerExecutorManager();
        this.zkConnector = new ZkConnector(workerConfig.getZkConfig());
        this.msgServerHandler = new MsgServerHandler<MsgBean>() {
            @Override
            public void handleMsg(ChannelHandlerContext ctx, MsgBean msgBean) {
                MsgType msgType = msgBean.getMsgType();
                if (msgType == MsgType.SEND_DATA) {
                    try {
                        NetDataBean data = netSerializer.deserialize(msgBean.getMsgData(), NetDataBean.class);
                        EventBus.getDefault().post(new WorkerEventData(WorkerEvent.REVC_DATA, data));
                    } catch (SerializerException e) {
                        e.printStackTrace();
                    }
                    ctx.writeAndFlush(new MsgBean(MsgType.RECV_OK, null));
                }
            }

            @Override
            public void handleError(ChannelHandlerContext ctx, Throwable cause) {

            }

            @Override
            public ChannelHandlerContext getChannelHandlerContext() {
                return null;
            }
        };
    }

    private String buildWorkerName(WorkerConfig workerConfig) {
        return "worker-" + workerConfig.getWorkerId();
    }
    private WorkerStatus buildWorkerStatus(WorkerConfig workerConfig) {
        int spoutExecutorNum = workerConfig.getSpoutExecutorNum();
        int boltExecutorNum = workerConfig.getBoltExecutorNum();
        int eachBoltOfTaskNum = workerConfig.getEachBoltOfTaskNum();
        WorkerStatus workerStatus = new WorkerStatus(spoutExecutorNum, boltExecutorNum, eachBoltOfTaskNum);
        return workerStatus;
    }

    private NetAddress buildNetAddress(WorkerConfig workerConfig) {
        NetAddress netAddress = new NetAddress(workerConfig.getHostName(), workerConfig.getPort());
        return netAddress;
    }

    /**
     * 更改worker状态信息
     */
    public void changeWorkerStat() {

    }

    /**
     * 更新zk中数据
     */
    public void updateZkRegisterData() {

    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public ZkConnector getZkConnector() {
        return zkConnector;
    }

    public void setZkConnector(ZkConnector zkConnector) {
        this.zkConnector = zkConnector;
    }

    public WorkerStatus getWorkerStatus() {
        return workerStatus;
    }

    public void setWorkerStatus(WorkerStatus workerStatus) {
        this.workerStatus = workerStatus;
    }

    public NetAddress getNetAddress() {
        return netAddress;
    }

    public void setNetAddress(NetAddress netAddress) {
        this.netAddress = netAddress;
    }

    public NetDataDispatcher getNetDataDispatcher() {
        return netDataDispatcher;
    }

    public void setNetDataDispatcher(NetDataDispatcher netDataDispatcher) {
        this.netDataDispatcher = netDataDispatcher;
    }

    public MsgServerHandler getMsgServerHandler() {
        return msgServerHandler;
    }

    public MsgCoder getMsgCoder() {
        MsgCoder msgCoder = new MsgBeanCoder();
        return msgCoder;
    }

    public WorkerExecutorManager getWorkerExecutorManager() {
        return workerExecutorManager;
    }

    public static WorkerData buildWorkerData(WorkerConfig workerConfig) {
        return new WorkerData(workerConfig);
    }

}
