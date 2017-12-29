package com.wojustme.llama.core.helper.net.msg;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 数据编码接口
 * @author xurenhe
 * @date 2017/12/29
 */
public interface MsgCoder<T> {

    MessageToByteEncoder<T> getToByteEncoder();

    ByteToMessageDecoder getToBeanDecoder();
}
