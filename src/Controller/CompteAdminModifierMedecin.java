package Controller;

import DAO.DatabaseConnection;
import DAO.PatientDAO;
import DAO.SpecialisteDAO;
import DAO.UtilisateurDAO;
import Model.Specialiste;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CompteAdminModifierMedecin {
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
    private ImageView imageMedecinView;

    @FXML
    private Label descriptionMedecinLabel;

    public void chargerImageMedecin(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageMedecinView.setImage(image);
            descriptionMedecinLabel.setText("Image chargée : " + selectedFile.getName());
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

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField specialiteField;
    @FXML
    private TextField qualificationField;

    private Utilisateur utilisateur;


    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        afficherDetails();
    }

    private void afficherDetails() {
        if (utilisateur != null) {
            nomField.setText(utilisateur.getNom());
            prenomField.setText(utilisateur.getPrenom());
            emailField.setText(utilisateur.getEmail());
            specialiteField.setText(utilisateur.getSpecialite());
            qualificationField.setText(utilisateur.getQualification());
        }
    }

    @FXML
    private void modifierMedecin(ActionEvent event) {
        if (utilisateur != null) {
            utilisateur.setNom(nomField.getText());
            utilisateur.setPrenom(prenomField.getText());
            utilisateur.setEmail(emailField.getText());
            utilisateur.setSpecialite(specialiteField.getText());
            utilisateur.setQualification(qualificationField.getText());

            // Update utilisateur
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO(DatabaseConnection.getConnection());
            utilisateurDAO.updateUtilisateur(utilisateur);

            // Update specialisation table specialiste
            SpecialisteDAO specialisteDAO = new SpecialisteDAO(DatabaseConnection.getConnection());
            specialisteDAO.updateSpecialisation(utilisateur.getId(), utilisateur.getSpecialite());
            specialisteDAO.updateQualification(utilisateur.getId(), utilisateur.getQualification());
            specialisteDAO.updateNom(utilisateur.getId(),utilisateur.getNom());
            specialisteDAO.updatePrenom(utilisateur.getId(),utilisateur.getPrenom());

            System.out.println("Médecin modifié avec succès !");
        }
    }
}
