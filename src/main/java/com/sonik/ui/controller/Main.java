package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;
import com.sonik.ui.navigation.ViewManager;
import com.sonik.ui.navigation.ViewType;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

import static com.sonik.config.SessionStorage.load;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, DataAccessException, ObjectNotFoundException, IncorrectArgumentException {

        AppContext.initializeApplication();

        ViewManager.setPrimaryStage(stage);

        String savedUser = load();

        if (savedUser != null) {
            User user = AppContext.getJpaUserRepository().findByUsername(savedUser);
            if (user != null) {
                UserSession.start(user);
                ViewManager.switchScene(ViewType.HOME);
                return;
            }
        }

        ViewManager.switchScene(ViewType.SIGN_IN);
    }

    public static void main(String[] args) {
        launch();
    }
}