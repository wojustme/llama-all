package com.wojustme.llama.core.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.wojustme.llama.core.exception.SerializerException;

import java.io.Serializable;

/**
 * protostuff
 * @author xurenhe
 * @date 2017/11/29
 */
public final class ProtoStuffUtils implements Serializable{

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    public static <T> byte[] toByteArr(T obj) {
        Schema<T> schema = (Schema<T>)RuntimeSchema.getSchema(obj.getClass());
        return ProtobufIOUtil.toByteArray(obj, schema, LinkedBuffer.allocate(DEFAULT_BUFFER_SIZE));
    }

    public static <T> T toBeanObj(byte[] bytes, Class<T> clazz) throws SerializerException {
        T obj;
        try {
            obj = clazz.newInstance();
            Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        } catch (InstantiationException e) {
            throw new SerializerException("instantiate fail, this class: " + clazz.getName() + " has no default construction method.", e);
        } catch (IllegalAccessException e) {
            throw new SerializerException("instantiate fail, can not access this class: " + clazz.getName() + "'s default construction method.", e);
        }
        return obj;
    }

}
