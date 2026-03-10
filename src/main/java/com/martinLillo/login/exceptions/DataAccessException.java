package com.martinLillo.login.exceptions;


public class DataAccessException extends Exception {

    public static final int CHANGES_REVERTED = 1;
    public static final int REVERT_ERROR = 2;
    public static final int CLOSE_OPERATION_ERROR = 3;
    public static final int CONNECTION_ERROR = 4;

    // Aunque errorCode no se use para nada, se almacena para un uso futuro
    private int errorCode;

    private static String resolverCodigo(int errorCode) {

        String message;
        switch (errorCode) {
            case 1:
                message = "Error al acceder a los datos, se han revertido los cambios";
                break;
            case 2:
                message = "Error al revertir los cambios";
                break;
            case 3:
                message = "Error al cerrar la conexión/recurso";
                break;
            case 4:
                message = "Error al intentar acceder al recurso";
                break;
            default:
                message = "Código de error desconocido: " + errorCode;
                break;
        }
        return message;
    }

    public DataAccessException(String message, Throwable cause) {

        super(message, cause);
    }

    public DataAccessException(Throwable cause) {

        super(cause);
    }

    public DataAccessException() {

        super();
    }

    public DataAccessException(String message) {

        super(message);
    }

    /**
     * @param errorCode Indica un código de error para asignar un mensaje a la excepción:
     *                  <ul>
     *                  <li>{@link #CHANGES_REVERTED} - Indica que ha habido un error y se han revertido los cambios al sistema de almacenamiento(rollback en SQL)</li>
     *                  <li>{@link #REVERT_ERROR} - Indica un error al revertir los cambios</li>
     *                  <li>{@link #CLOSE_OPERATION_ERROR} - Indica un error al cerrar una conexión/recurso</li>
     *                  <li>{@link #CONNECTION_ERROR} - Indica un error al intentar conectar con la BBDD</li>
     *                  </ul>
     *                  Un código de error diferente asignará el mensaje "Código de error desconocido"
     */
    public DataAccessException(int errorCode, Throwable cause) {

        super(resolverCodigo(errorCode), cause);
        // Aunque almacenar el código de error no haga nada se guarda por si hace falta usarlo
        this.errorCode = errorCode;

    }
}
