package Model;

public class Session {
    private static int utilisateurId;
    private static boolean estConnecte = false;

    public static void connecter(int idUtilisateur) {
        utilisateurId = idUtilisateur;
        estConnecte = true;
    }

    public static void deconnecter() {
        utilisateurId = -1;
        estConnecte = false;
    }

    public static boolean estConnecte() {
        return estConnecte;
    }

    public static int getUtilisateurId() {
        return utilisateurId;
    }
}
