package com.sonik.ui.controller.login;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.repository.UserRepository;
import com.sonik.infrastructure.persistence.JpaUserRepository;
import com.sonik.service.AuthService;
import com.sonik.service.impl.AuthServiceImpl;
import com.sonik.service.impl.PasswordServiceImpl;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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

public class SignInController {

    public SignInController() {}

    private AuthServiceImpl authService;

    @FXML
    public TextField UserTextfield;
    @FXML
    public PasswordField PasswordTextfield;
    @FXML
    public Button SignInButton;


    public void initialize(AuthServiceImpl authService) {
        this.authService =  authService;
    }

    public void OnkeyPressed_UserTexfield(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            PasswordTextfield.requestFocus();
        }
    }

    public void OnkeyPressed_PasswordTexfield(KeyEvent keyEvent) {}

    public void SignInButton_MouseClicked(MouseEvent mouseEvent) throws ObjectNotFoundException, DataAccessException {
        authService.login(UserTextfield.getText(), PasswordTextfield.getText());
    }

    public void SignUpButton_MouseClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup-view.fxml"));
            Scene newScene = new Scene(loader.load());

            Stage stage = (Stage) SignInButton.getScene().getWindow();
            stage.setScene(newScene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
