package Controller;

import DAO.DatabaseConnection;
import Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import Model.Utilisateur;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dashboard {
    @FXML
    private ImageView pieChartView;   // pour afficher le camembert
    @FXML
    private ImageView barChartView;

    @FXML
    private ImageView specialityChartView;


    @FXML
    public void initialize() {
        createPieChart();
        createBarChart();
        createSpecialityChart();

    }

    private void createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
            Connection conn = DatabaseConnection.getConnection(); // ta classe pour la connexion
            String sql = "SELECT statut, COUNT(*) as total FROM rendezvous GROUP BY statut";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String statut = rs.getString("statut");
                int total = rs.getInt("total");
                dataset.setValue(statut, total);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Répartition des RDV",
                dataset,
                true, true, false
        );

        setChartToImageView(chart, pieChartView);
    }

    private void createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT MONTHNAME(Date) as mois, COUNT(*) as total FROM rendezvous GROUP BY mois ORDER BY MONTH(Date)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String mois = rs.getString("mois");
                int total = rs.getInt("total");
                dataset.addValue(total, "RDV", mois);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "RDV par mois",
                "Mois",
                "Nombre de RDV",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        setChartToImageView(chart, barChartView);
    }

    private void createSpecialityChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT s.specialisation, COUNT(*) as total FROM rendezvous r JOIN specialiste s ON r.id_specialiste = s.id_specialiste GROUP BY s.specialisation";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String specialisation = rs.getString("specialisation");
                int total = rs.getInt("total");
                dataset.setValue(specialisation, total);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Répartition RDV par Spécialité",
                dataset,
                true, true, false
        );

        setChartToImageView(chart, specialityChartView);
    }


    private void setChartToImageView(JFreeChart chart, ImageView imageView) {
        BufferedImage bufferedImage = chart.createBufferedImage(400, 300);
        WritableImage fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
        imageView.setImage(fxImage);
    }

    // (et tu laisses tes méthodes de navigation ici...)
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
