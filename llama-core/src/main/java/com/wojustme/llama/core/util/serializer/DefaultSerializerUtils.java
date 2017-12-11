package com.wojustme.llama.core.util.serializer;

import com.wojustme.llama.core.util.zk.ZkException;

import java.io.*;

/**
 * Java默认序列化工具
 * @author xurenhe
 * @date 2017/12/8
 */
public final class DefaultSerializerUtils {

    public static byte[] toByteArray(Object obj) throws SerializerException {
        if (obj == null) {
            throw new SerializerException("this obj is null");
        }
        if (!validateObj(obj)) {
            throw new SerializerException("this obj do not realize interface of Serializable");
        }
        byte[] bytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException e) {
            throw new SerializerException("java default serializer error, maybe io error, data: " + obj, e);
        }
        return bytes;
    }

    public static <T> T toObject (byte[] bytes, Class<T> clazz) throws SerializerException {
        Object obj;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
            if (obj == null) {
                throw new SerializerException("this data is null");
            }
            if (obj.getClass() != clazz) {
                throw new SerializerException(obj.getClass().getName() + " can't cast to " + clazz.getName());
            }
        } catch (IOException e) {
            throw new SerializerException("java default serializer error, IO error", e);
        } catch (ClassNotFoundException e) {
            throw new SerializerException("java default serializer error, class not found", e);
        }
        return (T)obj;
    }

    private static boolean validateObj(Object obj) {
        if (obj instanceof Serializable)
            return true;
        return false;
    }
}
