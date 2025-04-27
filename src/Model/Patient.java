package Model;

public class Patient {
    private int id;
    private String nomUtilisateur, adresse, ville, codePostal, telephone, numSecu, mdp, nom, prenom;

    // Constructeur vide
    public Patient() {}

    // Constructeur avec id
    public Patient(int id, String nomUtilisateur, String nom, String prenom, String adresse, String ville, String codePostal, String telephone, String numSecu, String mdp) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.telephone = telephone;
        this.numSecu = numSecu;
        this.mdp = mdp;
    }

    // Constructeur sans id
    public Patient(String nomUtilisateur, String nom, String prenom, String adresse, String ville, String codePostal, String telephone, String numSecu, String mdp) {
        this.nomUtilisateur = nomUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.telephone = telephone;
        this.numSecu = numSecu;
        this.mdp = mdp;
    }

    // Getters
    public int getId() { return id; }
    public String getNomUtilisateur() { return nomUtilisateur; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getAdresse() { return adresse; }
    public String getVille() { return ville; }
    public String getCodePostal() { return codePostal; }
    public String getTelephone() { return telephone; }
    public String getNumSecu() { return numSecu; }
    public String getMdp() { return mdp; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setVille(String ville) { this.ville = ville; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setNumSecu(String numSecu) { this.numSecu = numSecu; }
    public void setMdp(String mdp) { this.mdp = mdp; }
}
