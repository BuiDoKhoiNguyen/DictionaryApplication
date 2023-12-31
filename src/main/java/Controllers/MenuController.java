package Controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class MenuController extends TaskControllers implements Initializable {
    @FXML
    private AnchorPane mainAP;
    @FXML
    private AnchorPane searchAP;
    @FXML
    private AnchorPane translateAP;
    @FXML
    private AnchorPane favouriteAP;
    @FXML
    private AnchorPane gameAP;
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
    private Button gameButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button profileButton;

    private SceneController sceneController;

    @FXML
    private VBox vbox;

    public static boolean switchG = false;

    @FXML
    public void searchFunction() {
        mainAP.getChildren().setAll(searchAP);
        searchAP.applyCss();
        searchAP.layout();
        searchButton.requestFocus();
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
    @FXML
    public void gameFunction() {
        AnchorPane.setTopAnchor(gameAP,0.0);
        AnchorPane.setLeftAnchor(gameAP,0.0);
        mainAP.getChildren().setAll(gameAP);
        Button chooseGameButton = (Button) gameAP.lookup("#chooseGame");
        chooseGameButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleChooseGame();
            }
        });
        Button typeGameButton = (Button) gameAP.lookup("#TypeGame");
        typeGameButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleTypeGame();
            }
        });
    }
    private void handleChooseGame() {
        vbox.setDisable(true);
    }

    private void handleTypeGame() {
        vbox.setDisable(true);
    }

    public void logoutButtonOnAction(ActionEvent event) throws IOException {
        LoginController.isLogin = false;
        ProfileController.currtime = 0;
        Stage stageToClose = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageToClose.close();
        sceneController.switchToLogin(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneController = new SceneController();

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(vbox);
        vbox.setTranslateX(-120);
        vbox.setOnMouseEntered(event -> {
            if (event.getSceneX() >= 0 && event.getSceneX() <= 174) {
                slide.setToX(0);
                slide.play();
            }
        });

        vbox.setOnMouseExited(event -> {
            slide.stop();
            slide.setToX(-120);
            slide.play();
        });

        Platform.runLater(() -> {
            searchButton.requestFocus();
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profile.fxml"));
        try {
            profileAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        profileController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
        try {
            searchAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("/fxml/translate.fxml"));
        try {
            translateAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        translateController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("/fxml/favourite.fxml"));
        try {
            favouriteAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        favouriteController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("/fxml/gameMenu.fxml"));
        try {
            gameAP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gameController = loader.getController();

        searchController.loadController(profileController, searchController, translateController, favouriteController,gameController);
        translateController.loadController(profileController, searchController, translateController, favouriteController,gameController);
        favouriteController.loadController(profileController, searchController, translateController, favouriteController,gameController);
        gameController.loadController(profileController, searchController, translateController, favouriteController,gameController);
        favouriteController.initFavouriteDict();

        mainAP.getChildren().setAll(searchAP);

        if(switchG == false){
            mainAP.getChildren().setAll(searchAP);
        }
        else{
            AnchorPane.setTopAnchor(gameAP,0.0);
            AnchorPane.setLeftAnchor(gameAP,0.0);
            mainAP.getChildren().setAll(gameAP);
            Platform.runLater(() -> {
                gameButton.fire();
                gameButton.requestFocus();
            });
            switchG = false;
        }
        Button chooseGameButton = (Button) gameAP.lookup("#chooseGame");
        chooseGameButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleChooseGame();
            }
        });
        Button typeGameButton = (Button) gameAP.lookup("#TypeGame");
        typeGameButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleTypeGame();
            }
        });
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

    public static boolean getSwitch(){
        return switchG;
    }
}
