package modele;

import java.io.Serializable;
import java.sql.Date;

public class Trajet implements Serializable {
    //------------------------------ V A R I A B L E   D ' I N S T A N C E ---------------------------------------------
    private int idTrajet;
    private int idUtilisateur;
    private Date dateTrajet;
    private String route;
    private int idAdresseDepart;
    private int idAdresseArrivee;
    private int distance;
    private boolean archive;

    //--------------------------------------- G E T T E R   S E T T E R ------------------------------------------------
    public int getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(int idTrajet) {
        this.idTrajet = idTrajet;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Date getDateTrajet() {
        return dateTrajet;
    }

    public void setDateTrajet(Date dateTrajet) {
        this.dateTrajet = dateTrajet;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getIdAdresseDepart() {
        return idAdresseDepart;
    }

    public void setIdAdresseDepart(int idAdresseDepart) {
        this.idAdresseDepart = idAdresseDepart;
    }

    public int getIdAdresseArrivee() {
        return idAdresseArrivee;
    }

    public void setIdAdresseArrivee(int idAdresseArrivee) {
        this.idAdresseArrivee = idAdresseArrivee;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    //------------------------------------------ C O N S T R U C T E U R -----------------------------------------------
    public Trajet(int idTrajet, int idUtilisateur, Date dateTrajet, String route, int idAdresseDepart, int idAdresseArrivee, int distance, boolean archive) {
        this.idTrajet = idTrajet;
        this.idUtilisateur = idUtilisateur;
        this.dateTrajet = dateTrajet;
        this.route = route;
        this.idAdresseDepart = idAdresseDepart;
        this.idAdresseArrivee = idAdresseArrivee;
        this.distance = distance;
        this.archive = archive;
    }

    public Trajet() {
    }

    //----------------------------------------- T O S T R I N G --------------------------------------------------------

    @Override
    public String toString() {
        return "Trajet{" +
                "idTrajet=" + idTrajet +
                ", idUtilisateur=" + idUtilisateur +
                ", dateTrajet=" + dateTrajet +
                ", route='" + route + '\'' +
                ", idAdresseDepart=" + idAdresseDepart +
                ", idAdresseArrivee=" + idAdresseArrivee +
                ", distance=" + distance +
                ", archive=" + archive +
                '}';
    }
}
