package com.wojustme.llama.core.exception;

/**
 * @author xurenhe
 * @date 2017/12/3
 */
public class ZkException extends RuntimeException {

    public ZkException() {
    }

    public ZkException(String message) {
        super(message);
    }

    public ZkException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZkException(Throwable cause) {
        super(cause);
    }
}
