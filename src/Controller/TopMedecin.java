package Controller;

import DAO.DatabaseConnection;
import Model.Session;
import Model.Specialiste;
import Model.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;

public class TopMedecin {

    @FXML
    private VBox medecinList;

    @FXML
    public void initialize() {
        chargerTopMedecins();
    }

    private void chargerTopMedecins() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = """
                SELECT s.nom, s.prenom, COUNT(r.id_rdv) as nb_rdv
                FROM specialiste s
                JOIN rendezvous r ON s.id_specialiste = r.id_specialiste
                GROUP BY s.id_specialiste
                ORDER BY nb_rdv DESC
                LIMIT 3
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int nbRdv = rs.getInt("nb_rdv");

                // Créer dynamiquement une carte médecin
                HBox medecinCard = createMedecinCard(nom, prenom, nbRdv);

                medecinList.getChildren().add(medecinCard);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createMedecinCard(String nom, String prenom, int nbRdv) {
        HBox card = new HBox(20);
        card.setStyle("-fx-background-color: #45aeb6; -fx-background-radius: 20; -fx-padding: 10;");
        card.setPrefHeight(128);
        card.setPrefWidth(843);
        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(110, 110);
        imagePane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-radius: 50; -fx-background-radius: 50;");
        ImageView profileImage = new ImageView("/Images/compte.png");
        profileImage.setFitHeight(48);
        profileImage.setFitWidth(48);
        imagePane.getChildren().add(profileImage);
        Label nameLabel = new Label(nom + " " + prenom + " - " + nbRdv + " RDV");
        nameLabel.setStyle("-fx-background-color: #6abec4; -fx-background-radius: 15; -fx-padding: 10 20;");
        nameLabel.setMinWidth(430);
        card.getChildren().addAll(imagePane, nameLabel);
        return card;
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
    void goToConnexion(ActionEvent event) {
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


}
