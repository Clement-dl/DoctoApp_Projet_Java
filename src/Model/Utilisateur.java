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

    public String getType() {
        return type;
    }

    // Getters et setters supplémentaires ici si besoin
}
