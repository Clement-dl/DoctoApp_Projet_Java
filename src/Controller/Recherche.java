package Controller;

import DAO.SpecialisteDAO;
import Model.Specialiste;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.ResourceBundle;

public class Recherche implements Initializable {

    @FXML
    private VBox medecinListContainer;


    private String rechercheSpecialite = "";
    private String rechercheNom = "";
    private Connection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rdvspe", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRechercheSpecialite(String specialite) {
        this.rechercheSpecialite = specialite;
        chargerResultatsParSpecialite();
    }

    public void setNomRecherche(String nom) {
        this.rechercheNom = nom;
        chargerResultatsParNom();
    }

    private void chargerResultatsParSpecialite() {
        if (conn == null) return;
        try {
            SpecialisteDAO dao = new SpecialisteDAO(conn);
            List<Specialiste> specialistes = dao.getSpecialistesParSpecialite(rechercheSpecialite);
            afficherSpecialistes(specialistes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerResultatsParNom() {
        if (conn == null) return;
        try {
            SpecialisteDAO dao = new SpecialisteDAO(conn);
            List<Specialiste> specialistes = dao.getSpecialistesParNom(rechercheNom);
            afficherSpecialistes(specialistes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void afficherSpecialistes(List<Specialiste> specialistes) {
        medecinListContainer.getChildren().clear();

        for (Specialiste sp : specialistes) {
            HBox hbox = new HBox(20);
            hbox.setPrefHeight(128);
            hbox.setPrefWidth(843);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-padding: 10;");

            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(110, 110);
            stackPane.setStyle("-fx-border-color: #a9a9a9; -fx-border-radius: 50; -fx-background-radius: 50;");
            ImageView photo = new ImageView(new Image(getClass().getResourceAsStream("/Images/compte.png")));
            photo.setFitHeight(48);
            photo.setFitWidth(48);
            stackPane.getChildren().add(photo);

            Label info = new Label(sp.getNomComplet() + "\n" + sp.getQualification());
            info.setAlignment(Pos.CENTER_LEFT);
            info.setMinHeight(78);
            info.setMinWidth(430);
            info.setTranslateX(50);
            info.setStyle("-fx-background-color: #d5e9e8; -fx-background-radius: 15; -fx-padding: 10 20;");

            Button rdvButton = new Button("Prendre rendez-vous");
            rdvButton.setMinHeight(40);
            rdvButton.setMinWidth(134);
            rdvButton.setTranslateX(100);
            rdvButton.setStyle("-fx-background-color: #33958f; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 5 10; -fx-cursor: hand;");
            rdvButton.setOnAction(e -> goToRDV(e, sp));

            hbox.getChildren().addAll(stackPane, info, rdvButton);
            medecinListContainer.getChildren().add(hbox);
        }
    }

    @FXML
    public void goToAccueil(ActionEvent event) {
        loadScene(event, "/View/Accueil.fxml", "Accueil");
    }

    public void goToPaiement(ActionEvent event) {
        loadScene(event, "/View/Paiement.fxml", "Paiement");
    }

    public void goToTopMedecin(ActionEvent event) {
        loadScene(event, "/View/TopMedecin.fxml", "TopMedecin");
    }

    @FXML
    void goToConnexion(ActionEvent event) {
        try {
            Utilisateur utilisateur = Connexion.getUtilisateurConnecte();
            String fxmlFile;
            if (utilisateur != null) {
                fxmlFile = switch (utilisateur.getType()) {
                    case "Patient" -> "/View/CompteClient.fxml";
                    case "Administrateur" -> "/View/CompteAdmin.fxml";
                    default -> "/View/ConnecterVous.fxml";
                };
            } else {
                fxmlFile = "/View/ConnecterVous.fxml";
            }
            loadScene(event, fxmlFile, "Connexion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRDV(ActionEvent event, Specialiste specialiste) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RDV.fxml"));
            Parent root = loader.load();

            RDV controller = loader.getController();
            controller.setSpecialiste(specialiste);
            controller.setUtilisateur(Connexion.getUtilisateurConnecte());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("RDV");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private javafx.scene.control.TextField searchBar;


    @FXML
    public void handleSearch(ActionEvent event) {
        String maRecherche = searchBar.getText().trim(); // Utiliser searchBar ici !

        if (!maRecherche.isEmpty()) {
            try {
                // Charger Recherche.fxml
                File fxml = new File("src/View/Recherche.fxml");
                URL fxmlUrl = fxml.toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Parent root = loader.load();

                // Récupérer le contrôleur de Recherche.fxml
                Recherche rechercheController = loader.getController();

                // DÉCIDER quoi faire : ici on tente d'abord par spécialité
                SpecialisteDAO dao = new SpecialisteDAO(DriverManager.getConnection("jdbc:mysql://localhost:3306/rdvspe", "root", ""));
                if (!dao.getSpecialistesParSpecialite(maRecherche).isEmpty()) {
                    rechercheController.setRechercheSpecialite(maRecherche);
                } else {
                    rechercheController.setNomRecherche(maRecherche);
                }

                // Changer de scène
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Champ vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une recherche.");
            alert.showAndWait();
        }
    }
}
