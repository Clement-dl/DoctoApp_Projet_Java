package DAO;

import Model.Patient;
import Model.Utilisateur;

import java.sql.*;



public class PatientDAO {

    private Connection conn;

    public static Patient getPatientById(int id) {
        Patient patient = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM patient WHERE id_patient = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();


            if (rs.next()) {
                String nomUtilisateur = rs.getString("nom_utilisateur");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String adresse = rs.getString("adresse");
                String ville = rs.getString("ville");
                String codePostal = rs.getString("code_postal");
                String telephone = rs.getString("telephone");
                String numSecu = rs.getString("numero_securite_sociale");
                String mdp = rs.getString("mot_de_passe");

                patient = new Patient(nomUtilisateur, nom, prenom, adresse, ville, codePostal, telephone, numSecu, mdp);
                patient.setId(id);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return patient;
    }


    public void ajouter(Patient patient) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtUtilisateur = null;
        PreparedStatement stmtPatient = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Début transaction

            //  Insertion dans utilisateur
            String sqlUtilisateur = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, type_utilisateur) VALUES (?, ?, ?, ?, ?)";
            stmtUtilisateur = conn.prepareStatement(sqlUtilisateur, Statement.RETURN_GENERATED_KEYS);

            stmtUtilisateur.setString(1, patient.getNom());
            stmtUtilisateur.setString(2, patient.getPrenom());
            stmtUtilisateur.setString(3, patient.getNomUtilisateur());
            stmtUtilisateur.setString(4, patient.getMdp());
            stmtUtilisateur.setString(5, "patient");

            stmtUtilisateur.executeUpdate();

            // Récupérer l'ID de l'utilisateur inséré
            rs = stmtUtilisateur.getGeneratedKeys();
            int idUtilisateur = 0;
            if (rs.next()) {
                idUtilisateur = rs.getInt(1); // ID généré pour l'utilisateur
            }

            //  Insertion dans patient
            String sqlPatient = "INSERT INTO patient (id_patient, nom_utilisateur, nom, prenom, adresse, ville, code_postal, telephone, numero_securite_sociale, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmtPatient = conn.prepareStatement(sqlPatient);

            stmtPatient.setInt(1, idUtilisateur);
            stmtPatient.setString(2, patient.getNomUtilisateur());
            stmtPatient.setString(3, patient.getNom());
            stmtPatient.setString(4, patient.getPrenom());
            stmtPatient.setString(5, patient.getAdresse());
            stmtPatient.setString(6, patient.getVille());
            stmtPatient.setString(7, patient.getCodePostal());
            stmtPatient.setString(8, patient.getTelephone());
            stmtPatient.setString(9, patient.getNumSecu());
            stmtPatient.setString(10, patient.getMdp());

            stmtPatient.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmtUtilisateur != null) stmtUtilisateur.close();
            if (stmtPatient != null) stmtPatient.close();
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public int getDernierUtilisateurId() {
        int id = -1;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT id_patient FROM patient ORDER BY id_patient DESC LIMIT 1";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id_patient");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id;
    }

    public static void updatePatient(Utilisateur utilisateur) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection(); // Ouvre une connexion ici
            String sql = "UPDATE patient SET telephone = ?, adresse = ?, ville = ?, code_postal = ?, numero_securite_sociale = ? WHERE id_patient = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, utilisateur.getTelephone());
            stmt.setString(2, utilisateur.getAdresse());
            stmt.setString(3, utilisateur.getVille());
            stmt.setString(4, utilisateur.getCode_postal());
            stmt.setString(5, utilisateur.getNumero_securite_sociale());
            stmt.setInt(6, utilisateur.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
