package com.wojustme.llama.core.util.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author xurenhe
 * @date 2017/11/29
 */
public final class JsonUtils {
    private static final Gson gson = new GsonBuilder().create();

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
}
