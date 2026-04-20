package com.sonik.domain.exceptions;

public class UserValidationException extends Exception {

    public static final int DUPLICATE_USERNAME = 1;
    public static final int INVALID_CURRENT_PASSWORD = 2;
    public static final int WEAK_PASSWORD = 3;
    public static final int INVALID_USERNAME_FORMAT = 4;
    public static final int INVALID_EMAIL_FORMAT = 5;
    public static final int PASSWORD_MISMATCH = 6;

    private final int errorCode;

    private static String resolverCodigo(int errorCode) {
        return switch (errorCode) {
            case DUPLICATE_USERNAME ->
                    "El nombre de usuario ya existe en el sistema.";
            case INVALID_CURRENT_PASSWORD ->
                    "La contraseña actual es incorrecta.";
            case WEAK_PASSWORD ->
                    "La nueva contraseña no cumple con los requisitos de seguridad.";
            case INVALID_USERNAME_FORMAT ->
                    "El nombre de usuario contiene caracteres no válidos.";
            case INVALID_EMAIL_FORMAT ->
                    "El formato del correo electrónico no es válido.";
            case PASSWORD_MISMATCH ->
                    "Las contraseñas no coinciden.";
            default ->
                    "Código de error de validación desconocido: " + errorCode;
        };
    }

    public UserValidationException(int errorCode, Throwable cause) {
        super(resolverCodigo(errorCode), cause);
        this.errorCode = errorCode;
    }

    public UserValidationException(int errorCode) {
        super(resolverCodigo(errorCode));
        this.errorCode = errorCode;
    }

    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = -1;
    }

    public UserValidationException(String message) {
        super(message);
        this.errorCode = -1;
    }

    public UserValidationException(Throwable cause) {
        super(cause);
        this.errorCode = -1;
    }

    public UserValidationException() {
        super();
        this.errorCode = -1;
    }

    public int getErrorCode() {
        return errorCode;
    }
}