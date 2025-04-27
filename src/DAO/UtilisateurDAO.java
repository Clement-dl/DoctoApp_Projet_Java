package DAO;

import Model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    private Connection conn;

    public UtilisateurDAO(Connection conn) {
        this.conn = conn;
    }


    public static String verifierUtilisateur(String email, String motDePasse) {
        String typeUtilisateur = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rdvspe", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT type_utilisateur FROM utilisateur WHERE email = ? AND mot_de_passe = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, motDePasse);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                typeUtilisateur = rs.getString("type_utilisateur");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typeUtilisateur;
    }

    public static Utilisateur getUtilisateurByEmailAndPassword(String email, String motDePasse) {
        Utilisateur utilisateur = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rdvspe", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, motDePasse);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("type_utilisateur")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }


    public static String getNomPrenomById(int idUtilisateur) {
        String nomPrenom = "Inconnu";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rdvspe", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT nom, prenom FROM utilisateur WHERE id_utilisateur = ?")) {

            stmt.setInt(1, idUtilisateur);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nomPrenom = rs.getString("nom") + " " + rs.getString("prenom");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nomPrenom;
    }


    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();

        String query = "SELECT * FROM utilisateur";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("type_utilisateur")
                );

                if ("Specialiste".equals(utilisateur.getType())) {
                    utilisateur.setSpecialite(getSpecialisationById(utilisateur.getId()));
                } else {
                    utilisateur.setSpecialite("-"); // Sinon pas de spécialité
                }

                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

    private String getSpecialisationById(int idUtilisateur) {
        String specialite = "-";
        String query = "SELECT specialisation FROM specialiste WHERE id_specialiste = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUtilisateur);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                specialite = rs.getString("specialisation");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialite;
    }


    public static void supprimerUtilisateur(int idUtilisateur) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rdvspe", "root", "");
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM utilisateur WHERE id_utilisateur = ?")) {

            stmt.setInt(1, idUtilisateur);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ? WHERE id_utilisateur = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getEmail());
            stmt.setInt(4, utilisateur.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
