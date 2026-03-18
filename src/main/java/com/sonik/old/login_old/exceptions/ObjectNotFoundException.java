package com.sonik.old.login_old.exceptions;

/**
 * Es lanzada cuando no se ha podido acceder a un objeto en un almacén de datos
 */
public class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(String message, Throwable cause) {

        super(message,cause);
    }

    public ObjectNotFoundException(Throwable cause) {

        super(cause);
    }

    public ObjectNotFoundException() {

        super();
    }

    public ObjectNotFoundException(String message) {

        super(message);
    }

}
