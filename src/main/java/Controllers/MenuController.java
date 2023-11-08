package Controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends Controllers implements Initializable {
    @FXML
    private AnchorPane mainAP;
    @FXML
    private AnchorPane searchAP;
    @FXML
    private AnchorPane translateAP;
    @FXML

    private AnchorPane usedTimeAP;
    @FXML
    private BorderPane profileAP;
    @FXML
    private Button searchButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button editButton;
    @FXML
    private Button favouriteButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button usedTime;
    @FXML
    private Button profile;
    private SearchController searchController;
    private TranslateController translateController;
    private SceneController sceneController;
    private ProfileController profileController;

    @FXML
    private VBox vbox;
    public MenuController() {
        searchController = new SearchController();
        translateController = new TranslateController();
        sceneController = new SceneController();
        profileController = new ProfileController();
    }


    @FXML
    public void searchFunction() {
        mainAP.getChildren().setAll(searchAP);
    }
    @FXML
    public void translateFunction() {
        mainAP.getChildren().setAll(translateAP);
    }
    @FXML
    public void favouriteFunction() {
        mainAP.getChildren().setAll(favouriteAP);
    }

    @FXML
    public void profileFunction() {
        mainAP.getChildren().setAll(profileAP);
    }


    public void logoutButtonOnAction(ActionEvent event) throws IOException {
        Stage stageToClose = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageToClose.close();
        sceneController.switchToLogin(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(vbox);
        vbox.setTranslateX(-135);
        vbox.setOnMouseEntered(event -> {
            if (event.getSceneX() >= 0 && event.getSceneX() <= 174) {
                slide.setToX(0);
                slide.play();
            }
        });

        vbox.setOnMouseExited(event -> {
            slide.stop();
            slide.setToX(-135);
            slide.play();
        });

        Platform.runLater(() -> searchButton.requestFocus());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/search.fxml"));
        try {
            searchAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchController = loader.getController();
        searchController.loadButton(this.searchButton, this.translateButton, this.favouriteButton, this.saveButton, this.gameButton, this.logoutButton);

        loader = new FXMLLoader(getClass().getResource("/FXML/translate.fxml"));
        try {
            translateAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        translateController = loader.getController();
        translateController.loadButton(this.searchButton, this.translateButton, this.favouriteButton, this.saveButton, this.gameButton, this.logoutButton);

        loader = new FXMLLoader(getClass().getResource("/FXML/favourite.fxml"));
        try {
            favouriteAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        favouriteController = loader.getController();
        favouriteController.loadButton(this.searchButton, this.translateButton, this.favouriteButton, this.saveButton, this.gameButton, this.logoutButton);

        searchController.loadController(searchController, translateController, favouriteController);
        translateController.loadController(searchController, translateController, favouriteController);
        favouriteController.loadController(searchController, translateController, favouriteController);

        loader = new FXMLLoader(getClass().getResource("/FXML/Profile.fxml"));
        try {
            profileAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        profileController = loader.getController();

        mainAP.getChildren().setAll(searchAP);
    }

    private double x = 0;
    private double y = 0;
    @FXML
    private AnchorPane anchorPane = new AnchorPane();
    @FXML
    public void paneDragged(MouseEvent e) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setY(e.getScreenY() - y);
        stage.setX(e.getScreenX() - x);
    }

    @FXML
    public void panePressed(MouseEvent e) {
        x = e.getSceneX();
        y = e.getSceneY();
    }
}

