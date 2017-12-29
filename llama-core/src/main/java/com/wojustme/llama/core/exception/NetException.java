package com.wojustme.llama.core.exception;

/**
 * @author xurenhe
 * @date 2017/12/29
 */
public class NetException extends Exception {

    public NetException() {
    }

    public NetException(String message) {
        super(message);
    }

    public NetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetException(Throwable cause) {
        super(cause);
    }
}
