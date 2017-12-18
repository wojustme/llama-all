package com.wojustme.llama.core.helper.serializer;

import com.wojustme.llama.core.exception.SerializerException;
import com.wojustme.llama.core.util.HessianUtils;

/**
 * @author xurenhe
 * @date 2017/12/17
 */
public class HessianSerializer implements LlamaSerializer {
    @Override
    public <T> byte[] serialize(T obj) throws SerializerException {
        return HessianUtils.toByteArr(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializerException {
        return HessianUtils.toBeanObj(bytes, clazz);
    }
}
