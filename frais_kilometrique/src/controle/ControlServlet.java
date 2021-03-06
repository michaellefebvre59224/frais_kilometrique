package controle;

import modele.*;

import org.mindrot.jbcrypt.BCrypt;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/process")
public class ControlServlet extends HttpServlet {




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    //navigation
    private void forward(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            //recupertaion de l'action
            String action = request.getParameter("action");

            //execution du traitement
            switch (action) {
                case "init":                                   //redirige la page index ver l'ecran login
                    doInit(request, response);
                    break;
                case "afficheInscription":                     //affiche la pages pour une nouvelle inscription
                    doAfficheInscription(request, response);
                    break;
                case "authenticate":                           //recherche des login en base de données
                    doAuthenticate(request, response);
                    break;
                case "register":                               //enregistrement de l'utilisateur en base de données
                    doRegister(request, response);
                    break;
                case "ajoutTrajet":
                    doAjoutTrajet(request, response);
                    break;
                case "archiveTrajet":
                    doArchiveTrajet(request, response);
                    break;
                case "modifierNomPrenom":
                    doUptdateNomPrenom(request, response);
                    break;
                case "modifierPassword":
                    doUptdatePassword(request, response);
                    break;
                case "creerVehicule":
                    doCreerVehicule(request, response);
                    break;
                case "modifierVehicule":
                    doModifierVehicule(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doModifierVehicule(HttpServletRequest request, HttpServletResponse response) throws Exception{
        FraisDAO dao = FraisDAO.getSingleton();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String id= request.getParameter("idVehicule");
        int idVehicule = Integer.parseInt(id);
        String marque = request.getParameter("marque");
        String modele = request.getParameter("modele");
        String puis = request.getParameter("puissance");
        int puissance = Integer.parseInt(puis);
        String immat = request.getParameter("immat");
        String choix = request.getParameter("choix");

        if (choix.equals("on")){
            int resultat = dao.deleteVehicule(idVehicule);
            Set<Vehicule> vehiculesUtilisateur = dao.findVehiculeByIdUtilisateur(utilisateur.getId_utilisateur());
            request.getSession().setAttribute("vehiculesUtilisateur", vehiculesUtilisateur);
            String url = "pages/utilisateur_general.jsp";
            forward(url, request, response);
        }else {
            int resultat = dao.updateVehicule(idVehicule, marque, modele, puissance, immat);
            Set<Vehicule> vehiculesUtilisateur = dao.findVehiculeByIdUtilisateur(utilisateur.getId_utilisateur());
            request.getSession().setAttribute("vehiculesUtilisateur", vehiculesUtilisateur);
            String url = "pages/utilisateur_general.jsp";
            forward(url, request, response);
        }
    }

    private void doCreerVehicule(HttpServletRequest request, HttpServletResponse response) throws Exception{
        FraisDAO dao = FraisDAO.getSingleton();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String marque = request.getParameter("marque");
        String modele = request.getParameter("modele");
        String puis = request.getParameter("puissance");
        int puissance = Integer.parseInt(puis);
        String immat = request.getParameter("immat");
        int resultat = dao.insertNewVehicule(marque,modele,puissance,immat,utilisateur.getId_utilisateur());
        Set<Vehicule> vehiculesUtilisateur = dao.findVehiculeByIdUtilisateur(utilisateur.getId_utilisateur());
        request.getSession().setAttribute("vehiculesUtilisateur", vehiculesUtilisateur);
        String url = "pages/utilisateur_general.jsp";
        forward(url, request, response);
    }

    private void doUptdatePassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
        FraisDAO dao = FraisDAO.getSingleton();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String password = request.getParameter("password");
        String passwordConf = request.getParameter("passwordConf");
        int resultat = dao.updatePassword(password,passwordConf,utilisateur.getId_utilisateur());
        String url = "pages/utilisateur_general.jsp";
        forward(url, request, response);
    }

    private void doUptdateNomPrenom(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FraisDAO dao = FraisDAO.getSingleton();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        request.getSession().setAttribute("utilisateur", utilisateur);
        int resultat = dao.updateUtilisateurNomPrenom(utilisateur.getId_utilisateur(), nom,prenom);
        String url = "pages/utilisateur_general.jsp";
        forward(url, request, response);
    }

    private void doArchiveTrajet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FraisDAO dao = FraisDAO.getSingleton();
        String id = request.getParameter("choix");
        int idTrajet = Integer.parseInt(id);

        int res = dao.archiveTrajet(idTrajet);

        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        Set<Trajet> trajetsUtilisateur = dao.findTrajetNonArchiveByUtilisateur(utilisateur.getId_utilisateur());
        request.getSession().setAttribute("trajetsUtilisateur", trajetsUtilisateur);
        String url = "pages/utilisateur_general.jsp";
        forward(url, request, response);
    }


    private void doAjoutTrajet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String numeroAdDep = request.getParameter("numero_ad_dep");
        String numeroAdArr = request.getParameter("numero_ad_arr");
        String typeRueDep = request.getParameter("type_rue_dep");
        String typeRueArr = request.getParameter("type_rue_arr");
        String nomRueDep = request.getParameter("nom_rue_dep");
        String nomRueArr = request.getParameter("nom_rue_arr");
        String codePostalDep = request.getParameter("code_postal_dep");
        String codePostalArr = request.getParameter("code_postal_arr");
        String villeDep = request.getParameter("ville_dep");
        String villeArr = request.getParameter("ville_arr");
        String dateString = request.getParameter("date");
        String route = request.getParameter("route");
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateU = format.parse(dateString);
        java.sql.Date date = new java.sql.Date(dateU.getTime());


        FraisDAO dao = FraisDAO.getSingleton();

        int retourInsertion = dao.createNewTrajetAndInsert(utilisateur.getId_utilisateur(), numeroAdDep,numeroAdArr,
                typeRueDep,typeRueArr,nomRueDep, nomRueArr,codePostalDep,codePostalArr,villeDep, villeArr, route, date);

        Set<Trajet> trajetsUtilisateur = dao.findTrajetNonArchiveByUtilisateur(utilisateur.getId_utilisateur());

        request.getSession().setAttribute("trajetsUtilisateur", trajetsUtilisateur);


        String url = "pages/utilisateur_general.jsp";
        forward(url, request, response);
    }

    //
    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String emailConfirmation = request.getParameter("email_conf");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password_conf");
        try {
            //Connection a la base de données
            FraisDAO dao = FraisDAO.getSingleton();
            //vérification des identifiants si non null
            if (email == null || password == null || nom == null || prenom == null ||
                    emailConfirmation==null || passwordConfirmation==null) {
                String url = "pages/inscription.jsp";
                forward(url, request, response);
            }
            //insertion de l'utilisateur dans la base de donées
            Utilisateur login = new Utilisateur(nom,prenom,email,password, "UTILISATEUR");
            int resultat = dao.insertUtilisateur(nom, prenom, email, password);

            //mise de l'utilisateur en session
            login = dao.findByEmail(email);
            request.getSession().setAttribute("utilisateur", login);
            //aller à la page générale de l'utilisateur
            String url = "pages/utilisateur_general.jsp";
            forward(url, request, response);
        }catch (Exception e) {
            e.printStackTrace();
        }
        String url = "pages/inscription.jsp";
        forward(url, request, response);

    }

    private void doAuthenticate (HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            //Connection a la base de données
            FraisDAO dao = FraisDAO.getSingleton();

            //vérification des identifiants si non null
            if (email == null) return;

            //recherche de l'utilisateur dans la base de données
            Utilisateur login = dao.findByEmail(email);

            //Selon si utilisateur trouvé ou non
            if (login == null) {
                //page connection avec message d'erreur l'adresse mail ou le mot de passe est inconnu
            } else {
                //page general
                //if à changer en fonction de la fonction de l'utilisateur admin , utilisateur, consultation...
                if (login.getFonction() != null) {
                    if (BCrypt.checkpw(password, login.getMdp())) {
                        System.out.println("mdp ok");
                        //mot de passe ok
                        //mise de l'utilisateur en session
                        request.getSession().setAttribute("utilisateur", login);

                        //recupération liste des trajets, vehicules,  de l'utilisateur
                        Set<Trajet> trajetsUtilisateur = dao.findTrajetNonArchiveByUtilisateur(login.getId_utilisateur());
                        Set<Vehicule> vehiculesUtilisateur = dao.findVehiculeByIdUtilisateur(login.getId_utilisateur());
                        Set<Adresse> adressesFavoritesUtilisateur = dao.findAdresseByIdUtilisateur(login.getId_utilisateur());
                        request.getSession().setAttribute("trajetsUtilisateur", trajetsUtilisateur);
                        request.getSession().setAttribute("vehiculesUtilisateur", vehiculesUtilisateur);
                        request.getSession().setAttribute("adressesFavoritesUtilisateur", adressesFavoritesUtilisateur);
                        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                        request.getSession().setAttribute("date", date);
                        //aller à la page générale de l'utilisateur
                        String url = "pages/utilisateur_general.jsp";
                        forward(url, request, response);
                    }else {
                        String url = "pages/index.jsp";
                        forward(url, request, response);
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doAfficheInscription (HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String url = "pages/inscription.jsp";
        forward(url, request, response);
    }

    private void doInit (HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String url = "pages/connection.jsp";
        forward(url, request, response);
    }

}
