package com.sonik.ui.controller;

import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;
import com.sonik.ui.navigation.ViewManager;
import com.sonik.ui.navigation.ViewType;
import javafx.application.Platform;
import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sonik.ui.controller.AuxiliaryMethods.loadAndPlay;

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

    @FXML
    private HBox hBoxLibrary;
    @FXML private VBox fSong1, fSong2, fSong3, fSong4, fSong5, fSong6;
    @FXML private ImageView fImg1, fImg2, fImg3, fImg4, fImg5, fImg6;
    @FXML private Label fLabel1, fLabel2, fLabel3, fLabel4, fLabel5, fLabel6;
    @FXML private StackPane cover1, cover2, cover3, cover4, cover5, cover6;

    private VBox[] songBoxes;
    private ImageView[] songImages;
    private Label[] songLabels;
    private StackPane[] covers;

    private List<Song> songsLoaded;

    @FXML private HBox hBoxPlaylists;

    @FXML private VBox pBox1, pBox2, pBox3, pBox4;
    @FXML private ImageView pImg1, pImg2, pImg3, pImg4;
    @FXML private Label pName1, pName2, pName3, pName4;
    @FXML private Label pDesc1, pDesc2, pDesc3, pDesc4;

    private VBox[] playlistBoxes;
    private ImageView[] playlistImages;
    private Label[] playlistNames;
    private Label[] playlistDescs;


    public void initialize() {

        ViewManager.setMainContainer(mainContainer);
        try {
            ViewManager.loadIntoBottom(ViewType.PLAYER_BOTTOMBAR);
        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        }

        homeContent = mainContainer.getCenter();
        leftContent = mainContainer.getLeft();

        songBoxes = new VBox[]{fSong1, fSong2, fSong3, fSong4, fSong5, fSong6};
        songImages = new ImageView[]{fImg1, fImg2, fImg3, fImg4, fImg5, fImg6};
        songLabels = new Label[]{fLabel1, fLabel2, fLabel3, fLabel4, fLabel5, fLabel6};
        covers = new StackPane[]{cover1, cover2, cover3, cover4, cover5, cover6};

        songsLoaded = new ArrayList<>();

        for (VBox box : songBoxes) {
            box.setOnMouseClicked(event -> {
                VBox clicked = (VBox) event.getSource();
                Song s = (Song) clicked.getUserData();
                if (s != null) playFavoriteSong(s);
            });
        }

        loadFavoriteSongs();

        playlistBoxes = new VBox[]{pBox1, pBox2, pBox3, pBox4};
        playlistImages = new ImageView[]{pImg1, pImg2, pImg3, pImg4};
        playlistNames = new Label[]{pName1, pName2, pName3, pName4};
        playlistDescs = new Label[]{pDesc1, pDesc2, pDesc3, pDesc4};

        loadHomePlaylists();

        for (VBox box : playlistBoxes) {
            box.setOnMouseClicked(event -> {
                VBox clicked = (VBox) event.getSource();
                var playlist = clicked.getUserData();
                if (playlist != null) openPlaylist(playlist);
            });
        }


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
            List<Playlist> playlists = AppContext.getPlaylistService().findAllPlaylistsForUser(UserSession.getUser().getUserName());

            PlaylistController controller = ViewManager.loadIntoCenterWithController(ViewType.PLAYLIST);
            controller.loadPlaylist(playlists.getFirst());
            ViewManager.loadIntoLeft(ViewType.PLAYLIST_SIDEBAR);
        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (IncorrectArgumentException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
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
        searchBar.setText("");
    }

    public void libraryBtnMC(ActionEvent actionEvent) {
        try {
            ViewManager.loadIntoCenter(ViewType.LIBRARY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFavoriteSongs() {

        AppContext.getExecutor().submit(() -> {
            try {
                List<Song> favorites = AppContext.getLibraryService().getFavouriteSongs();

                Platform.runLater(() -> {

                    double size = 110;

                    for (int i = 0; i < favorites.size() && i < 6; i++) {

                        Song song = favorites.get(i);

                        ImageView img = songImages[i];
                        StackPane container = covers[i];

                        container.setPrefSize(size, size);
                        container.setMaxSize(size, size);

                        img.setPreserveRatio(true);
                        img.setFitWidth(size * 1.6);
                        img.setFitHeight(size * 1.6);

                        StackPane.setAlignment(img, Pos.CENTER);

                        Rectangle clip = new Rectangle(size, size);
                        clip.setArcWidth(15);
                        clip.setArcHeight(15);
                        container.setClip(clip);

                        img.setImage(new Image(song.getThumbnailUrl()));

                        songLabels[i].setMaxWidth(110);
                        songLabels[i].setText(song.getTitle());
                        songBoxes[i].setVisible(true);

                        //  Guardar canción para el evento
                        Song loadedSong = favorites.get(i);

                        songBoxes[i].setUserData(loadedSong);
                        covers[i].setUserData(loadedSong);

                        int index = favorites.size();

                        // Actualizo cola
                        AppContext.getPlaybackQueueService().setQueue(
                                favorites,
                                index
                        );
                    }

                });

            } catch (Exception e) {
                AuxiliaryMethods.showAlert(e);
            }
        });
    }

    private void playFavoriteSong(Song song) {
        loadAndPlay(song);
    }

    private void loadHomePlaylists() {

        AppContext.getExecutor().submit(() -> {
            try {
                var playlists = AppContext.getPlaylistService().findAllPlaylistsForUser(UserSession.getUser().getUserName());

                Platform.runLater(() -> {

                    for (int i = 0; i < playlists.size() && i < 4; i++) {

                        var pl = playlists.get(i);

                        playlistNames[i].setText(pl.getName());
                        playlistDescs[i].setText(pl.getDescription());

                        playlistBoxes[i].setUserData(pl);
                        playlistBoxes[i].setVisible(true);
                    }

                });

            } catch (Exception e) {
                AuxiliaryMethods.showAlert(e);
            }
        });
    }

    private void openPlaylist(Object playlist) {
        try {
            PlaylistController controller = ViewManager.loadIntoCenterWithController(ViewType.PLAYLIST);
            controller.loadPlaylist((Playlist) playlist);
            ViewManager.loadIntoLeft(ViewType.PLAYLIST_SIDEBAR);
        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        }
    }

}
