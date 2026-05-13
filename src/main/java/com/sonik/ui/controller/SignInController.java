package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.service.AuthService;
import com.sonik.ui.navigation.ViewManager;
import com.sonik.ui.navigation.ViewType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static com.sonik.config.SessionStorage.clear;
import static com.sonik.config.SessionStorage.save;


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
    private TextArea accountCreatedLabel;

    @FXML
    private CheckBox rememberCheckBox;

    @FXML
    private Button closeBtn;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;

        // Close
        closeBtn.setOnAction(e -> stage.close());
        closeBtn.setOnMousePressed(e -> {
            closeBtn.setStyle("-fx-background-color: red;");
        });
        closeBtn.setOnMouseReleased(e -> {
            closeBtn.setStyle("-fx-background-color: black;");
            AppContext.shutDown();
        });
    }

    public void initialize() {

        this.authService = AppContext.getAuthService();

        if (ViewManager.NavigationFlags.showAccountCreated) {
            showAccountCreatedMessage();
            ViewManager.NavigationFlags.showAccountCreated = false;
        }

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
        accountCreatedLabel.setManaged(true);
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

                boolean remember = rememberCheckBox.isSelected();

                if (remember) {
                    save(UserSession.getUser().getUserName());
                } else {
                    clear();
                }
                ViewManager.switchScene(ViewType.HOME);

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
            AuxiliaryMethods.showAlert(e);
        }
    }

    public void SignUpButton_MouseClicked(MouseEvent mouseEvent) {
        try {
            ViewManager.switchScene(ViewType.SIGN_UP);
        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        }
    }

    public void closeBtnMC(MouseEvent mouseEvent) {
    }
}
