package com.sonik.domain.exceptions;

public class DataAccessException extends Exception {

    public static final int CHANGES_REVERTED = 1;
    public static final int REVERT_ERROR = 2;
    public static final int CLOSE_OPERATION_ERROR = 3;
    public static final int CONNECTION_ERROR = 4;
    public static final int ALREADY_CONFIGURED = 5;

    private final int errorCode;

    private static String resolverCodigo(int errorCode) {
        return switch (errorCode) {
            case CHANGES_REVERTED ->
                    "Error while accessing data, changes have been reverted.";
            case REVERT_ERROR ->
                    "Error while reverting changes.";
            case CLOSE_OPERATION_ERROR ->
                    "Error while closing the resource.";
            case CONNECTION_ERROR ->
                    "Error while trying to connect to the resource.";
            case ALREADY_CONFIGURED -> "The application's default values have already been set up.";
            default ->
                    "Unknown error code: " + errorCode;
        };
    }

    public DataAccessException(int errorCode, Throwable cause) {
        super(resolverCodigo(errorCode), cause);
        this.errorCode = errorCode;
    }

    public DataAccessException(int errorCode) {
        super(resolverCodigo(errorCode));
        this.errorCode = errorCode;
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = -1;
    }

    public DataAccessException(String message) {
        super(message);
        this.errorCode = -1;
    }

    public DataAccessException(Throwable cause) {
        super(cause);
        this.errorCode = -1;
    }

    public DataAccessException() {
        super();
        this.errorCode = -1;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
