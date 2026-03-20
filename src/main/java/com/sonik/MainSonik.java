package com.sonik;

import com.sonik.config.AppContext;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class starts the program, by using {@link AppContext#initializeApplication()}
 */
public class MainSonik extends Application {

    public static void main(String[] args) {

    }

    @Override
    public void start(Stage stage) throws Exception {
        AppContext.initializeApplication();
    }

    @Override
    public void stop() throws Exception {
        AppContext.shutDown();
    }
}
