package Model;

public class Patient {
    private int id;
    private String nomUtilisateur, adresse, ville, codePostal, telephone, numSecu, mdp, nom, prenom;

    // Nouveau constructeur
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

    // Nouveaux getters
    public int getId() {return id;}
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getNomUtilisateur() { return nomUtilisateur; }
    public String getAdresse() { return adresse; }
    public String getVille() { return ville; }
    public String getCodePostal() { return codePostal; }
    public String getTelephone() { return telephone; }
    public String getNumSecu() { return numSecu; }
    public String getMdp() { return mdp; }

    public void setId(int id) {
        this.id = id;
    }

}
