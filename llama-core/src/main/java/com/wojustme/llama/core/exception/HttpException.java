package com.wojustme.llama.core.exception;

/**
 * @author xurenhe
 * @date 2018/1/16
 */
public class HttpException extends Exception {

    public HttpException() {
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }
}
