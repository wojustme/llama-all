package com.wojustme.llama.core.helper.net.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xurenhe
 * @date 2017/12/29
 */
public class MsgBeanToByteEncoder extends MessageToByteEncoder<MsgBean> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MsgBean msg, ByteBuf out) throws Exception {
        MsgType msgType = msg.getMsgType();
        byte[] msgData = msg.getMsgData();
        int msgTypeVal = msgType.val;
        out.writeInt(msgTypeVal);
        out.writeBytes(msgData);
    }
}
