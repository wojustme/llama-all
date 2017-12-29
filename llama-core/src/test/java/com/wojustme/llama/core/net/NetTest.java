package com.wojustme.llama.core.net;

import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.helper.net.handler.MsgClientServerHandler;
import com.wojustme.llama.core.helper.net.handler.MsgServerHandler;
import com.wojustme.llama.core.helper.net.msg.MsgBean;
import com.wojustme.llama.core.helper.net.msg.MsgBeanCoder;
import com.wojustme.llama.core.helper.net.msg.MsgCoder;
import com.wojustme.llama.core.helper.net.msg.MsgType;
import com.wojustme.llama.core.helper.net.MsgClient;
import com.wojustme.llama.core.helper.net.MsgServer;
import com.wojustme.llama.core.helper.net.bean.NetAddress;
import com.wojustme.llama.core.helper.serializer.HessianSerializer;
import com.wojustme.llama.core.helper.serializer.LlamaSerializer;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Test;

/**
 * @author xurenhe
 * @date 2017/12/29
 */
public class NetTest {
    LlamaSerializer llamaSerializer = new HessianSerializer();

    @Test
    public void test_startServer() throws Exception {
        int port = 9527;

        MsgServerHandler<MsgBean> msgServerHandler = new MsgServerHandler<MsgBean>() {
            @Override
            public void handleMsg(ChannelHandlerContext ctx, MsgBean data) {
                try {
                    System.out.println(llamaSerializer.deserialize(data.getMsgData(), String.class));
                } catch (SerializerException e) {
                    e.printStackTrace();
                }
                ctx.close();
            }

            @Override
            public void handleError(ChannelHandlerContext ctx, Throwable cause) {

            }
        };
        MsgCoder<MsgBean> msgBeanMsgCoder = new MsgBeanCoder();
        MsgServer msgServer = new MsgServer(port, msgServerHandler, msgBeanMsgCoder);
        msgServer.start();
    }

    @Test
    public void test_startClient() throws Exception {
        String host = "127.0.0.1";
        int port = 9527;
        NetAddress netAddress = new NetAddress();
        netAddress.setHost(host);
        netAddress.setPort(port);
        MsgClientServerHandler<MsgBean> msgClientHandler = new MsgClientServerHandler<MsgBean>() {
            @Override
            public void sendInitMsg(ChannelHandlerContext ctx) {
                MsgBean msgBean = null;
                try {
                    msgBean = new MsgBean(MsgType.HEARTBEAT_HELLO, llamaSerializer.serialize("hello"));
                } catch (SerializerException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(msgBean);
            }

            @Override
            public void handleMsg(ChannelHandlerContext ctx, MsgBean data) {

            }

            @Override
            public void handleError(ChannelHandlerContext ctx, Throwable cause) {

            }
        };

        MsgCoder<MsgBean> msgBeanMsgCoder = new MsgBeanCoder();
        MsgClient msgClient = new MsgClient(netAddress, msgClientHandler, msgBeanMsgCoder);
        msgClient.connect();
    }
}
