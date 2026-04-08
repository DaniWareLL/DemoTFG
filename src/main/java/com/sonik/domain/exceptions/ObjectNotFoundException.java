package com.sonik.domain.exceptions;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(String message, Throwable cause) {

        super(message,cause);
    }

    public ObjectNotFoundException(Throwable cause) {

        super(cause);
    }

    public ObjectNotFoundException() {

        super();
    }

    public ObjectNotFoundException(String message) {

        super(message);
    }
}
