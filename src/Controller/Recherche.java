package Controller;

import Model.Specialiste;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Recherche {
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
    private void goToRDV(ActionEvent event, Specialiste specialiste) {
        try {
            File fxml = new File("src/View/RDV.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            RDV controller = loader.getController();
            controller.setSpecialiste(specialiste);
            controller.setUtilisateur(Connexion.getUtilisateurConnecte()); // si méthode disponible


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("RDV");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToPaiement(ActionEvent event) {
        try {
            File fxml = new File("src/View/Paiement.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Paiement");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToTopMedecin(ActionEvent event) {
        try {
            File fxml = new File("src/View/TopMedecin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("TopMedecin");
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
