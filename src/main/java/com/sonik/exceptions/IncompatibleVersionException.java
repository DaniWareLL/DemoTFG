package com.sonik.exceptions;

// Esta excepción no se ha usado, pero podría llegar a ser útil para el futuro

public class IncompatibleVersionException extends Exception {

    public IncompatibleVersionException(String message,  Throwable cause) {

        super(message,cause);
    }

    public IncompatibleVersionException(Throwable cause) {

        super(cause);
    }

    public IncompatibleVersionException() {

        super();
    }

    public IncompatibleVersionException(String message) {

        super(message);
    }
}
