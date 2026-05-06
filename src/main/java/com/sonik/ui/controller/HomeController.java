package com.sonik.ui.controller;

import com.sonik.domain.model.Song;
import com.sonik.ui.navigation.ViewManager;
import com.sonik.ui.navigation.ViewType;
import javafx.application.Platform;
import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.List;

public class HomeController {

    // Botones de ventana
    @FXML private Button minBtn;
    @FXML private Button maxBtn;
    @FXML private Button closeBtn;

    private Stage stage;

    @FXML
    private BorderPane mainContainer;

    @FXML
    private VBox leftContainer;

    // Top bar / búsqueda
    @FXML
    private TextField searchBar;

    @FXML
    private Button buttonOptions;

    // Menú lateral
    @FXML
    private Button ButtonHome;

    @FXML
    private Button buttonExplore;

    @FXML
    private Button buttonPlaylist;

    @FXML
    private Button buttonSongs;

    // Reproductor inferior

    @FXML
    private Label userNameLabel;

    @FXML
    private Node homeContent;

    @FXML
    private Node leftContent;

    private Node searchPanel;
    private SearchController searchController;

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() {

        ViewManager.setMainContainer(mainContainer);
        ViewManager.loadIntoBottom(ViewType.PLAYER_BOTTOMBAR);

        homeContent = mainContainer.getCenter();
        leftContent = mainContainer.getLeft();

        userNameLabel.setText(UserSession.getUser().getUserName());

        enableWindowDrag();

        // Min
        minBtn.setOnAction(e -> stage.setIconified(true));
        minBtn.setOnMousePressed(e -> {
            minBtn.setStyle("-fx-background-color:  #191919;");
        });
        minBtn.setOnMouseReleased(e -> {
            minBtn.setStyle("-fx-background-color: black;");
        });

        // Max
        maxBtn.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));
        maxBtn.setOnMousePressed(e -> {
            maxBtn.setStyle("-fx-background-color:  #191919;");
        });
        maxBtn.setOnMouseReleased(e -> {
            maxBtn.setStyle("-fx-background-color: black;");
        });

        // Close
        closeBtn.setOnAction(e -> stage.close());
        closeBtn.setOnMousePressed(e -> {
            closeBtn.setStyle("-fx-background-color: red;");
        });
        closeBtn.setOnMouseReleased(e -> {
            closeBtn.setStyle("-fx-background-color: black;");
        });
    }

    public void settingButtonMC(MouseEvent mouseEvent) {
        ViewManager.loadIntoCenter(ViewType.SETTINGS);
    }

    public void exploreButtonOnMC(MouseEvent mouseEvent) {
        searchBar.requestFocus();
    }

    public void playlistButtonOnMC(MouseEvent mouseEvent) {
        ViewManager.loadIntoCenter(ViewType.PLAYLIST);
        ViewManager.loadIntoLeft(ViewType.PLAYLIST_SIDEBAR);
    }

    public void songButtonOnMC(MouseEvent mouseEvent) {
    }

    private void enableWindowDrag() {

        Node topBar = mainContainer.getTop();

        topBar.setOnMousePressed(event -> {
            Stage stage = (Stage) mainContainer.getScene().getWindow();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) mainContainer.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public void searchBarOnKP(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {

            String searchPattern = searchBar.getText();

            AppContext.getExecutor().submit(() -> {
                try {

                    List<Song> results = AppContext.getMetadataService().getMetadata(searchPattern);

                    Platform.runLater(() -> {
                        SearchController controller = ViewManager.loadIntoCenterWithController(ViewType.SEARCH);
                        controller.setResults(results);
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void homeBtnMC(MouseEvent mouseEvent) {
        mainContainer.setCenter(homeContent);
        mainContainer.setLeft(leftContent);
    }
}
