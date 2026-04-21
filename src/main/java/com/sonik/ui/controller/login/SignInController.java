package com.sonik.ui.controller.login;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.service.AuthService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInController {

    private AuthService authService;

    @FXML
    public TextField UserTextfield;
    @FXML
    public PasswordField PasswordTextfield;
    @FXML
    public Button SignInButton;


    public void initialize() {
        this.authService = AppContext.getAuthService();
    }

    public void OnkeyPressed_UserTexfield(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            PasswordTextfield.requestFocus();
        }
    }

    public void OnkeyPressed_PasswordTexfield(KeyEvent keyEvent) {}

    public void SignInButton_MouseClicked(MouseEvent mouseEvent) {
        try {
            if(authService.login(UserTextfield.getText(), PasswordTextfield.getText())){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home-view.fxml"));
                Scene newScene = new Scene(loader.load());

                Stage stage = (Stage) SignInButton.getScene().getWindow();
                stage.setScene(newScene);
                stage.show();
            }
        } catch (ObjectNotFoundException e) {
            Platform.runLater(()->{Alert alert = new Alert(Alert.AlertType.ERROR,
                    "The username and/or password is incorrect.", ButtonType.OK);
            alert.showAndWait();});

        } catch (DataAccessException e) {
            Platform.runLater(()->{Alert alert = new Alert(Alert.AlertType.ERROR,
                    "An error occurred when accessing the database.", ButtonType.OK);
                alert.showAndWait();});

        } catch (IOException e) {
            Platform.runLater(()->{Alert alert = new Alert(Alert.AlertType.ERROR,
                    "An error occurred when loading home-view.fxml.", ButtonType.OK);
                alert.showAndWait();});
        }
    }

    public void SignUpButton_MouseClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup-view.fxml"));
            Scene newScene = new Scene(loader.load());

            Stage stage = (Stage) SignInButton.getScene().getWindow();
            stage.setScene(newScene);
            stage.show();

        } catch (IOException e) {
            Platform.runLater(()->{Alert alert = new Alert(Alert.AlertType.ERROR,
                    "An error occurred when loading signup-view.fxml.", ButtonType.OK);
                alert.showAndWait();});
        }

    }
}
