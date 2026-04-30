package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class SignInController {


    private AuthService authService;

    @FXML
    private HBox leftHBox; // crea este fx:id en el FXML

    @FXML
    private TextField userTextfield;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button signInButton;

    @FXML
    private Label usernameErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label accountCreatedLabel;

    public void initialize() {

        this.authService = AppContext.getAuthService();

        /**
         * Código para redondear background-image
         */
        Rectangle clip = new Rectangle();
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        clip.widthProperty().bind(leftHBox.widthProperty());
        clip.heightProperty().bind(leftHBox.heightProperty());
        leftHBox.setClip(clip);
    }

    public void showAccountCreatedMessage() {
        accountCreatedLabel.setVisible(true);
    }

    public void OnkeyPressed_UserTexfield(KeyEvent keyEvent) {
        accountCreatedLabel.setVisible(false);
        AuxiliaryMethods.setErrorLabelState(false, usernameErrorLabel, userTextfield, Optional.empty());
        if (keyEvent.getCode() == KeyCode.ENTER) {
            passwordTextField.requestFocus();
        }
    }

    public void OnkeyPressed_PasswordTexfield(KeyEvent keyEvent) {
        accountCreatedLabel.setVisible(false);
        AuxiliaryMethods.setErrorLabelState(false, passwordErrorLabel, passwordTextField, Optional.empty());
    }

    public void SignInButton_MouseClicked(MouseEvent mouseEvent) {

        try {
            if (authService.login(userTextfield.getText(), passwordTextField.getText())) {

                // Cargar Home
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home-view.fxml"));
                Scene newScene = new Scene(loader.load());

                // Obtener controller del Home
                HomeController controller = loader.getController();

                // Crear un Stage NUEVO para el Home
                Stage homeStage = new Stage();
                homeStage.initStyle(StageStyle.UNDECORATED);
                homeStage.setScene(newScene);

                // PASAR EL STAGE AL CONTROLADOR
                controller.setStage(homeStage);

                homeStage.show();

                // Cerrar login
                Stage loginStage = (Stage) signInButton.getScene().getWindow();
                loginStage.close();
            } else {
                AuxiliaryMethods.setErrorLabelState(true, passwordErrorLabel, passwordTextField, Optional.of("Incorrect password"));
            }
        } catch (IncorrectArgumentException iae) {
            if (iae.getErrorType() == IncorrectArgumentException.ErrorType.EMPTY_USERNAME) {
                AuxiliaryMethods.setErrorLabelState(true, usernameErrorLabel, userTextfield, Optional.of(iae.getMessage()));
            } else if (iae.getErrorType() == IncorrectArgumentException.ErrorType.EMPTY_PASSWORD) {
                AuxiliaryMethods.setErrorLabelState(true, passwordErrorLabel, passwordTextField, Optional.of(iae.getMessage()));
            }

        } catch (ObjectNotFoundException e) {
            AuxiliaryMethods.setErrorLabelState(true, usernameErrorLabel ,userTextfield, Optional.of(e.getMessage()));
        } catch (DataAccessException | IOException e) {
            AuxiliaryMethods.showAlert(e.getMessage());
        }
    }

    public void SignUpButton_MouseClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup-view.fxml"));
            Scene newScene = new Scene(loader.load());

            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
            stage.sizeToScene();
            stage.centerOnScreen();


        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e.getMessage());
        }

    }
}
