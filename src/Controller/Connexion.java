package Controller;

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

import DAO.UtilisateurDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Model.Utilisateur;

public class Connexion {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    // 🔐 Utilisateur actuellement connecté
    private static Utilisateur utilisateurConnecte;

    // ✅ Accesseur
    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    // 🔓 Déconnexion
    public static void logout() {
        utilisateurConnecte = null;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = usernameField.getText();
        String motDePasse = passwordField.getText();

        String type = UtilisateurDAO.verifierUtilisateur(email, motDePasse);
        Utilisateur utilisateur = UtilisateurDAO.getUtilisateurByEmailAndPassword(email, motDePasse); // ajout de récupération complète

        if (type == null || utilisateur == null) {
            showAlert("Erreur", "Email ou mot de passe incorrect.");
        } else {
            utilisateurConnecte = utilisateur; // ✅ on garde l'utilisateur connecté

            try {
                String fxmlFile = switch (type) {
                    case "Patient" -> "/View/CompteClient.fxml";
                    case "Administrateur" -> "/View/CompteAdmin.fxml";
                    default -> null;
                };

                if (fxmlFile != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                    Parent root = loader.load();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    showAlert("Erreur", "Type d'utilisateur inconnu.");
                }

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Impossible de charger la page.");
            }
        }
    }

    public void goToCreerCompte(ActionEvent event) {
        try {
            File fxml = new File("src/View/CreerCompte.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Creer Compte");
            stage.setScene(new Scene(root));

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

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
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
}
