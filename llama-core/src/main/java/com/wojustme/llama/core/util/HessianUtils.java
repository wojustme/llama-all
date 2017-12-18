package com.wojustme.llama.core.util;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.wojustme.llama.core.exception.SerializerException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author xurenhe
 * @date 2017/12/16
 */
public class HessianUtils {
    public static <T> byte[] toByteArr(T obj) {
        if(obj == null) throw new NullPointerException();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        try {
            ho.writeObject(obj);
        } catch (IOException e) {
            new SerializerException("convert hessian error", e);
        }
        return os.toByteArray();
    }

    public static <T> T toBeanObj(byte[] bytes, Class<T> clazz) throws SerializerException {
        if(bytes == null || bytes.length == 0) throw new SerializerException("toBeanObj error, no data");
        Object obj;
        T beanObj;
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        HessianInput hi = new HessianInput(is);
        try {
            obj = hi.readObject();
            try {
                beanObj = clazz.cast(obj);
            } catch (ClassCastException e) {
                throw new SerializerException("toBeanObj hessian error, class cast", e);
            }
        } catch (IOException e) {
            throw new SerializerException("toBeanObj hessian error", e);
        }
        return beanObj;
    }
}
