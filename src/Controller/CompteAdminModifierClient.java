package Controller;

import DAO.DatabaseConnection;
import DAO.PatientDAO;
import DAO.UtilisateurDAO;
import Model.Patient;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CompteAdminModifierClient {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField villeField;
    @FXML
    private TextField codePostalField;
    @FXML
    private TextField numeroTelephoneField;
    @FXML
    private TextField carteVitaleField;
    @FXML
    private TextField motDePasseField;

    private Utilisateur utilisateur;

    private Patient patient;

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        // Récupérer le patient depuis la base de données !
        this.patient = PatientDAO.getPatientById(utilisateur.getId());

        afficherDetails();
    }


    private void afficherDetails() {
        if (utilisateur != null) {
            nomField.setText(utilisateur.getNom());
            prenomField.setText(utilisateur.getPrenom());
            adresseField.setText(patient.getAdresse());
            villeField.setText(patient.getVille());
            codePostalField.setText(patient.getCodePostal());
            numeroTelephoneField.setText(patient.getTelephone());
            carteVitaleField.setText(patient.getNumSecu());
            motDePasseField.setText(utilisateur.getMotDePasse());
        }
    }

    @FXML
    private void modifierClient(ActionEvent event) {
        if (utilisateur != null) {
            utilisateur.setNom(nomField.getText());
            utilisateur.setPrenom(prenomField.getText());
            utilisateur.setAdresse(adresseField.getText());
            utilisateur.setVille(villeField.getText());
            utilisateur.setCode_postal(codePostalField.getText());
            utilisateur.setTelephone(numeroTelephoneField.getText());
            utilisateur.setNumero_securite_sociale(carteVitaleField.getText());
            utilisateur.setMotDePasse(motDePasseField.getText());

            UtilisateurDAO utilisateurDAO = new UtilisateurDAO(DatabaseConnection.getConnection());
            utilisateurDAO.updateUtilisateur(utilisateur);

            PatientDAO.updatePatient(utilisateur);


            System.out.println("Client modifié avec succès !");
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

    @FXML
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
