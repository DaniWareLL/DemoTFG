package com.sonik.ui.controller;

import com.sonik.domain.model.Song;
import com.sonik.ui.navigation.ViewManager;
import com.sonik.ui.navigation.ViewType;
import javafx.application.Platform;
import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;

public class HomeController {

    // Botones de ventana
    @FXML private Button minBtn;
    @FXML private Button maxBtn;
    @FXML private Button closeBtn;
    @FXML private FontIcon maxIcon;

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
    private Button libraryBtn;

    // Reproductor inferior

    @FXML
    private Label userNameLabel;

    @FXML
    private Node homeContent;

    @FXML
    private Node leftContent;

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() {

        ViewManager.setMainContainer(mainContainer);
        try {
            ViewManager.loadIntoBottom(ViewType.PLAYER_BOTTOMBAR);
        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        }

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
        maxBtn.setOnMousePressed(e -> {
            maxBtn.setStyle("-fx-background-color: #191919;");
        });

        maxBtn.setOnMouseReleased(e -> {
            maxBtn.setStyle("-fx-background-color: black;");
        });

        maxBtn.setOnAction(e -> {
            if (!stage.isMaximized()) {
                maxIcon.setIconLiteral("mdi2w-window-restore");
                // Undecorated maximazied no respeta los limites de la barra de tareas
                // Obtener los límites de la pantalla (respetando la barra de tareas)
                javafx.geometry.Rectangle2D screenBounds =
                        javafx.stage.Screen.getPrimary().getVisualBounds();

                // Configurar la ventana al tamaño de la pantalla (sin cubrir barra de tareas)
                stage.setX(screenBounds.getMinX());
                stage.setY(screenBounds.getMinY());
                stage.setWidth(screenBounds.getWidth());
                stage.setHeight(screenBounds.getHeight());

                stage.setMaximized(true);
            } else {
                maxIcon.setIconLiteral("mdi2w-window-maximize");
                stage.setMaximized(false);
            }
        });

        // Close
        closeBtn.setOnAction(e -> stage.close());
        closeBtn.setOnMousePressed(e -> {
            closeBtn.setStyle("-fx-background-color: red;");
        });
        closeBtn.setOnMouseReleased(e -> {
            closeBtn.setStyle("-fx-background-color: black;");
            AppContext.shutDown();
        });
    }

    public void settingButtonMC(MouseEvent mouseEvent) {
        try {
            ViewManager.loadIntoCenter(ViewType.SETTINGS);
        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        }
    }

    public void exploreButtonOnMC(MouseEvent mouseEvent) {
        searchBar.requestFocus();
    }

    public void playlistButtonOnMC(MouseEvent mouseEvent) {
        try {
            ViewManager.loadIntoCenter(ViewType.PLAYLIST);
            ViewManager.loadIntoLeft(ViewType.PLAYLIST_SIDEBAR);
        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        }
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
                        SearchController controller = null;
                        try {
                            controller = ViewManager.loadIntoCenterWithController(ViewType.SEARCH);
                        } catch (IOException e) {
                            AuxiliaryMethods.showAlert(e);
                        }
                        controller.setResults(results);
                    });

                } catch (Exception e) {
                    AuxiliaryMethods.showAlert(e);
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

    public void libraryBtnMC(ActionEvent actionEvent) {
        try {
            ViewManager.loadIntoCenter(ViewType.LIBRARY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
