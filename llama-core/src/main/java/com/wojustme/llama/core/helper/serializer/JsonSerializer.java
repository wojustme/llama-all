package com.wojustme.llama.core.helper.serializer;

import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.util.JsonUtils;

/**
 * @author xurenhe
 * @date 2017/12/15
 */
public class JsonSerializer implements LlamaSerializer {

    @Override
    public <T> byte[] serialize(T obj) throws SerializerException {
        return JsonUtils.serialize(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializerException {
        return JsonUtils.deSerialize(bytes, clazz);
    }
}
