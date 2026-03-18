package com.sonik.old.login_old.exceptions;

public class IncorrectArgumentException extends Exception {

    public IncorrectArgumentException(String message, Throwable cause) {

        super(message,cause);
    }

    public IncorrectArgumentException(Throwable cause) {

        super(cause);
    }

    public IncorrectArgumentException() {

        super();
    }

    public IncorrectArgumentException(String message) {

        super(message);
    }

}
