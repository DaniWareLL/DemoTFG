package com.sonik;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.ui.controller.Main;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class starts the program, by using {@link AppContext#initializeApplication()}
 */
public class MainSonik {

    public static void main(String[] args) throws DataAccessException {
        Application.launch(Main.class, args);
    }
}
