package com.wojustme.llama.core.util.serializer;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * protostuff
 * @author xurenhe
 * @date 2017/11/29
 */
public final class ProtoStuffUtils {

    private static final int DEFAULT_BUFFER_SIZE = 256;

    public static <T> byte[] serializer(T obj) {
        Schema schema = RuntimeSchema.getSchema(obj.getClass());
        return ProtobufIOUtil.toByteArray(obj, schema, LinkedBuffer.allocate(DEFAULT_BUFFER_SIZE));
    }

    public static <T> T deserializer(byte[] bytes, Class<T> clazz) throws SerializerException {
        T obj;
        try {
            obj = clazz.newInstance();
            Schema schema = RuntimeSchema.getSchema(obj.getClass());
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        } catch (InstantiationException e) {
            throw new SerializerException("instantiate fail, this class: " + clazz.getName() + " has no default construction method.", e);
        } catch (IllegalAccessException e) {
            throw new SerializerException("instantiate fail, can not access this class: " + clazz.getName() + "'s default construction method.", e);
        }
        return obj;
    }

}
