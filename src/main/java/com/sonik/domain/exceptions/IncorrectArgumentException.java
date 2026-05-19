package com.sonik.domain.exceptions;

/**
 * Signals a problematic parameter/argument. Like {@link IllegalArgumentException} but checked.<br>
 * When throwing this exception, an {@link ErrorType ErrorType} must be assigned
 */
public class IncorrectArgumentException extends Exception {

    /**
     * Internal enum to define the different kinds of errors. This makes handling them easier.
     */
    public enum ErrorType{
        NULL_OBJECT_RECEIVED,
        INVALID_DATE,
        EMPTY_PASSWORD,
        EMPTY_EMAIL,
        INVALID_EMAIL,
        EMPTY_USERNAME,
        EMPTY_PLAYLIST_NAME,
        EMPTY_PLAYLIST_DESCRIPTION,
        INVALID_NUMBER,
        INVALID_SOURCE,
        INVALID_DOWNLOAD_PATH
    }

    private ErrorType errorType;

    /**
     * Constructs an {@link IncorrectArgumentException} with an {@link ErrorType ErrorType} and cause
     * @param error The error type
     * @param cause The cause
     */
    public IncorrectArgumentException(ErrorType error, Throwable cause) {
        this.errorType = error;
        super(resolveErrorCode(error), cause);
    }

    /**
     * Constructs an {@link IncorrectArgumentException} with an {@link ErrorType ErrorType}
     * @param error The error type
     */
    public IncorrectArgumentException(ErrorType error) {
        this.errorType = error;
        super(resolveErrorCode(error));
    }

    /**
     * Returns a message depending on the {@link ErrorType ErrorType}
     * @param error The error type
     * @return The message
     */
    private static String resolveErrorCode(ErrorType error) {
        return switch (error) {
            case NULL_OBJECT_RECEIVED -> "Object cannot be null nor be empty";
            case INVALID_DATE -> "Date cannot be null nor be in the future";
            case EMPTY_PASSWORD -> "The user's password cannot be empty";
            case EMPTY_EMAIL -> "The user's email cannot be empty";
            case INVALID_EMAIL -> "The user's email is invalid(example@domain.com)";
            case EMPTY_USERNAME -> "The user must provide a username";
            case EMPTY_PLAYLIST_NAME -> "The playlist must have a name.";
            case EMPTY_PLAYLIST_DESCRIPTION -> "The playlist must have a description.";
            case INVALID_NUMBER -> "Number must be greater than zero";
            case INVALID_SOURCE -> "Source cannot be null";
            case INVALID_DOWNLOAD_PATH -> "Download path cannot be null";
        };
    }

    public ErrorType getErrorType() {
        return errorType;
    }

}
