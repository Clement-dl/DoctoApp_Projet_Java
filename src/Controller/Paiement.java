package Controller;

import DAO.DatabaseConnection;
import DAO.RDVDAO;
import Model.Specialiste;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Paiement {

    // 1. Ajouter ces attributs tout en haut de ta classe Paiement
    private int idPatient;
    private int idSpecialiste;
    private LocalDate date;
    private java.time.LocalTime heure;

    // 2. Ajouter ce setter
    public void setRDVDetails(int idPatient, int idSpecialiste, LocalDate date, java.time.LocalTime heure) {
        this.idPatient = idPatient;
        this.idSpecialiste = idSpecialiste;
        this.date = date;
        this.heure = heure;
    }

    // 3. Méthode pour enregistrer le rendez-vous après paiement
    @FXML
    private void handlePayer(ActionEvent event) {
        // Vérification basique des champs de carte
        if (typeCarteField.getText().isEmpty() || numeroCarteField.getText().isEmpty() || nomCarteField.getText().isEmpty() ||
                expirationField.getText().isEmpty() || codeSecuriteField.getText().isEmpty()) {
            System.out.println("Veuillez remplir tous les champs de paiement !");
            return;
        }

        // Connexion à la base de données
        try (Connection conn = DatabaseConnection.getConnection()) {
            RDVDAO rdvDAO = new RDVDAO(conn);
            boolean success = rdvDAO.enregistrerRDV(idPatient, idSpecialiste, date, heure);

            if (success) {
                System.out.println("Rendez-vous enregistré avec succès !");
                goToCompteClient(event); // Rediriger vers CompteClient après paiement
            } else {
                System.out.println("Échec de l'enregistrement du rendez-vous !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private TextField typeCarteField;
    @FXML
    private TextField numeroCarteField;
    @FXML
    private TextField nomCarteField;
    @FXML
    private TextField expirationField;
    @FXML
    private TextField codeSecuriteField;
    @FXML
    private Button payerButton;

    @FXML
    public void initialize() {

    }



    private void goToCompteClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CompteClient.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
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

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Accueil");
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

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
