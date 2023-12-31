package Controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SwitchGame implements Initializable {
    @FXML
    private Button playButton;

    @FXML
    private AnchorPane chooseMenu;

    String mediaFile = "/sources_music_picture/good-night-160166.mp3";
    URL resourceUrl = getClass().getResource(mediaFile);
    Media media = new Media(resourceUrl.toExternalForm());

    MediaPlayer mediaPlayer = new MediaPlayer(media);

    public void playGame(ActionEvent event) throws Exception {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1));
        fadeOut.setNode(((javafx.scene.Node) event.getSource()).getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simpleGame.fxml"));
                mediaPlayer.stop();
                Parent scene2Parent = loader.load();
                Scene scene2 = new Scene(scene2Parent);

                Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                window.setScene(scene2);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1));
                fadeIn.setNode(scene2.getRoot());
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        fadeOut.play();
    }

    //switch to main menu game
    public void backMenu(ActionEvent event) throws Exception{
        //SceneController.switchBack(event);
        MenuController.switchG = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent scene2Parent = loader.load();
        Scene scene2 = new Scene(scene2Parent);

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
       mediaPlayer.stop();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
        mediaPlayer.play();
        playButton.getStyleClass().add("buttonPlay");

    }
}
