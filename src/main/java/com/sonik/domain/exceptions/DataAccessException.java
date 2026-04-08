package com.sonik.domain.exceptions;

public class DataAccessException extends Exception {

    public static final int CHANGES_REVERTED = 1;
    public static final int REVERT_ERROR = 2;
    public static final int CLOSE_OPERATION_ERROR = 3;
    public static final int CONNECTION_ERROR = 4;

    private final int errorCode;

    private static String resolverCodigo(int errorCode) {
        return switch (errorCode) {
            case CHANGES_REVERTED ->
                    "Error al acceder a los datos; los cambios han sido revertidos.";
            case REVERT_ERROR ->
                    "Error al intentar revertir los cambios.";
            case CLOSE_OPERATION_ERROR ->
                    "Error al cerrar la conexión o recurso.";
            case CONNECTION_ERROR ->
                    "Error al intentar conectar con la base de datos o recurso.";
            default ->
                    "Código de error desconocido: " + errorCode;
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
