package com.sonik.ui.controller.login;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.infrastructure.persistence.JpaUserRepository;
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

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sonik");

    private final AuthServiceImpl authService = new AuthServiceImpl(new JpaUserRepository(emf), new PasswordServiceImpl());

    @FXML
    public TextField UserTextfield;
    @FXML
    public PasswordField PasswordTextfield;
    @FXML
    public Button SignInButton;

    @FXML
    public Button ButtonPrueba;


    public void initialize() {
        // init presets
    }

    public void OnkeyPressed_UserTexfield(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            PasswordTextfield.requestFocus();
        }
    }

    public void OnkeyPressed_PasswordTexfield(KeyEvent keyEvent) {

    }

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
