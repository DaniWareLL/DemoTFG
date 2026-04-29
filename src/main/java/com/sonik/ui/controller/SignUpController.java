package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.model.User;
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
import java.time.LocalDate;
import java.util.Optional;

public class SignUpController {

    private AuthService authService;
    /* No creas un objeto de la impl para desacoplar el codigo y cumplir el principio
     de inversion de dependencias */

    @FXML
    public TextField EmailTextfield;
    @FXML
    public TextField UserTextfield;

    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label usernameErrorLabel;
    @FXML
    private Label passwordErrorLabel;

    @FXML
    public PasswordField PasswordTextfield;
    @FXML
    public Button CreateAccountButton;


    public void initialize() {
        this.authService = AppContext.getAuthService();
    }

    public void OnkeyPressed_EmailTexfield(KeyEvent keyEvent) {
        AuxiliaryMethods.setErrorLabelState(false, emailErrorLabel, EmailTextfield, Optional.empty());
        if (keyEvent.getCode() == KeyCode.ENTER) {
            UserTextfield.requestFocus();
        }
    }

    public void OnkeyPressed_UserTexfield(KeyEvent keyEvent) {
        AuxiliaryMethods.setErrorLabelState(false, usernameErrorLabel, UserTextfield, Optional.empty());
        if (keyEvent.getCode() == KeyCode.ENTER) {
            PasswordTextfield.requestFocus();
        }
    }

    public void OnkeyPressed_PasswordTexfield(KeyEvent keyEvent) {
        AuxiliaryMethods.setErrorLabelState(false, passwordErrorLabel, PasswordTextfield, Optional.empty());
        if (keyEvent.getCode() == KeyCode.ENTER) {
            CreateAccountButton.requestFocus();
        }
    }

    public void CreateAccountButton_MouseClicked(MouseEvent mouseEvent) {

        try {
            User user = new User(UserTextfield.getText(), EmailTextfield.getText(), PasswordTextfield.getText(), LocalDate.now());
            authService.register(user);

            BackToLogin();
        } catch (DuplicateIdException e) {
            AuxiliaryMethods.setErrorLabelState(true,
                    usernameErrorLabel,
                    UserTextfield,
                    Optional.of("The username has already been taken"));

        } catch (DataAccessException e) {
            AuxiliaryMethods.showAlert("An error occurred while connecting to the database.");

        } catch (IncorrectArgumentException e) {
            switch (e.getErrorType()) {
                case NULL_OBJECT_RECEIVED -> AuxiliaryMethods.showAlert("A null object was received as a parameter.");
                case INVALID_DATE -> AuxiliaryMethods.showAlert("The user's creation date is invalid.");
                case EMPTY_PASSWORD -> AuxiliaryMethods.setErrorLabelState(true,
                        passwordErrorLabel,
                        PasswordTextfield,
                        Optional.of("Password cannot be empty."));
                case EMPTY_EMAIL -> AuxiliaryMethods.setErrorLabelState(true,
                        emailErrorLabel,
                        EmailTextfield,
                        Optional.of("Email cannot be empty."));
                case INVALID_EMAIL -> AuxiliaryMethods.setErrorLabelState(true,
                        emailErrorLabel,
                        EmailTextfield,
                        Optional.of("Invalid email address(example@domain.com)."));
                case EMPTY_USERNAME -> AuxiliaryMethods.setErrorLabelState(true,
                        usernameErrorLabel,
                        UserTextfield,
                        Optional.of("The username cannot be empty."));
                case INVALID_NUMBER -> AuxiliaryMethods.showAlert(
                        "An invalid number was entered(usually a negative number when only positive numbers are allowed).");
            }
        }

    }

    public void BackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signin-view.fxml"));
            Scene newScene = new Scene(loader.load());

            Stage stage = (Stage) EmailTextfield.getScene().getWindow();
            stage.setScene(newScene);
            stage.show();

        } catch (IOException e) {
            AuxiliaryMethods.showAlert("An error occurred when loading signin-view.fxml.");
        }
    }



}
