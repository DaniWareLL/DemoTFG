package com.sonik.config;
import com.sonik.domain.model.User;
import com.sonik.domain.model.UserPref;


/**
 * Stores the user's preferences and useful information
 */
public class UserSession {

    private static User user;
    private static UserPref preferences;

    /**
     * Sets the user and preferences for the current session
     * @param u The user in the current session
     */
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

    public static void setUser(User user) {
        UserSession.user = user;
    }

}
