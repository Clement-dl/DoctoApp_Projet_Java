package Controller;

import DAO.PatientDAO;
import Model.Patient;
import Model.Session;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class CreerCompte {
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

    @FXML private TextField nomUtilisateurField;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField adresseField;
    @FXML private TextField villeField;
    @FXML private TextField codePostalField;
    @FXML private TextField telephoneField;
    @FXML private TextField numSecuField;
    @FXML private PasswordField mdpField;


    private final PatientDAO patientDAO = new PatientDAO();

    @FXML
    private void handleInscription(ActionEvent event) {
        try {
            String nomUtilisateur = nomUtilisateurField.getText();
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String adresse = adresseField.getText();
            String ville = villeField.getText();
            String codePostal = codePostalField.getText();
            String telephone = telephoneField.getText();
            String numSecu = numSecuField.getText();
            String mdp = mdpField.getText();

            Patient patient = new Patient(nomUtilisateur, nom, prenom, adresse, ville, codePostal, telephone, numSecu, mdp);
            patientDAO.ajouter(patient);
            // Après avoir ajouté le patient à la base de données
            int utilisateurId = patientDAO.getDernierUtilisateurId();  // Une méthode qui récupère l'ID de l'utilisateur ajouté
            Session.connecter(utilisateurId);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CompteClient.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
