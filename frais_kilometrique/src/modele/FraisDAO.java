package modele;

import org.mindrot.jbcrypt.BCrypt;
import service.Routing;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;
import java.security.MessageDigest;
import java.sql.*;


public class FraisDAO implements Serializable {

    //------------------------------------ R E Q U E S T   S Q L -------------------------------------
    private String SQLFindUtilisateurByEmailAndPw =
            "SELECT * FROM UTILISATEUR " +
                    " WHERE email = ? AND mdp = ?";

    private String SQLFindUtilisateurByEmail =
            "SELECT * FROM UTILISATEUR " +
                    " WHERE email = ?";

    private String SQLInsertNewUtilisateur =
            "INSERT  INTO UTILISATEUR " +
                    " (nom, prenom, email, mdp) " +
                    " VALUES (?, ?, ?, ?)";

    private String SQLInsertNewAdresseFavorite =
            "INSERT  INTO ADRESSE " +
                    " (id_utilisateur, numero, type_rue, nom_rue, code_postal, ville, pays, coordonnees) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private String SQLInsertNewAdresseTrajet =
            "INSERT  INTO ADRESSE " +
                    " (numero, type_rue, nom_rue, code_postal, ville, pays, coordonnees) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private String SQLFindAdresseFavoriteByIdUtilisateur =
            "SELECT id_adresse, id_utilisateur, numero, type_rue, nom_rue, code_postal, ville, pays, coordonnees FROM ADRESSE " +
                    " WHERE id_utilisateur = ? " +
                    "ORDER BY ville ,nom_rue , type_rue, numero";

    private String SQLFindAdresse =
            "SELECT id_adresse, id_utilisateur, numero, type_rue, nom_rue, code_postal, ville, pays, coordonnees FROM ADRESSE " +
                    " WHERE  (coordonnees = ? AND (id_utilisateur=? or id_utilisateur IS NULL)) " +
                    " ORDER BY ville ,nom_rue , type_rue, numero";

    private String SQLInsertNewTrajet =
            "INSERT  INTO TRAJET " +
                    " (id_utilisateur, date_trajet, route, id_adresse_dep, id_adresse_arr, distance, archive) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private String SQLFindAllTrajetByIdUtilisateur =
            "SELECT * FROM TRAJET " +
                    " WHERE id_utilisateur = ? " +
                    "ORDER BY date_trajet";

    private String SQLFindTrajetNonArchiveByIdUtilisateur =
            "SELECT * FROM TRAJET " +
                    " WHERE id_utilisateur = ? AND archive=FALSE " +
                    " ORDER BY date_trajet";

    private String SQLFindTrajetArchiveByIdUtilisateur =
            "SELECT * FROM TRAJET " +
                    " WHERE id_utilisateur = ? AND archive=TRUE " +
                    " ORDER BY date_trajet";

    private String SQLArchiveTrajetById =
            "UPDATE TRAJET " +
                    " SET archive = TRUE " +
                    " WHERE id_trajet = ?";

    private String SQLDesarchiveTrajetById =
            "UPDATE TRAJET " +
                    " SET archive = False " +
                    " WHERE id_trajet = ?";

    private String SQLInsertNewVehicule =
            "INSERT  INTO VEHICULE " +
                    " (marque, modele, puissance, immat, id_utilisateur) " +
                    " VALUES (?, ?, ?, ?, ?)";

    private String SQLFindVehiculeByIdUtilisateur =
            "SELECT * FROM VEHICULE " +
                    " WHERE id_utilisateur = ? " +
                    " ORDER BY marque, modele, puissance";

    private String SQLUpdateVehicule =
            "UPDATE VEHICULE " +
                    " SET marque=?, modele=?, puissance=?, immat=? " +
                    " WHERE id_vehicule = ?";

    private String SQLDeleteVehicule =
            "DELETE FROM VEHICULE " +
                    " WHERE id_vehicule = ?";

    //CONNECTION BASE DE DONNEES
    private static String URL = "jdbc:mysql://localhost:3306/frais_kilo";
    private static String USER = "michael";
    private static String PW = "mdppopmichael";


    private Connection connection = null;

    //-------------------------------------- S I N G L E T O N --------------------------------------
    private static FraisDAO fraisDao = null;

    public static FraisDAO getSingleton() {
        if (fraisDao == null) {
            fraisDao = new FraisDAO();
        }
        return fraisDao;
    }

    //---------------------------- C O N S T R U C T E U R   P R I V E E ---------------------------
    private FraisDAO() {
        try {
            //class for name driver
            connection = DriverManager.getConnection(URL, USER, PW);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //---------------------------------------- M E T H O D E  U T I L I S A T E U R ------------------------------------
    public Utilisateur findByEmailAndPw(String email, String password) throws Exception {
        if (email == null || password == null) return null;
        Utilisateur utilisateur = null;
        PreparedStatement selectStatement = null;
        ResultSet rs = null;

        //codage du mot de passe en SHA 512
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        System.out.println(password);

        StringBuffer pass = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            pass.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println(pass.toString());

        //Recherche dans la base de données
        try {
            selectStatement = connection.prepareStatement(SQLFindUtilisateurByEmailAndPw);
            selectStatement.setString(1, email.toLowerCase());
            selectStatement.setString(2, String.valueOf(pass));

            rs = selectStatement.executeQuery();
            if (rs.next()) {
                System.out.println("ok");
                int id = rs.getInt("id_utilisateur");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String fonction = rs.getString("fonction");
                utilisateur = new Utilisateur(id, nom, prenom, email, "PASSWORD", fonction);
            }
        } finally {
            rs.close();
            selectStatement.close();
        }
        return utilisateur;
    }

    public Utilisateur findByEmail(String email) throws Exception {
        if (email == null) return null;
        Utilisateur utilisateur = null;
        PreparedStatement selectStatement = null;
        ResultSet rs = null;
        //mise en minuscule de l'adresse email pour le cryptage en sha
        email = email.toLowerCase();

        //codage du mot de passe en SHA 512
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(email.getBytes());

        byte byteData[] = md.digest();

        StringBuffer mail = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            mail.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }


        //Recherche dans la base de données
        try {
            selectStatement = connection.prepareStatement(SQLFindUtilisateurByEmail);
            selectStatement.setString(1, mail.toString());

            rs = selectStatement.executeQuery();
            if (rs.next()) {
                System.out.println("ok");
                int id = rs.getInt("id_utilisateur");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String fonction = rs.getString("fonction");
                String password = rs.getString("mdp");
                utilisateur = new Utilisateur(id, nom, prenom, email, password, fonction);
            }
        } finally {
            rs.close();
            selectStatement.close();
        }
        return utilisateur;
    }


    public int insertUtilisateur(String nom, String prenom, String email, String password) throws Exception {
        if (email == null || password == null || nom == null || prenom == null) return 0;
        PreparedStatement insertStatement = null;
        int rsu = 0;
        connection.setAutoCommit(false);

        //cryptage avec jbcrypt

        String pass = BCrypt.hashpw(password, BCrypt.gensalt(15));

        //mise en minuscule de l'email avant cryptage sha
        email = email.toLowerCase();

        //cryptage avec SHA-512
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(email.getBytes());

        byte byteData[] = md.digest();

        StringBuffer mail = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            mail.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        try {
            insertStatement = connection.prepareStatement(SQLInsertNewUtilisateur);
            insertStatement.setString(1, nom.toUpperCase());
            insertStatement.setString(2, prenom.toUpperCase());
            insertStatement.setString(3, mail.toString());
            insertStatement.setString(4, pass);

            rsu = insertStatement.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rsu;
    }

    //---------------------------------------- M E T H O D E  A D R E S S E --------------------------------------------
    public int insertAdresseFavorite(int id_utilisateur, String numero, String type_rue, String nom_rue, int code_postal,
                                     String ville, String pays) throws Exception {
        if (id_utilisateur == 0 || numero == null || type_rue == null || nom_rue == null || code_postal == 0 ||
                ville == null || pays == null) return 0;

        PreparedStatement insertStatement = null;
        int rsu = 0;
        connection.setAutoCommit(false);

        Routing routing = new Routing();
        String coordonnees = routing.findCoordoneesAdresse(numero, type_rue, nom_rue, code_postal, ville, pays);

        try {
            insertStatement = connection.prepareStatement(SQLInsertNewAdresseFavorite);
            insertStatement.setInt(1, id_utilisateur);
            insertStatement.setString(2, numero.toUpperCase());
            insertStatement.setString(3, type_rue.toUpperCase());
            insertStatement.setString(4, nom_rue.toUpperCase());
            insertStatement.setInt(5, code_postal);
            insertStatement.setString(6, ville.toUpperCase());
            insertStatement.setString(7, pays.toUpperCase());
            insertStatement.setString(8, coordonnees.toUpperCase());

            rsu = insertStatement.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rsu;
    }

    public int insertAdresseTrajet(String numero, String type_rue, String nom_rue, int code_postal,
                                     String ville, String pays) throws Exception {
        if (numero == null || type_rue == null || nom_rue == null || code_postal == 0 ||
                ville == null || pays == null) return 0;

        PreparedStatement insertStatement = null;
        int rsu = 0;
        connection.setAutoCommit(false);

        Routing routing = new Routing();
        String coordonnees = routing.findCoordoneesAdresse(numero, type_rue, nom_rue, code_postal, ville, pays);

        try {
            insertStatement = connection.prepareStatement(SQLInsertNewAdresseTrajet);
            insertStatement.setString(1, numero.toUpperCase());
            insertStatement.setString(2, type_rue.toUpperCase());
            insertStatement.setString(3, nom_rue.toUpperCase());
            insertStatement.setInt(4, code_postal);
            insertStatement.setString(5, ville.toUpperCase());
            insertStatement.setString(6, pays.toUpperCase());
            insertStatement.setString(7, coordonnees.toUpperCase());

            rsu = insertStatement.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rsu;
    }

    public Set<Adresse> findAdresseByIdUtilisateur(int idUtilisateur) throws Exception {
        if (idUtilisateur == 0) return null;
        Set<Adresse> adresses = new HashSet<>();
        PreparedStatement selectStatement = null;
        ResultSet rs = null;

        //Recherche dans la base de données
        try {
            selectStatement = connection.prepareStatement(SQLFindAdresseFavoriteByIdUtilisateur);
            selectStatement.setInt(1, idUtilisateur);

            rs = selectStatement.executeQuery();
            while (rs.next()) {
                int idAdresse = rs.getInt("id_adresse");
                int id_utilisateur = rs.getInt("id_utilisateur");
                String numero = rs.getString("numero");
                String nomRue = rs.getString("nom_rue");
                String typeRue = rs.getString("type_rue");
                int codePostal = rs.getInt("code_postal");
                String ville = rs.getString("ville");
                String pays = rs.getString("pays");
                String region = rs.getString("region");
                String coordonees = rs.getString("coordonnees");
                Adresse a = new Adresse(idAdresse, idUtilisateur, numero, codePostal, typeRue, nomRue, pays, ville, region, coordonees);
                adresses.add(a);
            }
        } finally {
            rs.close();
            selectStatement.close();
        }
        return adresses;
    }

    public Adresse findAdresseByIdUtilisateurBycoordonnees(String coordonnees, int idUtilisateur) throws Exception {
        if (coordonnees == null|| idUtilisateur==0 ) return null;
        Adresse a = null;
        PreparedStatement selectStatement = null;
        ResultSet rs = null;

        //Recherche dans la base de données
        try {
            selectStatement = connection.prepareStatement(SQLFindAdresse);
            selectStatement.setString(1, coordonnees);
            selectStatement.setInt(2, idUtilisateur);

            rs = selectStatement.executeQuery();
            if (rs.next()) {
                int idAdresse = rs.getInt("id_adresse");
                int id_utilisateur = rs.getInt("id_utilisateur");
                String numero = rs.getString("numero");
                String nomRue = rs.getString("nom_rue");
                String typeRue = rs.getString("type_rue");
                int codePostal = rs.getInt("code_postal");
                String ville = rs.getString("ville");
                String pays = rs.getString("pays");

                String coordonees = rs.getString("coordonnees");
                a = new Adresse(idAdresse, id_utilisateur, numero, codePostal, typeRue, nomRue, pays,
                        ville, coordonees);
            }
        } finally {
            rs.close();
            selectStatement.close();
        }
        return a;
    }

    public Adresse findAdresseOrCreate(String coordonnees,int idUtilisateur, String numero, String type_rue,
                                       String nom_rue, int code_postal,
                                       String ville, String pays) throws Exception {
        FraisDAO f = new FraisDAO();
        Adresse a = null;
        a = f.findAdresseByIdUtilisateurBycoordonnees(coordonnees, idUtilisateur);
        if (a!=null) return a;
        f.insertAdresseTrajet(numero, type_rue, nom_rue,code_postal,ville,pays);
        a = f.findAdresseByIdUtilisateurBycoordonnees(coordonnees, idUtilisateur);
        return a;
    }



    //---------------------------------------- M E T H O D E  T R A J E T --------------------------------------------
    public int createNewTrajetAndInsert(int idUtilisateur, String numeroAdDep, String numeroAdArr, String typeRueDep, String typeRueArr,
                                        String nomRueDep, String nomRueArr, String codePostalDep, String codePostalArr,
                                        String villeDep, String villeArr, String route, Date date ) throws Exception {
        Routing r = new Routing();
        FraisDAO f = new FraisDAO();
        if (numeroAdDep==null || numeroAdArr==null || typeRueDep==null || typeRueArr==null || nomRueDep==null ||
                nomRueArr==null || codePostalDep ==null || codePostalArr==null || villeDep==null || villeArr==null ||
                route==null || date==null || idUtilisateur==0)return 100;
        if (route.toUpperCase().equals("ALLER") || route.toUpperCase().equals("RETOUR")){

        int codeDep = Integer.parseInt(codePostalDep);
        int codeArr = Integer.parseInt(codePostalArr);
        String coordonneedep = r.findCoordoneesAdresse(numeroAdDep, typeRueDep, nomRueDep, codeDep,
                villeDep, "FRANCE");
        String coordonneearr = r.findCoordoneesAdresse(numeroAdArr, typeRueArr, nomRueArr, codeArr,
                villeArr, "FRANCE");
        Double distance = (r.demandeDistanceTrajet(coordonneedep, coordonneearr));
        Adresse adresseDepart = null;
        Adresse adresseArrivee = null;
        adresseDepart = f.findAdresseOrCreate(coordonneedep, idUtilisateur,numeroAdDep, typeRueDep, nomRueDep, codeDep,
                villeDep, "FRANCE");
        adresseArrivee = f.findAdresseOrCreate(coordonneearr, idUtilisateur,numeroAdArr, typeRueArr, nomRueArr, codeArr,
                villeArr, "FRANCE");
            System.out.println("if aller");
            System.out.println(idUtilisateur);
            System.out.println(date);
            System.out.println(route);
            System.out.println(adresseDepart.getId());
            System.out.println(adresseArrivee.getId());
            System.out.println(distance);
        int traj = f.insertNewTrajet(idUtilisateur,date, route, adresseDepart.getId(), adresseArrivee.getId(), distance, false);
        System.out.println(traj);

        return traj;
        }
        else if (route.toUpperCase().equals("ALLER/RETOUR")){
            int codeDep = Integer.parseInt(codePostalDep);
            int codeArr = Integer.parseInt(codePostalArr);
            String coordonneedep = r.findCoordoneesAdresse(numeroAdDep, typeRueDep, nomRueDep, codeDep,
                    villeDep, "FRANCE");
            String coordonneearr = r.findCoordoneesAdresse(numeroAdArr, typeRueArr, nomRueArr, codeArr,
                    villeArr, "FRANCE");
            Double distance1 = r.demandeDistanceTrajet(coordonneedep, coordonneearr);
            Double distance2 = r.demandeDistanceTrajet(coordonneearr, coordonneedep);
            Double distance = (distance1+distance2);
            Adresse adresseDepart = null;
            Adresse adresseArrivee = null;
            adresseDepart = f.findAdresseOrCreate(coordonneedep, idUtilisateur,numeroAdDep, typeRueDep, nomRueDep, codeDep,
                    villeDep, "FRANCE");
            adresseArrivee = f.findAdresseOrCreate(coordonneearr, idUtilisateur,numeroAdArr, typeRueArr, nomRueArr, codeArr,
                    villeArr, "FRANCE");
            System.out.println("if aller/retour");
            int traj = f.insertNewTrajet(idUtilisateur,date, route, adresseDepart.getId(), adresseArrivee.getId(), distance, false);
            System.out.println(traj);

            return traj;
        }else return 200;
    }

    public int insertNewTrajet(int id_utilisateur,Date date_trajet,String route,
                               int id_adresse_dep,int id_adresse_arr,Double distance,boolean archive) throws Exception{
        if (id_utilisateur == 0 || date_trajet == null || route == null || id_adresse_dep == 0 || id_adresse_arr==0 ||
                distance==0) return 0;

        System.out.println("id utilisateur = "+id_utilisateur);
        System.out.println("date = "+ date_trajet);
        System.out.println("route = "+ route);
        System.out.println("id_adresse dep = "+id_adresse_dep);
        System.out.println("id adresse arr = "+id_adresse_arr);
        System.out.println("distance = "+ distance);
        System.out.println("archive = "+ archive);

        PreparedStatement insertStatement = null;
        int rsu = 0;
        connection.setAutoCommit(false);

        try{
            insertStatement = connection.prepareStatement(SQLInsertNewTrajet);
            insertStatement.setInt(1,id_utilisateur);
            insertStatement.setDate(2,date_trajet);
            insertStatement.setString(3,route.toUpperCase());
            insertStatement.setInt(4,id_adresse_dep);
            insertStatement.setInt(5,id_adresse_arr);
            insertStatement.setDouble(6,distance);
            insertStatement.setBoolean(7,archive);

            rsu = insertStatement.executeUpdate();
            connection.commit();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return rsu;
    }


    public Set<Trajet> findAllTrajetByIdUtilisateur(int idUtilisateur) throws Exception{
        if (idUtilisateur == 0) return null;
        Set<Trajet> trajets= new HashSet<>();
        PreparedStatement selectStatement = null;
        ResultSet rs = null;

        //Recherche dans la base de données
        try{
            selectStatement = connection.prepareStatement(SQLFindAllTrajetByIdUtilisateur);
            selectStatement.setInt(1,idUtilisateur);

            rs = selectStatement.executeQuery();
            while (rs.next()){
                int idTrajet = rs.getInt("id_trajet");
                int id_utilisateur = rs.getInt("id_utilisateur");
                Date dateTrajet = rs.getDate("date_trajet");
                String route = rs.getString("route");
                int idAdresseDepart = rs.getInt("id_adresse_dep");
                int idAdresseArrivee = rs.getInt("id_adresse_arr");
                Double distance = rs.getDouble("distance");
                Boolean archive = rs.getBoolean("archive");
                Trajet t = new Trajet(idTrajet, idUtilisateur, dateTrajet, route, idAdresseDepart, idAdresseArrivee,
                        distance, archive);
                trajets.add(t);
            }
        }finally {
            rs.close();
            selectStatement.close();
        }
        return trajets;
    }

    public Set<Trajet> findTrajetNonArchiveByUtilisateur(int idUtilisateur) throws Exception{
        if (idUtilisateur == 0) return null;
        Set<Trajet> trajets= new HashSet<>();
        PreparedStatement selectStatement = null;
        ResultSet rs = null;

        //Recherche dans la base de données
        try{
            selectStatement = connection.prepareStatement(SQLFindTrajetNonArchiveByIdUtilisateur);
            selectStatement.setInt(1,idUtilisateur);

            rs = selectStatement.executeQuery();
            while (rs.next()){
                int idTrajet = rs.getInt("id_trajet");
                int id_utilisateur = rs.getInt("id_utilisateur");
                Date dateTrajet = rs.getDate("date_trajet");
                String route = rs.getString("route");
                int idAdresseDepart = rs.getInt("id_adresse_dep");
                int idAdresseArrivee = rs.getInt("id_adresse_arr");
                Double distance = rs.getDouble("distance");
                Boolean archive = rs.getBoolean("archive");
                Trajet t = new Trajet(idTrajet, idUtilisateur, dateTrajet, route, idAdresseDepart, idAdresseArrivee,
                        distance, archive);
                trajets.add(t);
            }
        }finally {
            rs.close();
            selectStatement.close();
        }
        return trajets;
    }

    public Set<Trajet> findTrajetArchiveByUtilisateur(int idUtilisateur) throws Exception{
        if (idUtilisateur == 0) return null;
        Set<Trajet> trajets= new HashSet<>();
        PreparedStatement selectStatement = null;
        ResultSet rs = null;

        //Recherche dans la base de données
        try{
            selectStatement = connection.prepareStatement(SQLFindTrajetArchiveByIdUtilisateur);
            selectStatement.setInt(1,idUtilisateur);

            rs = selectStatement.executeQuery();
            while (rs.next()){
                int idTrajet = rs.getInt("id_trajet");
                int id_utilisateur = rs.getInt("id_utilisateur");
                Date dateTrajet = rs.getDate("date_trajet");
                String route = rs.getString("route");
                int idAdresseDepart = rs.getInt("id_adresse_dep");
                int idAdresseArrivee = rs.getInt("id_adresse_arr");
                Double distance = rs.getDouble("distance");
                Boolean archive = rs.getBoolean("archive");
                Trajet t = new Trajet(idTrajet, idUtilisateur, dateTrajet, route, idAdresseDepart, idAdresseArrivee,
                        distance, archive);
                trajets.add(t);
            }
        }finally {
            rs.close();
            selectStatement.close();
        }
        return trajets;
    }

    public int archiveTrajet(int id_trajet) throws Exception{
        if (id_trajet == 0) return 0;

        PreparedStatement insertStatement = null;
        int rsu = 0;
        connection.setAutoCommit(false);

        try{
            insertStatement = connection.prepareStatement(SQLArchiveTrajetById);
            insertStatement.setInt(1,id_trajet);

            insertStatement.executeUpdate();

            connection.commit();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return rsu;
    }

    public int desarchiveTrajet(int id_trajet) throws Exception{
        if (id_trajet == 0) return 0;

        PreparedStatement insertStatement = null;
        int rsu = 0;
        connection.setAutoCommit(false);

        try{
            insertStatement = connection.prepareStatement(SQLDesarchiveTrajetById);
            insertStatement.setInt(1,id_trajet);
            insertStatement.executeUpdate();
            connection.commit();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return rsu;
    }

    //---------------------------------------- M E T H O D E  V E H I C U L E ------------------------------------------
    public int insertNewVehicule(String marque, String modele,int puissance,String immat,int id_utilisateur) throws Exception {
        if ( modele==null || puissance==0 || immat==null || id_utilisateur==0) return 0;

        PreparedStatement insertStatement = null;
        int rsu = 0;
        connection.setAutoCommit(false);

            try {
            insertStatement = connection.prepareStatement(SQLInsertNewVehicule);
            insertStatement.setString(1, marque.toUpperCase());
            insertStatement.setString(2, modele.toUpperCase());
            insertStatement.setInt(3, puissance);
            insertStatement.setString(4, immat.toUpperCase());
            insertStatement.setInt(5, id_utilisateur);

            rsu = insertStatement.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rsu;
    }

    public Set<Vehicule> findVehiculeByIdUtilisateur(int idUtilisateur) throws Exception{
        if (idUtilisateur == 0) return null;
        Set<Vehicule> vehicules= new HashSet<>();
        PreparedStatement selectStatement = null;
        ResultSet rs = null;

        //Recherche dans la base de données
        try{
            selectStatement = connection.prepareStatement(SQLFindVehiculeByIdUtilisateur);
            selectStatement.setInt(1,idUtilisateur);

            rs = selectStatement.executeQuery();
            while (rs.next()){
                int idVehicule = rs.getInt("id_vehicule");
                String marque = rs.getString("marque");
                String modele = rs.getString("modele");
                int puissance = rs.getInt("puissance");
                String immat = rs.getString("immat");

                Vehicule v = new Vehicule(idVehicule, marque, modele, puissance,immat, idUtilisateur);
                vehicules.add(v);
            }
        }finally {
            rs.close();
            selectStatement.close();
        }
        return vehicules;
    }

    public int deleteVehicule(int idVehicule) throws Exception {
        if ( idVehicule==0 ) return 0;

        PreparedStatement insertStatement = null;
        int rsu = 0;
        connection.setAutoCommit(false);

        try {
            insertStatement = connection.prepareStatement(SQLDeleteVehicule);
            insertStatement.setInt(1, idVehicule);

            rsu = insertStatement.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rsu;
    }

    public int updateVehicule(int idVehicule, String marque, String modele,int puissance,String immat) throws Exception {
        if ( modele==null || puissance==0 || immat==null || idVehicule==0 ) return 0;

        PreparedStatement insertStatement = null;
        int rsu = 0;
        connection.setAutoCommit(false);

        try {
            insertStatement = connection.prepareStatement(SQLUpdateVehicule);
            insertStatement.setString(1, marque.toUpperCase());
            insertStatement.setString(2, modele.toUpperCase());
            insertStatement.setInt(3, puissance);
            insertStatement.setString(4, immat.toUpperCase());
            insertStatement.setInt(5, idVehicule);

            rsu = insertStatement.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rsu;
    }

    public static void main(String[] args) throws Exception {
        Routing r = new Routing();
        FraisDAO f = new FraisDAO();
        //int essaie = f.insertUtilisateur("ADMIN", "ADMIN", "admin@admin.admin", "Administrateur59");
        /*int essaie = f.insertAdresseFavorite(1,"326", "rue", "jean jaures",
                59264, "onnaing", "FRANCE", "NORD");
        System.out.println(essaie);
        Set<Adresse> adresses = f.findAdresseByIdUtilisateur(1);
        for (Adresse a : adresses){
            System.out.println(a);
        }*/
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
       int test = f.createNewTrajetAndInsert(2,"1","16","rue",
               "rue","de thiant","pasteur", "59224",
               "59224","Monchaux sur ecaillon", "monchaux sur ecaillon",
               "ALLER/RETOUR", date);
        System.out.println(test);

    }
}
