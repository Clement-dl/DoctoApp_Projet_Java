package DAO;

import Model.RendezVous;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RDVDAO {
    private Connection conn;

    public RDVDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean enregistrerRDV(int idPatient, int idSpecialiste, java.time.LocalDate date, java.time.LocalTime heure) {
        String query = "INSERT INTO rendezvous (id_patient, id_specialiste, Date, Heure, statut, notes) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idPatient);
            stmt.setInt(2, idSpecialiste);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setTime(4, Time.valueOf(heure));
            stmt.setString(5, "En attente");
            stmt.setString(6, "");

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<RendezVous> getRendezVousActuelsByPatientId(int idPatient) {
        List<RendezVous> liste = new ArrayList<>();
        String query = "SELECT * FROM rendezvous WHERE id_patient = ? AND ((Date > ?) OR (Date = ? AND Heure > ?)) AND (statut = 'En attente' OR statut = 'Confirmé')";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idPatient);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setTime(4, Time.valueOf(LocalTime.now()));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RendezVous rdv = new RendezVous(
                        rs.getInt("id_rdv"),
                        rs.getInt("id_patient"),
                        rs.getInt("id_specialiste"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getTime("Heure").toLocalTime(),
                        rs.getString("statut")
                );
                liste.add(rdv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }


    public List<RendezVous> getRendezVousHistoriquesByPatientId(int idPatient) {
        List<RendezVous> liste = new ArrayList<>();
        String query = "SELECT * FROM rendezvous WHERE id_patient = ? AND (Date < ? OR statut = 'Terminé')";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idPatient);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RendezVous rdv = new RendezVous(
                        rs.getInt("id_rdv"),
                        rs.getInt("id_patient"),
                        rs.getInt("id_specialiste"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getTime("Heure").toLocalTime(),
                        rs.getString("statut")
                );
                liste.add(rdv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
