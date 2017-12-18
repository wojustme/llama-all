package com.wojustme.llama.core.util;

import com.wojustme.llama.core.exception.SerializerException;
import org.msgpack.MessagePack;

import java.io.IOException;

/**
 * @author xurenhe
 * @date 17/12/11
 */
public final class MsgPackUtils {
    private static final MessagePack MESSAGEPACK = new MessagePack();

    public static <T> byte[] serializer(T obj) throws SerializerException {
        byte[] bytes;
        try {
            bytes = MESSAGEPACK.write(obj);
        } catch (IOException e) {
            throw new SerializerException("fail to toByteArr obj: " + obj + ", use msgpack", e);
        }
        return bytes;
    }

    public static <T> T deserializer(byte[] bytes, Class<T> clazz) throws SerializerException {
        T obj;
        try {
            obj = MESSAGEPACK.read(bytes, clazz);
        } catch (IOException e) {
            throw new SerializerException("fail to toBeanObj, use msgpack", e);
        }
        return obj;
    }
}
