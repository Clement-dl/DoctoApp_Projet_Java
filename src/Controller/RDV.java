package Controller;

import DAO.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import Model.Specialiste;
import javafx.fxml.FXML;
import Model.Utilisateur;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class RDV {

    @FXML
    private GridPane calendrierGrid;

    private Button selectedJourButton = null;
    private Button selectedHeureButton = null;
    private String selectedJour = null;
    private String selectedHeure = null;

    private Specialiste specialiste;
    private Utilisateur utilisateur;

    private final Map<Button, String[]> boutonInfos = new HashMap<>();
    private List<LocalDateTime> rdvsExistants = new ArrayList<>();

    @FXML
    private Button lundiButton;
    @FXML
    private Button mardiButton;
    @FXML
    private Button mercrediButton;
    @FXML
    private Button jeudiButton;
    @FXML
    private Button vendrediButton;
    @FXML
    private Button samediButton;
    @FXML
    private Button dimancheButton;

    @FXML
    private Button h9Button;
    @FXML
    private Button h10Button;
    @FXML
    private Button h11Button;
    @FXML
    private Button h12Button;
    @FXML
    private Button h14Button;
    @FXML
    private Button h15Button;
    @FXML
    private Button h16Button;

    @FXML
    public void initialize() {
        setupDates();



        boutonInfos.put(lundiButton, new String[]{"Lundi", null});
        boutonInfos.put(mardiButton, new String[]{"Mardi", null});
        boutonInfos.put(mercrediButton, new String[]{"Mercredi", null});
        boutonInfos.put(jeudiButton, new String[]{"Jeudi", null});
        boutonInfos.put(vendrediButton, new String[]{"Vendredi", null});
        boutonInfos.put(samediButton, new String[]{"Samedi", null});
        boutonInfos.put(dimancheButton, new String[]{"Dimanche", null});

        boutonInfos.put(h9Button, new String[]{null, "09:00"});
        boutonInfos.put(h10Button, new String[]{null, "10:00"});
        boutonInfos.put(h11Button, new String[]{null, "11:00"});
        boutonInfos.put(h12Button, new String[]{null, "12:00"});
        boutonInfos.put(h14Button, new String[]{null, "14:00"});
        boutonInfos.put(h15Button, new String[]{null, "15:00"});
        boutonInfos.put(h16Button, new String[]{null, "16:00"});

        lundiButton.setOnAction(e -> handleSlotSelection(lundiButton));
        mardiButton.setOnAction(e -> handleSlotSelection(mardiButton));
        mercrediButton.setOnAction(e -> handleSlotSelection(mercrediButton));
        jeudiButton.setOnAction(e -> handleSlotSelection(jeudiButton));
        vendrediButton.setOnAction(e -> handleSlotSelection(vendrediButton));
        samediButton.setOnAction(e -> handleSlotSelection(samediButton));
        dimancheButton.setOnAction(e -> handleSlotSelection(dimancheButton));

        h9Button.setOnAction(e -> handleSlotSelection(h9Button));
        h10Button.setOnAction(e -> handleSlotSelection(h10Button));
        h11Button.setOnAction(e -> handleSlotSelection(h11Button));
        h12Button.setOnAction(e -> handleSlotSelection(h12Button));
        h14Button.setOnAction(e -> handleSlotSelection(h14Button));
        h15Button.setOnAction(e -> handleSlotSelection(h15Button));
        h16Button.setOnAction(e -> handleSlotSelection(h16Button));

        for (Node node : calendrierGrid.getChildren()) {
            if (node instanceof Button button && !boutonInfos.containsKey(button)) {
                Integer colIndex = GridPane.getColumnIndex(button);
                Integer rowIndex = GridPane.getRowIndex(button);
                if (colIndex != null && rowIndex != null && rowIndex != 0) {
                    String jour = switch (colIndex) {
                        case 0 -> "Lundi";
                        case 1 -> "Mardi";
                        case 2 -> "Mercredi";
                        case 3 -> "Jeudi";
                        case 4 -> "Vendredi";
                        case 5 -> "Samedi";
                        case 6 -> "Dimanche";
                        default -> "";
                    };
                    String heure = switch (rowIndex) {
                        case 2 -> "09:00";
                        case 3 -> "10:00";
                        case 4 -> "11:00";
                        case 5 -> "14:00";
                        case 6 -> "15:00";
                        case 7 -> "16:00";
                        default -> "";
                    };
                    boutonInfos.put(button, new String[]{jour, heure});
                    button.setOnAction(e -> handleSlotSelection(button));
                }
            }
        }
    }

    private void setupDates() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        lundiButton.setText("Lundi\n" + currentMonday.format(formatter));
        mardiButton.setText("Mardi\n" + currentMonday.plusDays(1).format(formatter));
        mercrediButton.setText("Mercredi\n" + currentMonday.plusDays(2).format(formatter));
        jeudiButton.setText("Jeudi\n" + currentMonday.plusDays(3).format(formatter));
        vendrediButton.setText("Vendredi\n" + currentMonday.plusDays(4).format(formatter));
        samediButton.setText("Samedi\n" + currentMonday.plusDays(5).format(formatter));
        dimancheButton.setText("Dimanche\n" + currentMonday.plusDays(6).format(formatter));
    }


    private void handleSlotSelection(Button button) {
        String[] infos = boutonInfos.get(button);
        if (infos == null) return;

        String jour = infos[0];
        String heure = infos[1];

        if (jour != null) {
            if (selectedJourButton != null) {
                selectedJourButton.setStyle("-fx-background-radius: 10; -fx-background-color: #c6dce0; -fx-text-fill: black;");
            }
            selectedJourButton = button;
            selectedJourButton.setStyle("-fx-background-radius: 10; -fx-background-color: #005d74;");
            selectedJour = jour;

            mettreAJourDisponibilites();
        } else if (heure != null) {
            if (selectedHeureButton != null) {
                selectedHeureButton.setStyle("-fx-background-radius: 10; -fx-background-color: #c6dce0; -fx-text-fill: black;");
            }
            selectedHeureButton = button;
            selectedHeureButton.setStyle("-fx-background-radius: 10; -fx-background-color: #005d74;");
            selectedHeure = heure;
        }
    }

    private void mettreAJourDisponibilites() {
        if (selectedJour == null) return;

        LocalDate monday = currentMonday;

        Map<String, Integer> joursMap = Map.of(
                "Lundi", 0,
                "Mardi", 1,
                "Mercredi", 2,
                "Jeudi", 3,
                "Vendredi", 4,
                "Samedi", 5,
                "Dimanche", 6
        );

        LocalDate selectedDate = monday.plusDays(joursMap.get(selectedJour));
        boolean toutesHeuresReservees = true;

        for (Button heureButton : List.of(h9Button, h10Button, h11Button, h12Button, h14Button, h15Button, h16Button)) {
            String[] infos = boutonInfos.get(heureButton);
            if (infos == null || infos[1] == null) continue;

            LocalTime heure = LocalTime.parse(infos[1]);
            LocalDateTime rdvDateTime = LocalDateTime.of(selectedDate, heure);

            boolean estReserve = rdvsExistants.stream()
                    .anyMatch(rdv -> rdv.toLocalDate().equals(rdvDateTime.toLocalDate())
                            && rdv.toLocalTime().equals(rdvDateTime.toLocalTime()));

            if (estReserve) {
                heureButton.setDisable(true);
                heureButton.setStyle("-fx-background-radius: 10; -fx-background-color: #ff6060; -fx-text-fill: white;");
                Tooltip.install(heureButton, new Tooltip("Créneau déjà réservé"));
            } else {
                heureButton.setDisable(false);
                heureButton.setStyle("-fx-background-radius: 10; -fx-background-color: #c6dce0; -fx-text-fill: black;");
                Tooltip.uninstall(heureButton, null);
                toutesHeuresReservees = false;
            }
        }

        if (toutesHeuresReservees) {
            if (selectedJourButton != null) {
                selectedJourButton.setDisable(true);
                selectedJourButton.setStyle("-fx-background-radius: 10; -fx-background-color: #ff6060; -fx-text-fill: white;");
                Tooltip.install(selectedJourButton, new Tooltip("Aucun créneau disponible ce jour-là"));
            }
        } else {
            if (selectedJourButton != null) {
                selectedJourButton.setDisable(false);
                selectedJourButton.setStyle("-fx-background-radius: 10; -fx-background-color: #005d74; -fx-text-fill: white;");
                Tooltip.uninstall(selectedJourButton, null);
            }
        }
    }

    @FXML
    private Label nomLabel;
    @FXML
    private Label specialiteLabel;
    @FXML
    private TextArea informationsTextArea;

    public void setSpecialiste(Specialiste sp) {
        this.specialiste = sp;
        if (specialiste != null) {
            nomLabel.setText(specialiste.getNom() + " " + specialiste.getPrenom());
            specialiteLabel.setText(specialiste.getSpecialisation());
            informationsTextArea.setText(specialiste.getQualification());
            chargerRDVsSpecialiste();
        }
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    private void chargerRDVsSpecialiste() {
        rdvsExistants.clear();

        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT Date, Heure FROM rendezvous WHERE id_specialiste = ? AND statut = 'En attente'";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, specialiste.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDate date = rs.getDate("Date").toLocalDate();
                LocalTime heure = rs.getTime("Heure").toLocalTime();
                rdvsExistants.add(LocalDateTime.of(date, heure));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button leftArrowButton;
    @FXML
    private Button rightArrowButton;

    private LocalDate currentMonday = LocalDate.now().with(DayOfWeek.MONDAY);

    @FXML
    private void previousWeek() {
        currentMonday = currentMonday.minusWeeks(1);
        updateCalendar();
    }

    @FXML
    private void nextWeek() {
        currentMonday = currentMonday.plusWeeks(1);
        updateCalendar();
    }

    private void updateCalendar() {
        setupDates();
        if (selectedJourButton != null) {
            selectedJourButton.setStyle("-fx-background-radius: 10; -fx-background-color: #c6dce0; -fx-text-fill: black;");
            selectedJourButton = null;
        }
        if (selectedHeureButton != null) {
            selectedHeureButton.setStyle("-fx-background-radius: 10; -fx-background-color: #c6dce0; -fx-text-fill: black;");
            selectedHeureButton = null;
        }
        selectedJour = null;
        selectedHeure = null;
    }


    public void goBack(ActionEvent event) {
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
            if (selectedJour == null || selectedHeure == null) {
                System.out.println("Veuillez sélectionner un jour et une heure !");
                return;
            }

            File fxml = new File("src/View/Paiement.fxml");
            URL fxmlUrl = fxml.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Paiement paiementController = loader.getController();

            LocalDate monday = currentMonday;

            Map<String, Integer> joursMap = Map.of(
                    "Lundi", 0,
                    "Mardi", 1,
                    "Mercredi", 2,
                    "Jeudi", 3,
                    "Vendredi", 4,
                    "Samedi", 5,
                    "Dimanche", 6
            );

            LocalDate selectedDate = monday.plusDays(joursMap.get(selectedJour));
            LocalTime heureRDV = LocalTime.parse(selectedHeure);

            paiementController.setRDVDetails(utilisateur.getId(), specialiste.getId(), selectedDate, heureRDV);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Paiement");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
