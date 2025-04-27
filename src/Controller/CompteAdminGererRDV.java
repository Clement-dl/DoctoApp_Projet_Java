package Controller;

import DAO.DatabaseConnection;
import DAO.RDVDAO;
import Model.RendezVous;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;



public class CompteAdminGererRDV implements Initializable {

    @FXML
    private TableView<RendezVous> tableViewRDV;
    @FXML
    private TableColumn<RendezVous, Integer> colId;
    @FXML
    private TableColumn<RendezVous, Integer> colIdPatient;
    @FXML
    private TableColumn<RendezVous, Integer> colIdSpecialiste;
    @FXML
    private TableColumn<RendezVous, String> colDate;
    @FXML
    private TableColumn<RendezVous, String> colHeure;
    @FXML
    private TableColumn<RendezVous, String> colStatut;

    private ObservableList<RendezVous> rdvList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdPatient.setCellValueFactory(new PropertyValueFactory<>("idPatient"));
        colIdSpecialiste.setCellValueFactory(new PropertyValueFactory<>("idSpecialiste"));
        colDate.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate().toString()));
        colHeure.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getHeure().toString()));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        chargerRendezVous();
        tableViewRDV.setItems(rdvList);
    }

    private void chargerRendezVous() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            RDVDAO rdvdao = new RDVDAO(conn);
            rdvList.addAll(rdvdao.getAllRendezVous());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



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
    private void terminerRDV(ActionEvent event) {
        RendezVous selectedRDV = tableViewRDV.getSelectionModel().getSelectedItem();
        if (selectedRDV != null) {
            try {
                Connection conn = DatabaseConnection.getConnection();
                RDVDAO rdvdao = new RDVDAO(conn);
                boolean success = rdvdao.supprimerRDV(selectedRDV.getId());

                if (success) {
                    rdvList.remove(selectedRDV);
                } else {
                    System.out.println("Erreur lors de la suppression en base de données.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
