package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Specialiste;

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
}
