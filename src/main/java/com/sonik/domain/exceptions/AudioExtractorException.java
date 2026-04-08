package com.sonik.domain.exceptions;

public class AudioExtractorException extends RuntimeException {

    public static final int DOWNLOAD_ERROR = 1;
    public static final int METADATA_ERROR = 2;
    public static final int SETTING_ERROR = 3;
    public static final int STREAM_URL_ERROR = 4;

    private final int errorCode;

    private static String resolverCodigo(int errorCode) {
        return switch (errorCode) {
            case DOWNLOAD_ERROR ->
                    "Error durante la descarga del audio.";
            case METADATA_ERROR ->
                    "Error al procesar o extraer los metadatos del audio.";
            case SETTING_ERROR ->
                    "Error en la configuración del extractor o herramientas externas.";
            case STREAM_URL_ERROR ->
                    "Error al obtener o procesar la URL de streaming.";
            default ->
                    "Error desconocido en el extractor de audio.";
        };
    }

    public AudioExtractorException(int errorCode, Throwable cause) {
        super(resolverCodigo(errorCode), cause);
        this.errorCode = errorCode;
    }

    public AudioExtractorException(int errorCode) {
        super(resolverCodigo(errorCode));
        this.errorCode = errorCode;
    }

    public AudioExtractorException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = -1;
    }

    public AudioExtractorException(String message) {
        super(message);
        this.errorCode = -1;
    }

    public AudioExtractorException(Throwable cause) {
        super(cause);
        this.errorCode = -1;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
