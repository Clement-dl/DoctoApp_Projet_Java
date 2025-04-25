package Controller;

import Model.Patient;
import Model.Session;
import DAO.PatientDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CompteClient {
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
    void logout(ActionEvent event) {
        Connexion.logout(); // Réinitialise
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ConnecterVous.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    @FXML private Label nomPrenomLabel;
    @FXML private Label nomUtilisateurLabel;
    @FXML private Label adresseLabel;
    @FXML private Label villeLabel;
    @FXML private Label codePostalLabel;
    @FXML private Label telephoneLabel;
    @FXML private Label carteVitaleLabel;
    @FXML private Label mdpLabel;

    public void initialize() {
        // Vérifier si l'utilisateur est connecté
        if (Session.estConnecte()) {
            // Récupère l'ID de l'utilisateur connecté
            int utilisateurId = Session.getUtilisateurId();

            // Récupère les informations du patient à partir de l'ID
            Patient patient = PatientDAO.getPatientById(utilisateurId);

            // Vérifie si le patient existe
            if (patient != null) {
                // Met à jour les labels avec les informations du patient
                nomPrenomLabel.setText(patient.getNom() + " " + patient.getPrenom());
                nomUtilisateurLabel.setText(patient.getNomUtilisateur());
                adresseLabel.setText(patient.getAdresse());
                villeLabel.setText(patient.getVille());
                codePostalLabel.setText(patient.getCodePostal());
                telephoneLabel.setText(patient.getTelephone());
                carteVitaleLabel.setText(patient.getNumSecu() != null ? "Oui" : "Non");
                mdpLabel.setText("**********"); // Par sécurité, on ne montre jamais le mot de passe
            } else {
                // Si le patient n'est pas trouvé, on peut afficher un message d'erreur
                System.out.println("Utilisateur non trouvé.");
            }
        } else {
            // Si l'utilisateur n'est pas connecté, on peut rediriger vers la page de connexion
            System.out.println("Utilisateur non connecté.");
        }
    }
}
