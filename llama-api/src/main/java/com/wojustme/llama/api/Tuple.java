package com.wojustme.llama.api;

/**
 * @author xurenhe
 * @date 2018/2/23
 */
public class Tuple {

    /**
     * key值
     */
    private String key;
    /**
     * 真实数据
     */
    private String data;

    /**
     * 分区tuple
     * @param key
     * @param data
     */
    public Tuple(String key, String data) {
        this.key = key;
        this.data = data;
    }
    /**
     * 无分区tuple
     * @param data
     */
    public Tuple(String data) {
        this(null, data);
    }

    public String getKey() {
        return key;
    }

    public String getData() {
        return data;
    }
}
