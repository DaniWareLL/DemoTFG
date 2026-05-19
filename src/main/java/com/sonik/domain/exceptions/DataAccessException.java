package com.sonik.domain.exceptions;

/**
 * Indicates an error when accessing the data
 */
public class DataAccessException extends Exception {

    public static final int CHANGES_REVERTED = 1;
    public static final int REVERT_ERROR = 2;
    public static final int CLOSE_OPERATION_ERROR = 3;
    public static final int CONNECTION_ERROR = 4;
    public static final int ALREADY_CONFIGURED = 5;

    private int errorCode = -1;

    /**
     * Returns an error message depending on the error code received
     * @param errorCode The error code
     * @return The error message
     */
    private static String resolveCode(int errorCode) {
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
        super(resolveCode(errorCode), cause);
        this.errorCode = errorCode;
    }

    public DataAccessException(int errorCode) {
        super(resolveCode(errorCode));
        this.errorCode = errorCode;
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException() {
        super();
    }

    public int getErrorCode() {
        return errorCode;
    }
}
