package dao;

import model.RendezVous;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface IRendezVousDAO pour accéder aux données des rendez-vous.
 */
public class RendezVousDAO implements IRendezVousDAO {

    @Override
    public void createRendezVous(RendezVous rdv) throws SQLException {
        String sql = "INSERT INTO rendezvous (idPatient, idSpecialiste, dateHeure, note) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rdv.getIdPatient());
            stmt.setInt(2, rdv.getIdSpecialiste());
            stmt.setTimestamp(3, Timestamp.valueOf(rdv.getDateHeure()));
            stmt.setString(4, rdv.getNote());
            stmt.executeUpdate();
        }
    }

    @Override
    public RendezVous getRendezVousById(int id) throws SQLException {
        String sql = "SELECT * FROM rendezvous WHERE idRendezVous = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RendezVous(
                    rs.getInt("idRendezVous"),
                    rs.getInt("idPatient"),
                    rs.getInt("idSpecialiste"),
                    rs.getTimestamp("dateHeure").toLocalDateTime(),
                    rs.getString("note")
                );
            }
        }
        return null;
    }

    @Override
    public List<RendezVous> getRendezVousByPatientId(int patientId) throws SQLException {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String sql = "SELECT * FROM rendezvous WHERE idPatient = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rendezVousList.add(new RendezVous(
                    rs.getInt("idRendezVous"),
                    rs.getInt("idPatient"),
                    rs.getInt("idSpecialiste"),
                    rs.getTimestamp("dateHeure").toLocalDateTime(),
                    rs.getString("note")
                ));
            }
        }
        return rendezVousList;
    }

    @Override
    public List<RendezVous> getRendezVousBySpecialisteId(int specId) throws SQLException {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String sql = "SELECT * FROM rendezvous WHERE idSpecialiste = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, specId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rendezVousList.add(new RendezVous(
                    rs.getInt("idRendezVous"),
                    rs.getInt("idPatient"),
                    rs.getInt("idSpecialiste"),
                    rs.getTimestamp("dateHeure").toLocalDateTime(),
                    rs.getString("note")
                ));
            }
        }
        return rendezVousList;
    }

    @Override
    public List<RendezVous> getAllRendezVous() throws SQLException {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String sql = "SELECT * FROM rendezvous";
        try (Connection conn = ConnexionBDD.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rendezVousList.add(new RendezVous(
                    rs.getInt("idRendezVous"),
                    rs.getInt("idPatient"),
                    rs.getInt("idSpecialiste"),
                    rs.getTimestamp("dateHeure").toLocalDateTime(),
                    rs.getString("note")
                ));
            }
        }
        return rendezVousList;
    }

    @Override
    public void updateRendezVous(RendezVous rdv) throws SQLException {
        String sql = "UPDATE rendezvous SET idPatient = ?,idSpecialiste = ?, dateHeure = ?, note = ? WHERE idRendezVous = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rdv.getIdPatient());
            stmt.setInt(2, rdv.getIdSpecialiste());
            stmt.setTimestamp(3, Timestamp.valueOf(rdv.getDateHeure()));
            stmt.setString(4, rdv.getNote());
            stmt.setInt(5, rdv.getIdRendezVous());
            stmt.executeUpdate();
        }
    }
    @Override
    public void deleteRendezVous(int id) throws SQLException {
        String sql = "DELETE FROM rendezvous WHERE idRendezVous = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
