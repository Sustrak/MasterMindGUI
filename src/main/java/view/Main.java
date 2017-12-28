package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import layers.PresentationCtrl;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main  extends Application{

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(new URL(Paths.get("src/main/resources/GUI/FXML/LoginView.fxml").toUri().toString()));
        stage = primaryStage;
        stage.setTitle("Master Mind");
        stage.setScene(new Scene(root, 911, 510));
        // MUSIC
        Media media = new Media(Paths.get("src/main/resources/GUI/Music/MM_music.mp4").toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setVolume(.05);
        player.setCycleCount(9999);
        player.play();
        // MUSIC
        stage.show();

    }

    public static void changeScene(FXMLLoader Loader) {
        Parent root = Loader.getRoot();
        stage.setScene(new Scene(root, 911, 510));
    }


    public static void main(String[] args) {
        // TODO code application logic here

        //PresentationCtrl pCtrl = new PresentationCtrl();
        //pCtrl.startMasterMind();

        launch(args);

    }

}