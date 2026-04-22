package com.sonik.config;
import com.sonik.domain.model.User;
import com.sonik.domain.model.UserPref;


/**
 * Gestiona el estado de sesión del usuario dentro de la aplicación.
 *
 * Esta clase almacena en memoria el usuario autenticado y sus preferencias
 * asociadas, evitando realizar consultas repetidas a la base de datos durante
 * la ejecución.
 *
 * Su propósito es proporcionar acceso rápido y centralizado a los datos del
 * usuario logueado (User y UserPref), permitiendo que los servicios y
 * controladores trabajen con ellos sin necesidad de acceder nuevamente al
 * repositorio o a la tabla user_pref.
 *
 * La sesión se inicializa tras un login exitoso y se limpia al cerrar sesión.
 */

public class UserSession {

    private static User user;
    private static UserPref preferences;

    public static void start(User u) {
        user = u;
        preferences = u.getPreferences();
    }

    public static User getUser() {
        return user;
    }

    public static UserPref getPreferences() {
        return preferences;
    }

    public static void updatePreferences(UserPref pref) {
        preferences = pref;
    }

    public static void clear() {
        user = null;
        preferences = null;
    }
}
