package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.model.Playlist;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;

public class CreatePlaylistDialogController {

    @FXML
    public Label playlistNameErrorLabel;
    @FXML
    public Label playlistDescriptionErrorLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;

    public void nameTextFieldOnKP(KeyEvent keyEvent) {
        AuxiliaryMethods.setErrorLabelState(false, playlistNameErrorLabel, nameTextField, Optional.empty());
        playlistNameErrorLabel.setManaged(false);
    }

    public void descriptionTextFieldOnKP(KeyEvent keyEvent) {
        AuxiliaryMethods.setErrorLabelState(false, playlistDescriptionErrorLabel, descriptionTextField, Optional.empty());
        playlistDescriptionErrorLabel.setManaged(false);
    }

    public void buildPlaylist() throws IncorrectArgumentException, DuplicateIdException, DataAccessException {
        AppContext.getPlaylistService().createPlaylist(new Playlist(
                UserSession.getUser(),
                nameTextField.getText(),
                descriptionTextField.getText(), LocalDate.now()));
    }

    public void cancelBtnMC(MouseEvent mouseEvent) {
        ((Stage)playlistDescriptionErrorLabel.getScene().getWindow()).close();
    }

    public void createBtnMC(MouseEvent mouseEvent) {
        try {
            buildPlaylist();
            ((Stage)playlistDescriptionErrorLabel.getScene().getWindow()).close();
        } catch (IncorrectArgumentException e) {
            switch (e.getErrorType()) {
                case NULL_OBJECT_RECEIVED, INVALID_DATE -> AuxiliaryMethods.showAlert(e);
                case EMPTY_PLAYLIST_NAME -> {
                    AuxiliaryMethods.setErrorLabelState(
                        true, playlistNameErrorLabel, nameTextField, Optional.of(e.getMessage()));
                    playlistNameErrorLabel.setManaged(true);
                }
                case EMPTY_PLAYLIST_DESCRIPTION -> {
                    AuxiliaryMethods.setErrorLabelState(
                        true, playlistDescriptionErrorLabel, descriptionTextField, Optional.of(e.getMessage()));
                    playlistDescriptionErrorLabel.setManaged(true);
                }
            }
        } catch (DuplicateIdException e) {
            AuxiliaryMethods.setErrorLabelState(true, playlistNameErrorLabel, nameTextField, Optional.of(e.getMessage()));
            playlistDescriptionErrorLabel.setManaged(true);
        } catch (DataAccessException e) {
            AuxiliaryMethods.showAlert(e);
        }
    }
}
