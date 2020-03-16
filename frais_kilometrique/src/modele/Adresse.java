package modele;

import java.io.Serializable;

public class Adresse implements Serializable {

    //------------------------------- V A R I A B L E   D ' I N S T A N C E --------------------------------------------
    private int id;
    private int idUtilisateur;
    private int idTrajet;
    private String numero;
    private int codePostal;
    private String typeRue;
    private String nomRue;
    private String pays;
    private String ville;
    private String region;
    private String coordonnees;

    //--------------------------------- G E T T E R   S E T T E R ------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getTypeRue() {
        return typeRue;
    }

    public void setTypeRue(String typeRue) {
        this.typeRue = typeRue;
    }

    public String getNomRue() {
        return nomRue;
    }

    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(int idTrajet) {
        this.idTrajet = idTrajet;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(String coordonnees) {
        this.coordonnees = coordonnees;
    }

    //------------------------------------ C O N S T R U C T E U R -----------------------------------------------------

    public Adresse(String numero, int codePostal, String typeRue, String nomRue, String pays, String ville) {
        this.setNumero(numero);
        this.setCodePostal(codePostal);
        this.setTypeRue(typeRue);
        this.setNomRue(nomRue);
        this.setPays(pays);
        this.setVille(ville);
    }

    public Adresse(String numero, int codePostal, String typeRue, String nomRue, String pays, String ville, String coordonnees) {
        this.setNumero(numero);
        this.setCodePostal(codePostal);
        this.setTypeRue(typeRue);
        this.setNomRue(nomRue);
        this.setPays(pays);
        this.setVille(ville);
        this.setCoordonnees(coordonnees);
    }

    public Adresse(int id, int idUtilisateur, String numero, int codePostal,
                   String typeRue, String nomRue, String pays, String ville, String region) {
        this.setId(id);
        this.setIdUtilisateur(idUtilisateur);
        this.setNumero(numero);
        this.setCodePostal(codePostal);
        this.setTypeRue(typeRue);
        this.setNomRue(nomRue);
        this.setPays(pays);
        this.setVille(ville);
        this.setRegion(region);
    }

    public Adresse(int id, int idUtilisateur, String numero, int codePostal,
                   String typeRue, String nomRue, String pays, String ville, String region, String coordonnees) {
        this.setId(id);
        this.setIdUtilisateur(idUtilisateur);
        this.setNumero(numero);
        this.setCodePostal(codePostal);
        this.setTypeRue(typeRue);
        this.setNomRue(nomRue);
        this.setPays(pays);
        this.setVille(ville);
        this.setRegion(region);
        this.setCoordonnees(coordonnees);
    }


    public Adresse(int id, int idUtilisateur, int idTrajet, String numero, int codePostal,
                   String typeRue, String nomRue, String pays, String ville, String region, String coordonnees) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.idTrajet = idTrajet;
        this.numero = numero;
        this.codePostal = codePostal;
        this.typeRue = typeRue;
        this.nomRue = nomRue;
        this.pays = pays;
        this.ville = ville;
        this.region = region;
        this.coordonnees = coordonnees;
    }

    public Adresse() {
    }

    //----------------------------------------------- T O S T R I N G --------------------------------------------------

    @Override
    public String toString() {
        return "Adresse : " +
                " | id=" + id +
                " | idUtilisateur = " + idUtilisateur +
                " | idTrajet = " + idTrajet +
                " | numero = '" + numero + '\'' +
                " | codePostal = " + codePostal +
                " | typeRue = '" + typeRue + '\'' +
                " | nomRue = '" + nomRue + '\'' +
                " | pays = '" + pays + '\'' +
                " | ville='" + ville + '\'' +
                " | region = '" + region + '\'' +
                " | coordonnees = '" + coordonnees + '\'' +
                '|';
    }
}
