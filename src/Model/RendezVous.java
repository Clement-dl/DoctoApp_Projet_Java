package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVous {
    private int idRdv;
    private int idPatient;
    private int idSpecialiste;
    private LocalDate date;
    private LocalTime heure;
    private String statut;

    // Constructeur sans ID pour insertion
    public RendezVous(int idRDV, int idPatient, int idSpecialiste, LocalDate date, LocalTime heure, String statut) {
        this.idRdv = idRDV;
        this.idPatient = idPatient;
        this.idSpecialiste = idSpecialiste;
        this.date = date;
        this.heure = heure;
        this.statut = statut;
    }

    // Getter simples
    public int getId() { return idRdv; }
    public int getIdPatient() { return idPatient; }
    public int getIdSpecialiste() { return idSpecialiste; }
    public LocalDate getDate() { return date; }
    public LocalTime getHeure() { return heure; }
    public String getStatut() { return statut; }

}
