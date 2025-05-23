package Controller;

import Model.Session;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import DAO.SpecialisteDAO;
import Model.Specialiste;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class Gastroenterologie {

    @FXML
    private VBox medecinListContainer;

    @FXML
    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rdvspe", "root", "");
            SpecialisteDAO dao = new SpecialisteDAO(conn);
            List<Specialiste> gastro = dao.getSpecialistesParSpecialite("Gastroentérologie");

            for (Specialiste sp : gastro) {
                HBox hbox = new HBox(20);
                hbox.setPrefHeight(128);
                hbox.setPrefWidth(843);
                hbox.setMinHeight(128);
                hbox.setMinWidth(843);
                hbox.setMaxHeight(128);
                hbox.setMaxWidth(848);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-padding: 10;");
                StackPane stackPane = new StackPane();
                stackPane.setPrefHeight(110);
                stackPane.setPrefWidth(110);
                stackPane.setMinHeight(115);
                stackPane.setStyle("-fx-border-color: #a9a9a9; -fx-border-radius: 50; -fx-background-radius: 50;");
                ImageView photo;

                if (sp.getImage() != null && !sp.getImage().isEmpty() && !sp.getImage().equals("default")) {
                    // Si une image personnalisée existe
                    File imageFile = new File("src/Images/" + sp.getImage());
                    if (imageFile.exists()) {
                        photo = new ImageView(new Image(imageFile.toURI().toString()));
                    } else {
                        // Si le fichier n'existe pas, utiliser l'image par défaut
                        photo = new ImageView(new Image(getClass().getResourceAsStream("/Images/compte.png")));
                    }
                } else {
                    // Sinon utiliser image par défaut
                    photo = new ImageView(new Image(getClass().getResourceAsStream("/Images/compte.png")));
                }
                photo.setFitHeight(64);
                photo.setFitWidth(64);
                stackPane.getChildren().add(photo);
                Label info = new Label(sp.getNomComplet() + "\n" + sp.getQualification());
                info.setAlignment(Pos.CENTER);
                info.setMinHeight(78);
                info.setMinWidth(430);
                info.setTranslateX(50);
                info.setStyle("-fx-background-color: #f8fddd; -fx-background-radius: 15; -fx-padding: 10 20;");
                Button rdvButton = new Button("Prendre rendez-vous");
                rdvButton.setMinHeight(40);
                rdvButton.setMinWidth(134);
                rdvButton.setTranslateX(100);
                rdvButton.setStyle("-fx-background-color: #ddf65a; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 5 10; -fx-cursor: hand;");
                rdvButton.setOnAction(e -> goToRDV(e, sp));
                hbox.getChildren().addAll(stackPane, info, rdvButton);
                medecinListContainer.getChildren().add(hbox);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRDV(ActionEvent event, Specialiste specialiste) {
        try {
            File fxml;
            FXMLLoader loader;

            if (Session.estConnecte()) {
                // Si l'utilisateur est connecté, aller vers RDV.fxml
                fxml = new File("src/View/RDV.fxml");
                URL fxmlUrl = fxml.toURI().toURL();
                loader = new FXMLLoader(fxmlUrl);
                Parent root = loader.load();

                RDV controller = loader.getController();
                controller.setSpecialiste(specialiste);
                controller.setUtilisateur(Connexion.getUtilisateurConnecte()); // à adapter si besoin

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("RDV");
                stage.setScene(new Scene(root));
                stage.show();

            } else {
                // Sinon, aller vers RDVPasConnecter.fxml
                fxml = new File("src/View/RDVPasConnecté.fxml");
                URL fxmlUrl = fxml.toURI().toURL();
                loader = new FXMLLoader(fxmlUrl);
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Connexion requise");
                stage.setScene(new Scene(root));
                stage.show();
            }

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
