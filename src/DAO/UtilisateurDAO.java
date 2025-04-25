package DAO;

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
}
