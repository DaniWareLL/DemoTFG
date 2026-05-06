package com.sonik.domain.exceptions;

public class IncorrectArgumentException extends Exception {

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
        INVALID_DONWLOAD_PATH
    }

    private ErrorType errorType;

    public IncorrectArgumentException(ErrorType error, Throwable cause) {
        this.errorType = error;
        super(resolveErrorCode(error), cause);
    }

    public IncorrectArgumentException(ErrorType error) {
        this.errorType = error;
        super(resolveErrorCode(error));
    }

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
            case INVALID_DONWLOAD_PATH -> "Donwload path cannot be null";
        };
    }

    public ErrorType getErrorType() {
        return errorType;
    }

}
