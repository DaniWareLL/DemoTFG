package com.sonik.ui.controller.login;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.model.User;
import com.sonik.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SignUpController {

    private AuthService authService;
    /* No creas un objeto de la impl para desacoplar el codigo y cumplir el principio
     de inversion de dependencias */

    @FXML
    public TextField EmailTextfield;
    @FXML
    public TextField UserTextfield;
    @FXML
    public PasswordField PasswordTextfield;
    @FXML
    public Button CreateAccountButton;


    public void initialize() {
        this.authService = AppContext.getAuthService();;
    }

    public void OnkeyPressed_EmailTexfield(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            UserTextfield.requestFocus();
        }
    }

    public void OnkeyPressed_UserTexfield(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            PasswordTextfield.requestFocus();
        }
    }

    public void OnkeyPressed_PasswordTexfield(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            CreateAccountButton.requestFocus();
        }
    }

    public void CreateAccountButton_MouseClicked(MouseEvent mouseEvent) throws DuplicateIdException, DataAccessException {
        User user = new User(UserTextfield.getText(), EmailTextfield.getText(), PasswordTextfield.getText(), LocalDate.now());
        authService.register(user);
        BackToLogin();
    }

    public void BackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signin-view.fxml"));
            Scene newScene = new Scene(loader.load());

            Stage stage = (Stage) EmailTextfield.getScene().getWindow();
            stage.setScene(newScene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
