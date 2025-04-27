package model;

/**
 * Classe représentant un spécialiste de l'application DoctoApp.
 */
public class Specialiste {
    private int idSpecialiste;
    private String nom;
    private String prenom;
    private String specialite;
    private String lieu;

    public Specialiste() {
    }

    public Specialiste(int idSpecialiste, String nom, String prenom, String specialite, String lieu) {
        this.idSpecialiste = idSpecialiste;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.lieu = lieu;
    }

    public int getIdSpecialiste() {
        return idSpecialiste;
    }

    public void setIdSpecialiste(int idSpecialiste) {
        this.idSpecialiste = idSpecialiste;
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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
}
