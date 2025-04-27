package Model;

/**
 * Classe représentant un patient de l'application DoctoApp.
 */
public class Patient {
    private int idPatient;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String statut; // Ancien ou Nouveau patient

    public Patient() {
    }

    public Patient(int idPatient, String nom, String prenom, String email, String motDePasse, String statut) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.statut = statut;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
