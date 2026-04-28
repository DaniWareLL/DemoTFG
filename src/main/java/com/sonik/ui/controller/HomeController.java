package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import com.sonik.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {

    // Botones de ventana
    @FXML private Button minBtn;
    @FXML private Button maxBtn;
    @FXML private Button closeBtn;

    private Stage stage;

    @FXML
    private BorderPane mainContainer;

    // Top bar / búsqueda
    @FXML
    private TextField SearchBar;

    @FXML
    private Button ButtonOptions;

    // Menú lateral
    @FXML
    private Button ButtonHome;

    @FXML
    private Button ButtonExplore;

    @FXML
    private Button ButtonPlaylist;

    @FXML
    private Button ButtonSongs;

    // Reproductor inferior
    @FXML
    private Button ButtonPreviousSong;

    @FXML
    private Button ButtonPlay;

    @FXML
    private Button ButtonNextSong;

    @FXML
    private ProgressBar ProgressBar;

    @FXML
    private Slider VolumeSlider;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label totalTimeLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Node homeContent;

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() {
        homeContent = mainContainer.getCenter(); // guardas el contenido original
        userNameLabel.setText(UserSession.getUser().getUserName());
        enableWindowDrag();

        // Acciones de la ventana
        minBtn.setOnAction(e -> stage.setIconified(true));

        maxBtn.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));

        closeBtn.setOnAction(e -> stage.close());
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exploreButtonOnMC(MouseEvent mouseEvent) {
        SearchBar.requestFocus();
    }

    public void playlistButtonOnMC(MouseEvent mouseEvent) {
    }

    public void songButtonOnMC(MouseEvent mouseEvent) {
    }

    public void homeButtonOnMC(MouseEvent mouseEvent) {

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
        String search =  SearchBar.getText();
        AppContext.getPlayerService().getStreamUrl(search);
        AppContext.getMetadataService().getMetadata(search);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
