package Controller;

import DAO.DatabaseConnection;
import DAO.UtilisateurDAO;
import Model.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CompteAdmin {

    @FXML
    private TableView<Utilisateur> tableUtilisateurs;
    @FXML
    private TableColumn<Utilisateur, Integer> colId;
    @FXML
    private TableColumn<Utilisateur, String> colNom;
    @FXML
    private TableColumn<Utilisateur, String> colPrenom;
    @FXML
    private TableColumn<Utilisateur, String> colEmail;
    @FXML
    private TableColumn<Utilisateur, String> colType;
    @FXML
    private TableColumn<Utilisateur, String> colSpecialite;

    private UtilisateurDAO utilisateurDAO;

    @FXML
    public void initialize() {
        utilisateurDAO = new UtilisateurDAO(DatabaseConnection.getConnection());
        chargerUtilisateurs();
    }

    private void chargerUtilisateurs() {
        try {
            List<Utilisateur> utilisateurs = utilisateurDAO.getAllUtilisateurs();
            ObservableList<Utilisateur> liste = FXCollections.observableArrayList(utilisateurs);

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colSpecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));

            // Largeur des colonnes
            colNom.setPrefWidth(80);
            colPrenom.setPrefWidth(80);
            colEmail.setPrefWidth(150);
            colType.setPrefWidth(100);

            tableUtilisateurs.setItems(liste);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerUtilisateur(ActionEvent event) {
        Utilisateur utilisateur = tableUtilisateurs.getSelectionModel().getSelectedItem();
        if (utilisateur != null) {
            utilisateurDAO.supprimerUtilisateur(utilisateur.getId());
            chargerUtilisateurs();
        }
    }

    @FXML
    private void goToAccueilAdmin(ActionEvent event) {
        chargerScene(event, "/View/AccueilAdmin.fxml", "Accueil Admin");
    }

    @FXML
    private void logout(ActionEvent event) {
        Connexion.logout();
        chargerScene(event, "/View/ConnecterVous.fxml", "Connexion");
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        chargerScene(event, "/View/Dashboard.fxml", "Dashboard");
    }

    @FXML
    private void goToTopMedecinAdmin(ActionEvent event) {
        chargerScene(event, "/View/TopMedecinAdmin.fxml", "Top Médecins Admin");
    }

    @FXML
    private void modifierMedecin(ActionEvent event) {
        Utilisateur utilisateur = tableUtilisateurs.getSelectionModel().getSelectedItem();

        if (utilisateur != null && "Specialiste".equalsIgnoreCase(utilisateur.getType())) {
            try {
                File fxml = new File("src/View/CompteAdminModifierMédecin.fxml");
                URL fxmlUrl = fxml.toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Parent root = loader.load();

                // Récupère le contrôleur associé au fichier FXML
                CompteAdminModifierMedecin controller = loader.getController();
                controller.setUtilisateur(utilisateur); // Transmet l'utilisateur au contrôleur

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Modifier Médecin");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void modifierPatient(ActionEvent event) {
        Utilisateur utilisateur = tableUtilisateurs.getSelectionModel().getSelectedItem();

        if (utilisateur != null && "Patient".equalsIgnoreCase(utilisateur.getType())) {
            try {
                File fxml = new File("src/View/CompteAdminModifierClient.fxml");
                URL fxmlUrl = fxml.toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Parent root = loader.load();

                // Récupère le contrôleur associé au fichier FXML
                CompteAdminModifierClient controller = loader.getController();
                controller.setUtilisateur(utilisateur);
                // Transmet l'utilisateur au contrôleur

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Modifier Médecin");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void ajouterMedecin(ActionEvent event) {
        try {
            File fxml = new File("src/View/CompteAdminAjouterMedecin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter Médecin");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gererRDV(ActionEvent event) {
        try {
            File fxml = new File("src/View/CompteAdminGererRDV.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gerer RDV");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargerScene(ActionEvent event, String cheminFXML, String titre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(titre);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
