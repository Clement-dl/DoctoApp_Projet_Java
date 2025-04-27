package Controller;

import DAO.DatabaseConnection;
import DAO.UtilisateurDAO;
import Model.Patient;
import Model.Session;
import DAO.RDVDAO;
import DAO.PatientDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

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
    @FXML private VBox rdvActuelsVBox;
    @FXML private VBox historiqueVBox;
    @FXML private VBox paiementVBox;

    private Connection conn; //  connexion à toute la classe
    private RDVDAO rdvdao;

    public void initialize() {
        if (Session.estConnecte()) {
            conn = DatabaseConnection.getConnection();
            rdvdao = new RDVDAO(conn);

            int utilisateurId = Session.getUtilisateurId();
            Patient patient = PatientDAO.getPatientById(utilisateurId);

            if (patient != null) {
                // Remplir infos patient
                nomPrenomLabel.setText(patient.getNom() + " " + patient.getPrenom());
                nomUtilisateurLabel.setText(patient.getNomUtilisateur());
                adresseLabel.setText(patient.getAdresse());
                villeLabel.setText(patient.getVille());
                codePostalLabel.setText(patient.getCodePostal());
                telephoneLabel.setText(patient.getTelephone());
                carteVitaleLabel.setText(patient.getNumSecu() != null ? "Oui" : "Non");
                mdpLabel.setText("**********");

                // Charger les rendez-vous actuels et historiques
                chargerRendezVous(utilisateurId);
                chargerHistorique(utilisateurId);

            } else {
                System.out.println("Utilisateur non trouvé.");
            }
        } else {
            System.out.println("Utilisateur non connecté.");
        }
    }

    private void chargerRendezVous(int patientId) {
        var rdvs = rdvdao.getRendezVousActuelsByPatientId(patientId);

        for (var rdv : rdvs) {
            String medecinNom = UtilisateurDAO.getNomPrenomById(rdv.getIdSpecialiste());

            Label label = new Label(rdv.getDate() + " à " + rdv.getHeure() + "\navec Dr " + medecinNom);
            label.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

            VBox container = new VBox(label);
            container.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 10;");

            rdvActuelsVBox.getChildren().add(container);
        }
    }

    private void chargerHistorique(int patientId) {
        var rdvs = rdvdao.getRendezVousHistoriquesByPatientId(patientId);

        if (rdvs != null) {
            for (var rdv : rdvs) {
                String medecinNom = UtilisateurDAO.getNomPrenomById(rdv.getIdSpecialiste());

                Label label = new Label(rdv.getDate() + " à " + rdv.getHeure() + "\navec Dr " + medecinNom);
                label.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

                VBox container = new VBox(label);
                container.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 10;");

                historiqueVBox.getChildren().add(container);
            }
        }
    }


}
