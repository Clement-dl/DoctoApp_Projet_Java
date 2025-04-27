package dao;

import model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface IPatientDAO pour accéder aux données des patients.
 */
public class PatientDAO implements IPatientDAO {

    @Override
    public void createPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (nom, prenom, email, motDePasse, statut) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getMotDePasse());
            stmt.setString(5, patient.getStatut());
            stmt.executeUpdate();
        }
    }

    @Override
    public Patient getPatientById(int id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE idPatient = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getInt("idPatient"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("motDePasse"),
                    rs.getString("statut")
                );
            }
        }
        return null;
    }

    @Override
    public Patient getPatientByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM patients WHERE email = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getInt("idPatient"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("motDePasse"),
                    rs.getString("statut")
                );
            }
        }
        return null;
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = ConnexionBDD.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new Patient(
                    rs.getInt("idPatient"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("motDePasse"),
                    rs.getString("statut")
                ));
            }
        }
        return patients;
    }

    @Override
    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET nom = ?, prenom = ?, email = ?, motDePasse = ?, statut = ? WHERE idPatient = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getMotDePasse());
            stmt.setString(5, patient.getStatut());
            stmt.setInt(6, patient.getIdPatient());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePatient(int id) throws SQLException {
        String sql = "DELETE FROM patients WHERE idPatient = ?";
        try (Connection conn = ConnexionBDD.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
