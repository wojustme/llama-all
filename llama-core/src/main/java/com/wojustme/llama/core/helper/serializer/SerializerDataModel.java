package com.wojustme.llama.core.helper.serializer;

/**
 * 序列化模型
 * @author xurenhe
 * @date 2017/12/18
 */
public class SerializerDataModel {
    /**
     * 真实的数据
     */
    Object realDataObj;
    /**
     * 对应的数据类
     */
    Class clazz;

    public SerializerDataModel() {
    }

    public SerializerDataModel(Object realDataObj, Class clazz) {
        this.realDataObj = realDataObj;
        this.clazz = clazz;
    }

    public Object getRealDataObj() {
        return realDataObj;
    }

    public void setRealDataObj(Object realDataObj) {
        this.realDataObj = realDataObj;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
