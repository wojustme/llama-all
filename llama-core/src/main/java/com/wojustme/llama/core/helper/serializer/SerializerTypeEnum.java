package com.wojustme.llama.core.helper.serializer;

/**
 * @author xurenhe
 * @date 2017/12/7
 */
public enum SerializerTypeEnum {
    NONE("none"),
    JAVA_DEFAULT("default"),
    JSON("json"),
    PROTO("proto"),
    HESSIAN("hessian");

    public String value;

    SerializerTypeEnum(String value) {
        this.value = value;
    }

    public static SerializerTypeEnum transform(String value) {
        switch (value.toLowerCase()) {
            case "default":
                return SerializerTypeEnum.JAVA_DEFAULT;
            case "json":
                return SerializerTypeEnum.JSON;
            case "proto":
                return SerializerTypeEnum.PROTO;
            case "hessian":
                return SerializerTypeEnum.HESSIAN;
            default:
                return SerializerTypeEnum.NONE;
        }
    }

    public static LlamaSerializer getSerializer(SerializerTypeEnum serializerTypeEnum) {
        switch (serializerTypeEnum) {
            case JSON:
                return new JsonSerializer();
            case HESSIAN:
                return new HessianSerializer();
        }
        return null;
    }

}
