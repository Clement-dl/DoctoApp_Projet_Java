package model;

import java.time.LocalDateTime;

/**
 * Classe représentant un rendez-vous dans l'application DoctoApp.
 */
public class RendezVous {
    private int idRendezVous;
    private int idPatient;
    private int idSpecialiste;
    private LocalDateTime dateHeure;
    private String note;

    public RendezVous() {
    }

    public RendezVous(int idRendezVous, int idPatient, int idSpecialiste, LocalDateTime dateHeure, String note) {
        this.idRendezVous = idRendezVous;
        this.idPatient = idPatient;
        this.idSpecialiste = idSpecialiste;
        this.dateHeure = dateHeure;
        this.note = note;
    }

    public int getIdRendezVous() {
        return idRendezVous;
    }

    public void setIdRendezVous(int idRendezVous) {
        this.idRendezVous = idRendezVous;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getIdSpecialiste() {
        return idSpecialiste;
    }

    public void setIdSpecialiste(int idSpecialiste) {
        this.idSpecialiste = idSpecialiste;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
