package Model;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String type;

    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.type = type;
    }

    public Utilisateur() {
        // Constructeur vide pour pouvoir créer un Utilisateur sans passer d'arguments directement
    }


    public int getId() {
        return id;
    }


    public String getType() {
        return type;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    private String specialite;

    public String getSpecialite() {
        return specialite;
    }

    private String telephone;

    public String getTelephone() {
        return telephone;
    }

    private String adresse;

    public String getAdresse() {
        return adresse;
    }

    private String ville;

    public String getVille() {
        return ville;
    }

    private String code_postal;

    public String getCode_postal() {
        return code_postal;
    }

    private String numero_securite_sociale;

    public String getNumero_securite_sociale() {
        return numero_securite_sociale;
    }

    private String qualification;

    public String getQualification() {
        return qualification;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setAdresse(String adresse) { this.adresse = adresse; }

    public void setNumero_securite_sociale(String numero_securite_sociale) { this.numero_securite_sociale = numero_securite_sociale; }

    public void setCode_postal(String code_postal) { this.code_postal = code_postal; }

    public void setVille(String ville) { this.ville = ville; }

    public void setTelephone(String telephone) { this.telephone = telephone; }

    public void setType(String type) { this.type = type; }
}
