package com.sonik.domain.exceptions;

/**
 * Signals a problem with the {@link com.sonik.service.audio.AudioExtractor audio extractor}
 */
public class AudioExtractorException extends Exception {

    public static final int DOWNLOAD_ERROR = 1;
    public static final int METADATA_ERROR = 2;
    public static final int SETTING_ERROR = 3;
    public static final int STREAM_URL_ERROR = 4;

    private int errorCode = -1;

    /**
     * Returns an error message depending on the error code received
     * @param errorCode The error code
     * @return The error message
     */
    private static String solveCode(int errorCode) {
        return switch (errorCode) {
            case DOWNLOAD_ERROR ->
                    "Error downloading file.";
            case METADATA_ERROR ->
                    "Error while processing metadata.";
            case SETTING_ERROR ->
                    "Error, invalid extractor configuration.";
            case STREAM_URL_ERROR ->
                    "Error while processing the streaming url.";
            default ->
                    "Unknown error.";
        };
    }

    /**
     * Constructs an {@link AudioExtractorException} with the error code received
     * @param errorCode The error code
     * @param cause The cause of the exception
     */
    public AudioExtractorException(int errorCode, Throwable cause) {
        super(solveCode(errorCode), cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructs an {@link AudioExtractorException} with the error code received
     * @param errorCode The error code
     */
    public AudioExtractorException(int errorCode) {
        super(solveCode(errorCode));
        this.errorCode = errorCode;
    }

    public AudioExtractorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AudioExtractorException(String message) {
        super(message);
    }

    public AudioExtractorException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
