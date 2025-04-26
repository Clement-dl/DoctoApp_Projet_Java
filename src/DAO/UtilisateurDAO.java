package DAO;

import Model.Utilisateur;
import java.sql.*;

public class UtilisateurDAO {

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

    // 🔥 NOUVELLE MÉTHODE
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
}
