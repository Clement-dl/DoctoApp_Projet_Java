package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import DAO.SpecialisteDAO;
import Model.Specialiste;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class Generaliste {

    @FXML
    private VBox medecinListContainer;

    @FXML
    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rdvspe", "root", "");
            SpecialisteDAO dao = new SpecialisteDAO(conn);
            List<Specialiste> generalistes = dao.getSpecialistesParSpecialite("Généraliste");

            for (Specialiste sp : generalistes) {
                HBox hbox = new HBox(20);
                hbox.setPrefHeight(128);
                hbox.setPrefWidth(843);
                hbox.setMinHeight(128);
                hbox.setMinWidth(843);
                hbox.setMaxHeight(128);
                hbox.setMaxWidth(848);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-padding: 10;");
                StackPane stackPane = new StackPane();
                stackPane.setPrefHeight(110);
                stackPane.setPrefWidth(110);
                stackPane.setMinHeight(115);
                stackPane.setStyle("-fx-border-color: #a9a9a9; -fx-border-radius: 50; -fx-background-radius: 50;");
                ImageView photo = new ImageView(new Image(getClass().getResourceAsStream("/Images/compte.png")));
                photo.setFitHeight(48);
                photo.setFitWidth(48);
                stackPane.getChildren().add(photo);
                Label info = new Label(sp.getNomComplet() + "\n" + sp.getQualification());
                info.setAlignment(Pos.CENTER);
                info.setMinHeight(78);
                info.setMinWidth(430);
                info.setTranslateX(50);
                info.setStyle("-fx-background-color: #e6e3f7; -fx-background-radius: 15; -fx-padding: 10 20;");
                Button rdvButton = new Button("Prendre rendez-vous");
                rdvButton.setMinHeight(40);
                rdvButton.setMinWidth(134);
                rdvButton.setTranslateX(100);
                rdvButton.setStyle("-fx-background-color: #5e4af1; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 5 10; -fx-cursor: hand;");
                rdvButton.setOnAction(e -> goToRDV(e, sp));
                hbox.getChildren().addAll(stackPane, info, rdvButton);
                medecinListContainer.getChildren().add(hbox);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRDV(ActionEvent event, Specialiste specialiste) {
        try {
            File fxml = new File("src/View/RDV.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("RDV");
            stage.setScene(new Scene(root));
            System.out.println("Bouton RDV cliqué pour : " + specialiste.getNomComplet());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToAccueil(ActionEvent event) {
        try {
            File fxml = new File("src/View/Accueil.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRDV(ActionEvent event) {
        System.out.println("Méthode goToRDV appelée depuis le FXML (pas utilisée)");
    }
}
