package Model;

public class Specialiste {
    private int id;
    private String specialisation;
    private String qualification;
    private String nom;
    private String prenom;

    public Specialiste() {
        // Constructeur vide nécessaire pour créer un objet sans paramètres
    }

    public Specialiste(int id, String specialisation, String qualification, String nom, String prenom) {
        this.id = id;
        this.specialisation = specialisation;
        this.qualification = qualification;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() { return id; }
    public String getSpecialisation() { return specialisation; }
    public String getQualification() { return qualification; }
    public String getNomComplet() { return prenom + " " + nom; }
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }

    public void setId(int id) { this.id = id; }
    public void setSpecialisation(String specialisation) { this.specialisation = specialisation; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
}
