package modele;

import java.io.Serializable;

public class Vehicule implements Serializable {
    //----------------------- VARIABLES D'INSTANCE --------------
    private int idVehicule;
    private String marque;
    private String modele;
    private int puissance;
    private String immat;
    private int idUtilisateur;


    //----------------------------GETTER SETTER -------------------
    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public String getImmat() {
        return immat;
    }

    public void setImmat(String immat) {
        this.immat = immat;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    //---------------------------CONSTRUCTEUR------------------
    public Vehicule(int idVehicule, String marque, String modele, int puissance, String immat, int idUtilisateur) {
        this.idVehicule = idVehicule;
        this.marque = marque;
        this.modele = modele;
        this.puissance = puissance;
        this.immat = immat;
        this.idUtilisateur = idUtilisateur;
    }

    public Vehicule() {
    }
}
