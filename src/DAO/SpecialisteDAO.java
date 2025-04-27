package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Specialiste;
import Model.Utilisateur;


public class SpecialisteDAO {
    private Connection conn;

    public SpecialisteDAO(Connection conn) {
        this.conn = conn;
    }
    public List<Specialiste> getSpecialistesParSpecialite(String specialite) {
        List<Specialiste> liste = new ArrayList<>();
        String query = "SELECT s.id_specialiste, s.specialisation, s.qualification, u.nom, u.prenom FROM specialiste s " +
                "JOIN utilisateur u ON s.id_specialiste = u.id_utilisateur WHERE s.specialisation = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, specialite);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Specialiste sp = new Specialiste(
                        rs.getInt("id_specialiste"),
                        rs.getString("specialisation"),
                        rs.getString("qualification"),
                        rs.getString("nom"),
                        rs.getString("prenom")
                );
                liste.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Specialiste> getSpecialistesParNom(String nom) throws SQLException {
        List<Specialiste> specialistes = new ArrayList<>();
        String sql = "SELECT * FROM specialiste WHERE nom LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nom + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Specialiste specialiste = new Specialiste();
                    specialiste.setNom(rs.getString("nom"));
                    specialiste.setPrenom(rs.getString("prenom"));
                    specialiste.setQualification(rs.getString("specialisation"));
                    specialistes.add(specialiste);
                }
            }
        }
        return specialistes;
    }

    public void updateQualification(int utilisateurId, String qualification) {
        String sql = "UPDATE specialiste SET qualification = ? WHERE id_specialiste = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, qualification);
            stmt.setInt(2, utilisateurId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNom(int utilisateurId, String nom) {
        String sql = "UPDATE specialiste SET nom = ? WHERE id_specialiste = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setInt(2, utilisateurId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePrenom(int utilisateurId, String prenom) {
        String sql = "UPDATE specialiste SET prenom = ? WHERE id_specialiste = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prenom);
            stmt.setInt(2, utilisateurId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSpecialisation(int idSpecialiste, String nouvelleSpecialisation) {
        String sql = "UPDATE specialiste SET specialisation = ? WHERE id_specialiste = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nouvelleSpecialisation);
            stmt.setInt(2, idSpecialiste);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterMedecin(Utilisateur medecin) {
        try {
            // Insertion dans la table utilisateur
            String sqlUtilisateur = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, type_utilisateur) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmtUtilisateur = conn.prepareStatement(sqlUtilisateur, Statement.RETURN_GENERATED_KEYS);
            pstmtUtilisateur.setString(1, medecin.getNom());
            pstmtUtilisateur.setString(2, medecin.getPrenom());
            pstmtUtilisateur.setString(3, medecin.getEmail());
            pstmtUtilisateur.setString(4, medecin.getMotDePasse());
            pstmtUtilisateur.setString(5, medecin.getType());
            pstmtUtilisateur.executeUpdate();

            ResultSet rs = pstmtUtilisateur.getGeneratedKeys();
            int utilisateurId = -1;
            if (rs.next()) {
                utilisateurId = rs.getInt(1);
            }

            // Ensuite insertion dans la table specialiste
            if (utilisateurId != -1) {
                String sqlSpecialiste = "INSERT INTO specialiste (id_specialiste, specialisation, qualification, nom, prenom) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmtSpecialiste = conn.prepareStatement(sqlSpecialiste);
                pstmtSpecialiste.setInt(1, utilisateurId);
                pstmtSpecialiste.setString(2, medecin.getSpecialite());
                pstmtSpecialiste.setString(3, medecin.getQualification());
                pstmtSpecialiste.setString(4, medecin.getNom());
                pstmtSpecialiste.setString(5, medecin.getPrenom());
                pstmtSpecialiste.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}


