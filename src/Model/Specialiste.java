package Model;

public class Specialiste {
    private int id;
    private String specialisation;
    private String qualification;
    private String nom;
    private String prenom;

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
}
