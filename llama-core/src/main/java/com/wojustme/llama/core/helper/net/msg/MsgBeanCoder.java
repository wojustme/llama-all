package com.wojustme.llama.core.helper.net.msg;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xurenhe
 * @date 2017/12/29
 */
public class MsgBeanCoder implements MsgCoder<MsgBean> {

    private ByteToMessageDecoder byteToMessageDecoder;
    private MessageToByteEncoder msgBeanToByteEncoder;

    public MsgBeanCoder() {
        byteToMessageDecoder = new ByteToMsgBeanDecoder();
        msgBeanToByteEncoder = new MsgBeanToByteEncoder();
    }

    @Override
    public MessageToByteEncoder<MsgBean> getToByteEncoder() {
        return msgBeanToByteEncoder;
    }

    @Override
    public ByteToMessageDecoder getToBeanDecoder() {
        return byteToMessageDecoder;
    }
}
