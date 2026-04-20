package com.sonik.domain.exceptions;

public class IncorrectArgumentException extends Exception {

    public final static int NULL_OR_EMPTY_OBJECT = 1;
    public final static int INVALID_DATE = 2;
    public final static int INVALID_NUMBER = 3;

    public IncorrectArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectArgumentException(int errorCode, Throwable cause) {
        super(resolveErrorCode(errorCode), cause);
    }

    public IncorrectArgumentException(int errorCode) {
        super(resolveErrorCode(errorCode));
    }

    public IncorrectArgumentException(String message) {
        super(message);
    }

    private static String resolveErrorCode(int code) {
        switch (code) {
            case NULL_OR_EMPTY_OBJECT:
                return "Object cannot be null nor be empty";
            case INVALID_DATE:
                return "Date cannot be null nor be in the future";
            case INVALID_NUMBER:
                return "Number must be greater than zero";
            default:
                return "Unknown code " + code;
        }
    }

}
