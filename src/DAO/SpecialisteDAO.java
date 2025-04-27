package dao;

import model.Specialiste;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface ISpecialisteDAO pour accéder aux données des spécialistes.
 */
public class SpecialisteDAO implements ISpecialisteDAO {

    @Override
    public void createSpecialiste(Specialiste s) throws SQLException {
        String sql = "INSERT INTO specialistes (nom, prenom, specialite, lieu) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s.getNom());
            stmt.setString(2, s.getPrenom());
            stmt.setString(3, s.getSpecialite());
            stmt.setString(4, s.getLieu());
            stmt.executeUpdate();
        }
    }

    @Override
    public Specialiste getSpecialisteById(int id) throws SQLException {
        String sql = "SELECT * FROM specialistes WHERE idSpecialiste = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Specialiste(
                    rs.getInt("idSpecialiste"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("specialite"),
                    rs.getString("lieu")
                );
            }
        }
        return null;
    }

    @Override
    public List<Specialiste> getAllSpecialistes() throws SQLException {
        List<Specialiste> specialistes = new ArrayList<>();
        String sql = "SELECT * FROM specialistes";
        try (Connection conn = ConnexionBDD.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                specialistes.add(new Specialiste(
                    rs.getInt("idSpecialiste"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("specialite"),
                    rs.getString("lieu")
                ));
            }
        }
        return specialistes;
    }

    @Override
    public void updateSpecialiste(Specialiste s) throws SQLException {
        String sql = "UPDATE specialistes SET nom = ?, prenom = ?, specialite = ?, lieu = ? WHERE idSpecialiste = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s.getNom());
            stmt.setString(2, s.getPrenom());
            stmt.setString(3, s.getSpecialite());
            stmt.setString(4, s.getLieu());
            stmt.setInt(5, s.getIdSpecialiste());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteSpecialiste(int id) throws SQLException {
        String sql = "DELETE FROM specialistes WHERE idSpecialiste = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
