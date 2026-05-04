package com.sonik.ui.controller;

import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.model.Playlist;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class CreatePlaylistDialogController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    private Dialog<?> dialog;

    public void setDialog(Dialog<?> dialog) {
        this.dialog = dialog;
    }

    public Playlist buildPlaylist() throws IncorrectArgumentException {
        return new Playlist(
                UserSession.getUser(),
                nameTextField.getText(),
                descriptionTextField.getText(), LocalDate.now()

        );
    }

    public void onSaveChangesMC(MouseEvent mouseEvent) {
    }

    public void cancelBtnMC(MouseEvent mouseEvent) {
        //dialog.setResult(ButtonType.CANCEL);
        dialog.close();
    }

    public void createBtnMC(MouseEvent mouseEvent) throws IncorrectArgumentException {
        buildPlaylist();
    }
}
