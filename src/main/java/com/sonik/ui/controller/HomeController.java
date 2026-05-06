package com.sonik.ui.controller;

import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Song;
import javafx.application.Platform;
import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Button ButtonPreviousSong;

    @FXML
    private Button ButtonPlay;

    @FXML
    private Button buttonNextSong;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label totalTimeLabel;

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

        homeContent = mainContainer.getCenter(); // guardas el contenido original
        leftContent = mainContainer.getLeft();   // ← sidebar original
        userNameLabel.setText(UserSession.getUser().getUserName());

        enableWindowDrag();

        // Acciones de la ventana
        minBtn.setOnAction(e -> stage.setIconified(true));

        maxBtn.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));

        closeBtn.setOnAction(e -> {AppContext.shutDown();
            stage.close();});

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/search-view.fxml"));
            searchPanel = loader.load();
            searchController = loader.getController();
        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e.getMessage());
            e.printStackTrace();
        }

    }

    public void settingButtonMC(MouseEvent mouseEvent) {
        try {
            // Cargar ajustes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/settings-view.fxml"));
            Node settingsPanel = loader.load();

            // Configurar controller
            SettingsController controller = loader.getController();
            controller.setMainContainer(mainContainer);
            controller.setHomeContent(homeContent);

            // SOLO cambiar el centro - izquierda y abajo se quedan igual
            mainContainer.setCenter(settingsPanel);

        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    public void exploreButtonOnMC(MouseEvent mouseEvent) {
        searchBar.requestFocus();
    }

    public void playlistButtonOnMC(MouseEvent mouseEvent) {
        try {
            // Cargar panel central
            FXMLLoader loaderCenter = new FXMLLoader(getClass().getResource("/views/playlist-view.fxml"));
            Node playlistPanel = loaderCenter.load();

            // Cargar panel izquierdo (sidebar)
            FXMLLoader loaderLeft = new FXMLLoader(getClass().getResource("/views/playlist-sidebar-view.fxml"));
            Node playlistSidebar = loaderLeft.load();

            // Configurar controllers si hace falta
            PlaylistController controllerCenter = loaderCenter.getController();
            PlaylistSidebarController controllerLeft = loaderLeft.getController();

            // Cambiar CENTER y LEFT
            mainContainer.setCenter(playlistPanel);
            mainContainer.setLeft(playlistSidebar);

        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    public void songButtonOnMC(MouseEvent mouseEvent) {
    }

    private void enableWindowDrag() {
        // El top bar es el HBox que tienes en el <top> del BorderPane
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
                        searchController.setResults(results);
                        mainContainer.setCenter(searchPanel);
                    });

                } catch (AudioExtractorException e) {
                    AuxiliaryMethods.showAlert(e.getMessage());
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
