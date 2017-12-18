package com.wojustme.llama.core.helper.serializer;

import com.wojustme.llama.core.exception.SerializerException;

/**
 * @author xurenhe
 * @date 2017/12/15
 */
public interface LlamaSerializer {

    <T> byte[] serialize(T obj) throws SerializerException;

    <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializerException;

}
