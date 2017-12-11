package com.wojustme.llama.core.util.serializer;

/**
 * @author xurenhe
 * @date 2017/12/3
 */
public class SerializerException extends Exception {

    public SerializerException() {
    }

    public SerializerException(String message) {
        super(message);
    }

    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializerException(Throwable cause) {
        super(cause);
    }
}
