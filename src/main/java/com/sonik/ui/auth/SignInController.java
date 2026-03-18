package com.sonik.ui.auth;

import com.sonik.old.login_old.dao.IUserDAO;
import com.sonik.old.login_old.exceptions.ObjectNotFoundException;
import com.sonik.old.login_old.service.UserService;
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

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("login");;
    private final UserService userService = new UserService(new IUserDAO(emf));

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

    public void SignInButton_MouseClicked(MouseEvent mouseEvent) throws ObjectNotFoundException {
        userService.login(UserTextfield.getText(), PasswordTextfield.getText());
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
