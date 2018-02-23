package com.wojustme.llama.core.worker.net;


import com.wojustme.llama.core.bean.NetAddress;
import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.helper.net.MsgClient;
import com.wojustme.llama.core.helper.net.handler.MsgClientServerHandler;
import com.wojustme.llama.core.helper.net.msg.MsgBean;
import com.wojustme.llama.core.helper.net.msg.MsgBeanCoder;
import com.wojustme.llama.core.helper.net.msg.MsgCoder;
import com.wojustme.llama.core.helper.net.msg.MsgType;
import com.wojustme.llama.core.helper.serializer.HessianSerializer;
import com.wojustme.llama.core.helper.serializer.LlamaSerializer;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xurenhe
 * @date 2018/2/23
 */
public class MsgNetClientFactory {


    private static ConcurrentHashMap<NetAddress, MsgClient> clientTargetNetMap;

    private static volatile MsgNetClientFactory instance;

    private static LlamaSerializer llamaSerializer;

    private MsgNetClientFactory() {
    }

    public static MsgNetClientFactory getInstance() {
        if (instance == null) {
            synchronized (MsgNetClientFactory.class) {
                if (instance == null) {
                    instance = new MsgNetClientFactory();
                    clientTargetNetMap = new ConcurrentHashMap<>();
                    llamaSerializer = new HessianSerializer();
                }
            }
        }
        return instance;
    }

    public synchronized MsgClient getClient(NetAddress netAddress) {
        MsgClient msgClient = clientTargetNetMap.get(netAddress);
        if (msgClient == null) {
            addTargetNet(netAddress);
        }
        msgClient = clientTargetNetMap.get(netAddress);
        return msgClient;
    }



    public void addTargetNet(NetAddress netAddress) {
        MsgClientServerHandler<MsgBean> msgClientHandler = new MsgClientServerHandler<MsgBean>() {
            private ChannelHandlerContext channelHandlerContext;

            @Override
            public void sendInitMsg(ChannelHandlerContext ctx) {
                this.channelHandlerContext = ctx;
                // 初始发送
                MsgBean msgBean = new MsgBean(MsgType.HEARTBEAT_HELLO, null);
                ctx.writeAndFlush(msgBean);
            }

            @Override
            public void handleMsg(ChannelHandlerContext ctx, MsgBean data) {

            }

            @Override
            public void handleError(ChannelHandlerContext ctx, Throwable cause) {

            }

            @Override
            public void sendMsg(ChannelHandlerContext ctx, MsgBean data) {
                ctx.writeAndFlush(data);
            }

            @Override
            public ChannelHandlerContext getChannelHandlerContext() {
                return channelHandlerContext;
            }
        };
        MsgCoder<MsgBean> msgBeanMsgCoder = new MsgBeanCoder();
        MsgClient msgClient = new MsgClient(netAddress, msgClientHandler, msgBeanMsgCoder);
        clientTargetNetMap.put(netAddress, msgClient);
    }

    public synchronized void sendMsg(NetAddress netAddress, NetDataBean networkData) {
        MsgClient client = getClient(netAddress);
        try {
            byte[] data = llamaSerializer.serialize(networkData);
            MsgBean msgBean = new MsgBean(MsgType.SEND_DATA, data);
            client.sendMsg(msgBean);
        } catch (SerializerException e) {
            e.printStackTrace();
        }
    }
}
