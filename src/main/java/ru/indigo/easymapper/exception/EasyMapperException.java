package ru.indigo.easymapper.exception;

public class EasyMapperException extends RuntimeException {

    public EasyMapperException(String message) {
        super(message);
    }

    public EasyMapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
