package com.wojustme.llama.core.util.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;

/**
 * @author xurenhe
 * @date 2017/11/29
 */
public final class JsonUtils {
    private static final Gson gson = new GsonBuilder().create();
    private static final String CHARSETNAME = "UTF-8";

    public static <T> String toJsonStr(T obj) throws SerializerException {
        String json;
        try {
            json = gson.toJson(obj);
        } catch (Exception e) {
            throw new SerializerException(e);
        }
        return json;
    }

    public static <T> T toBeanObj(String json, Class<T> type) throws SerializerException {
        T pojo;
        try {
            pojo = gson.fromJson(json, type);
        } catch (Exception e) {
            throw new SerializerException(e);
        }
        return pojo;
    }

    public static <T> byte[] serializer(T obj) throws SerializerException {
        byte[] bytes;
        try {
            String jsonStr = toJsonStr(obj);
            bytes = jsonStr.getBytes(CHARSETNAME);
        } catch (UnsupportedEncodingException e) {
            throw new SerializerException("Unsupport UTF-8 encode", e);
        }
        return bytes;
    }

    public static <T> T deserializer(byte[] bytes, Class<T> clazz) throws SerializerException {
        T pojo;
        try {
            String str = new String(bytes, CHARSETNAME);
            pojo = toBeanObj(str, clazz);
        } catch (UnsupportedEncodingException e) {
            throw new SerializerException("Unsupport UTF-8 encode", e);
        }
        return pojo;
    }
}
