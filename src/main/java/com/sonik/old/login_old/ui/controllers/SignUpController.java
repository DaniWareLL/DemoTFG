package com.sonik.old.login_old.ui.controllers;

import com.sonik.old.login_old.dao.IUserDAO;
import com.sonik.old.login_old.exceptions.DataAccessException;
import com.sonik.old.login_old.exceptions.DuplicateIdException;
import com.sonik.old.login_old.model.User;
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

import java.time.LocalDate;

public class SignUpController {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("login");;
    private final UserService userService = new UserService(new IUserDAO(emf));

    @FXML
    public TextField EmailTextfield;
    @FXML
    public TextField UserTextfield;
    @FXML
    public PasswordField PasswordTextfield;
    @FXML
    public Button CreateAccountButton;


    public void initialize() {
        // init presets
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
        userService.register(user);
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
