package Controller;

import DAO.SpecialisteDAO;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;

public class AccueilAdmin {
    @FXML
    private ImageView banniereImage;

    @FXML
    public void initialize() {
        Rectangle clip = new Rectangle(banniereImage.getFitWidth(), banniereImage.getFitHeight());
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        banniereImage.setClip(clip);
    }

    @FXML
    public void goToGeneralisteAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/GeneralisteAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("GénéralisteAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToCardiologieAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/CardiologieAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("CardiologieAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToDentisteAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/DentisteAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("DentisteAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToDermatologieAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/DermatologieAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("DermatologieAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToGastroenterologieAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/GastroenterologieAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("GastroenterologieAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToNeurologieAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/NeurologieAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("NeurologieAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToOrthopedieAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/OrthopedieAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("OrthopedieAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToRadiologieAdmin(ActionEvent event) {
        try {
            File fxml = new File("src/View/RadiologieAdmin.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("RadiologieAdmin");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToConnexionAdmin(ActionEvent event) {
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
