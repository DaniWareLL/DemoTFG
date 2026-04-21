package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.UserPref;
import com.sonik.domain.model.enums.SourceName;
import com.sonik.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.Arrays;

public class SettingsController {

    private UserService userService;

    // Elementos del FXML
    @FXML
    private ChoiceBox<String> qualityCB;
    @FXML
    private ChoiceBox<String> sourceCB;
    @FXML
    private ChoiceBox<String> themeCB;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button browseButton;
    @FXML
    private Label downloadLocationLabel;
    @FXML
    private Button backHomeButton;

    public void initialize() {
        qualityCB.getItems().addAll(
                Arrays.stream(UserPref.StreamingQuality.values())
                        .map(Enum::name)
                        .toList()
        );

        sourceCB.getItems().addAll(
                Arrays.stream(SourceName.values())
                        .map(Enum::name)
                        .toList()
        );

        themeCB.getItems().addAll(
                Arrays.stream(UserPref.InterfaceTheme.values())
                        .map(Enum::name)
                        .toList()
        );

        // Cargar preferencias del usuario
        var pref = UserSession.getPreferences();

        qualityCB.setValue(pref.getStreamingQuality().name());
        sourceCB.setValue(pref.getAudioSource().name());
        themeCB.setValue(pref.getInterfaceTheme().name());

        downloadLocationLabel.setText(pref.getDownloadLocation());

        // Cargar UserService
        userService = AppContext.getUserService();

    }


    public void browseButtonMC(MouseEvent mouseEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Download Folder");

        File selected = chooser.showDialog(null);
        if (selected != null) {
            downloadLocationLabel.setText(selected.getAbsolutePath());
        }
    }

    public void onSaveChangesMC(MouseEvent mouseEvent) throws IncorrectArgumentException, ObjectNotFoundException, DataAccessException {
        var pref = UserSession.getPreferences();

        pref.setStreamingQuality(UserPref.StreamingQuality.valueOf(qualityCB.getValue()));
        pref.setAudioSource(SourceName.valueOf(sourceCB.getValue()));
        pref.setInterfaceTheme(UserPref.InterfaceTheme.valueOf(themeCB.getValue()));
        pref.setDownloadLocation(downloadLocationLabel.getText());

        userService.updatePreferences(pref);
        UserSession.updatePreferences(pref);
    }

    public void backHomeMC(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home-view.fxml"));
            Node homeView = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
