package Controller;

import DAO.SpecialisteDAO;
import Model.Specialiste;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class OrthopedieAdmin {
    @FXML
    private VBox medecinListContainer;

    @FXML
    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rdvspe", "root", "");
            SpecialisteDAO dao = new SpecialisteDAO(conn);
            List<Specialiste> orthopedie = dao.getSpecialistesParSpecialite("Orthopedie");

            for (Specialiste sp : orthopedie) {
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
                ImageView photo;

                if (sp.getImage() != null && !sp.getImage().isEmpty() && !sp.getImage().equals("default")) {
                    // Si une image personnalisée existe
                    File imageFile = new File("src/Images/" + sp.getImage());
                    if (imageFile.exists()) {
                        photo = new ImageView(new Image(imageFile.toURI().toString()));
                    } else {
                        // Si le fichier n'existe pas, utiliser l'image par défaut
                        photo = new ImageView(new Image(getClass().getResourceAsStream("/Images/compte.png")));
                    }
                } else {
                    // Sinon utiliser image par défaut
                    photo = new ImageView(new Image(getClass().getResourceAsStream("/Images/compte.png")));
                }
                photo.setFitHeight(64);
                photo.setFitWidth(64);
                stackPane.getChildren().add(photo);
                Label info = new Label(sp.getNomComplet() + "\n" + sp.getQualification());
                info.setAlignment(Pos.CENTER);
                info.setMinHeight(78);
                info.setMinWidth(430);
                info.setTranslateX(50);
                info.setStyle("-fx-background-color: #cfe1e9; -fx-background-radius: 15; -fx-padding: 10 20;");
                Button rdvButton = new Button("Prendre rendez-vous");
                rdvButton.setMinHeight(40);
                rdvButton.setMinWidth(134);
                rdvButton.setTranslateX(100);
                rdvButton.setStyle("-fx-background-color: #156e95; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 5 10; -fx-cursor: hand;");
                rdvButton.setOnAction(e -> goToRDVAdmin(e, sp));
                hbox.getChildren().addAll(stackPane, info, rdvButton);
                medecinListContainer.getChildren().add(hbox);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void goToAccueilAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/AccueilAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("AccueilAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRDVAdmin(ActionEvent event, Specialiste specialiste) {
        try {
            File fxml = new File("src/View/RDVAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            RDVAdmin controller = loader.getController();
            controller.setSpecialiste(specialiste);
            controller.setUtilisateur(Connexion.getUtilisateurConnecte());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("RDVAdmin");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToConnexionAdmin(ActionEvent event) {
        try {
            Utilisateur utilisateur = Connexion.getUtilisateurConnecte();

            if (utilisateur != null) {
                String type = utilisateur.getType();
                String fxmlFile = switch (type) {
                    case "Patient" -> "/View/CompteClient.fxml";
                    case "Administrateur" -> "/View/CompteAdmin.fxml";
                    default -> null;
                };

                if (fxmlFile != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                    Scene scene = new Scene(loader.load());
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }

            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ConnecterVous.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToDashboard(ActionEvent event) {
        try {
            File fxml = new File("src/View/Dashboard.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToTopMedecinAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/TopMedecinAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("TopMedecinAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
