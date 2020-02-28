package modele;

import org.w3c.dom.UserDataHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FraisDAO {

    //------------------------------------ R E Q U E S T   S Q L -------------------------------------
    private String SQLFindUtilisateurByEmailAndPw =
    "SELECT * FROM UTILISATEUR " +
            " WHERE email = ? AND mdp = ?";

    private String SQLInsertNewUtilisateur =
            "INSERT  INTO UTILISATEUR " +
                    " (nom, prenom, email, mdp, fonction) " +
                    " VALUES (?, ?, ?, ?, ?)";

    private static String URL = "jdbc:mysql://localhost:3306/frais_kilo";
    private static String USER = "michael";
    private static String PW = "mdppopmichael";


    private Connection connection = null;

    //-------------------------------------- S I N G L E T O N --------------------------------------
    private static FraisDAO fraisDao = null;

    public static FraisDAO getSingleton(){
        if (fraisDao == null){
            fraisDao = new FraisDAO();
        }
        return fraisDao;
    }

    //---------------------------- C O N S T R U C T E U R   P R I V E E ---------------------------
    private FraisDAO(){
        try {
            //class for name driver
            connection= DriverManager.getConnection(URL,USER, PW);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    //---------------------------------------- M E T H O D E -------------------------------------
    public Utilisateur findByEmailAndPw(String email, String password) throws Exception{
        if (email == null || password == null) return null;
        Utilisateur utilisateur = null;
        PreparedStatement selectStatement = null;
        ResultSet rs = null;

        try{
            selectStatement = connection.prepareStatement(SQLFindUtilisateurByEmailAndPw);
            selectStatement.setString(1,email.toLowerCase());
            selectStatement.setString(2,password);

            rs = selectStatement.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id_utilisateur");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String fonction = rs.getString("fonction");
                String adresse = (rs.getString("adresse") ==null ? "":rs.getString("adresse"));
                utilisateur = new Utilisateur(id, nom, prenom, email, password, fonction, adresse);
            }
        }finally {
            rs.close();
            selectStatement.close();
        }
        return utilisateur;
    }

    public Utilisateur insertUtilisateur(String nom, String prenom, String email, String password, String fonction) throws Exception{
        if (email == null || password == null || nom == null || prenom == null) return null;
        PreparedStatement insertStatement = null;
        ResultSet rs = null;
        connection.setAutoCommit(false);
        password= password.
        try{
            insertStatement = connection.prepareStatement(SQLFindUtilisateurByEmailAndPw);
            selectStatement.setString(1,email.toLowerCase());
            selectStatement.setString(2,password);

            rs = selectStatement.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id_utilisateur");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String fonction = rs.getString("fonction");
                String adresse = (rs.getString("adresse") ==null ? "":rs.getString("adresse"));
                utilisateur = new Utilisateur(id, nom, prenom, email, password, fonction, adresse);
            }
        }finally {
            rs.close();
            selectStatement.close();
        }
        return utilisateur;
    }



}
