package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        File fxml = new File("src/View/Accueil.fxml");
        URL fxmlUrl = fxml.toURI().toURL();
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        primaryStage.setFullScreen(true); // Mode plein écran
        primaryStage.setTitle("Accueil");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
