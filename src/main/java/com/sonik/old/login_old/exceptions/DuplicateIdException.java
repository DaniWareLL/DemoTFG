package com.sonik.old.login_old.exceptions;

public class DuplicateIdException extends Exception {

    public DuplicateIdException(String message, Throwable cause) {

        super(message,cause);
    }

    public DuplicateIdException(Throwable cause) {

        super(cause);
    }

    public DuplicateIdException() {

        super();
    }

    public DuplicateIdException(String message) {

        super(message);
    }
}
