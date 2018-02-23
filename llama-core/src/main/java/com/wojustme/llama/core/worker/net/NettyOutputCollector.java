package com.wojustme.llama.core.worker.net;

import com.wojustme.llama.api.OutputCollector;
import com.wojustme.llama.api.Strategy;
import com.wojustme.llama.api.Tuple;
import com.wojustme.llama.core.bean.NetAddress;
import com.wojustme.llama.core.util.TupleKeyUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xurenhe
 * @date 2018/2/23
 */
public class NettyOutputCollector implements OutputCollector {

    /**
     * 发送器隶属于任务名
     */
    private String taskName;
    /**
     * 数据发送策略
     */
    private Strategy strategy;
    /**
     * 下游节点任务和主机封装
     */
    private List<DownstreamNetNode> downstreamNetNodes;
    /**
     * 该节点的所有网络管理器
     */
    private MsgNetClientFactory msgNetClientFactory;

    public NettyOutputCollector(String taskName, Strategy strategy, List<DownstreamNetNode> downstreamNetNodes) {
        this.taskName = taskName;
        this.strategy = strategy;
        this.downstreamNetNodes = downstreamNetNodes;
        this.msgNetClientFactory = MsgNetClientFactory.getInstance();
    }

    @Override
    public void emit(Tuple tuple) {
        if (CollectionUtils.isEmpty(downstreamNetNodes)) {
            return;
        }
        Set<Integer> nextNodeIndexSet = new HashSet<>();
        int nextNodeNum = downstreamNetNodes.size();
        switch (strategy) {
            case GROUP:
                String key = tuple.getKey();
                nextNodeIndexSet.add(TupleKeyUtils.computeHashKey(key, nextNodeNum));
                break;
            case RANDOM:
                nextNodeIndexSet.add(TupleKeyUtils.randomKey(nextNodeNum));
                break;
            case BROADCAST:
                for (int i = 0; i < nextNodeNum; i++) {
                    nextNodeIndexSet.add(i);
                }
                break;
        }
        // 真实数据
        String data = tuple.getData();
        // 循环发送，random和group模式只发送一次，而broadcast模式发送多次
        for (int index : nextNodeIndexSet) {
            DownstreamNetNode downstreamNetNode = downstreamNetNodes.get(index);
            NetAddress serverHost = downstreamNetNode.getNetAddress();
            // 网络中传输的数据
            NetDataBean networkData = new NetDataBean(downstreamNetNode.getTaskName(), data.getBytes());
            msgNetClientFactory.sendMsg(serverHost, networkData);
        }
    }
}
