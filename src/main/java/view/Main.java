package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import layers.PresentationCtrl;

import java.io.IOException;

public class Main  extends Application{

    static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/GUI/FXML/LoginView.fxml"));
        stage = primaryStage;
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 911, 510));
        stage.show();
    }

    public static void changeScene(FXMLLoader Loader) throws IOException {
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