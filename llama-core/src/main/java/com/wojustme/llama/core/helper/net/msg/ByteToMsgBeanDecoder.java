package com.wojustme.llama.core.helper.net.msg;

import com.wojustme.llama.core.exception.NetException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xurenhe
 * @date 2017/12/29
 */
public class ByteToMsgBeanDecoder extends ByteToMessageDecoder {

    private static final int TYPE_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readableBytes();
        if (length <= TYPE_LENGTH) {
            throw new NetException("data error");
        }
        MsgType type = MsgType.getMsgType(in.readInt());
        byte[] bytes = new byte[length - TYPE_LENGTH];
        in.readBytes(bytes);

        MsgBean msgBean = new MsgBean(type, bytes);
        out.add(msgBean);
    }
}
