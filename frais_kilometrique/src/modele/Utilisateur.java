package modele;

import org.apache.taglibs.standard.tei.Util;

import static modele.Fonction.ADMIN;

public class Utilisateur {
    //-------------------- A T T R I B U T S   D ' I N S T A N C E ---------------------------------
    private int id_utilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private Fonction fonction;
    private String adresse;

    //--------------------------- G E T T E R   S E T T E R ----------------------------------------
    public int getId_utilisateur() {
        return id_utilisateur;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    //------------------------- C O N S T R U C T E U R -----------------------------------------

    public Utilisateur(int id_utilisateur, String nom, String prenom,
                       String email, String mdp, Fonction fonction, String adresse) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.fonction = fonction;
        this.adresse = adresse;
    }

    public Utilisateur(int id_utilisateur, String nom, String prenom, String email, String mdp, Fonction fonction) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.fonction = fonction;
    }

    //------------------------------- T O S T R I N G -------------------------------------------

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id_utilisateur=" + id_utilisateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", fonction=" + fonction +
                ", adresse='" + (adresse==null?"pas d'adresse":adresse) + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Utilisateur u = new Utilisateur(1,"LEFEBVRE", "MICHAEL", "test@test.fr",
                "azerty", ADMIN,null);
        System.out.println(u);
    }
}
