package controle;

import modele.FraisDAO;
import modele.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private void forward(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            //recupertaion de l'action
            String action = request.getParameter("action");

            //execution du traitement
            switch (action){
                case "init" :                                   //redirige la page index ver l'ecran login
                    doInit(request, response);
                    break;
                case "afficheInscription" :                     //affiche la pages pour une nouvelle inscription
                    doAfficheInscription(request, response);
                    break;
                case "authenticate" :                           //recherche des login en base de données
                    doAuthenticate(request, response);
                    break;
                case "register" :                               //enregistrement de l'utilisateur en base de données
                    doRegister(request, response);
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    //
    private void doRegister(HttpServletRequest request, HttpServletResponse response) {


    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            //Connection a la base de données
            FraisDAO dao = FraisDAO.getSingleton();
            //vérification des identifiants si non null
            if (email == null || password == null) return;
            //recherche de l'utilisateur dans la base de données
            Utilisateur login = dao.findByEmailAndPw(email, password);
            //Selon si utilisateur trouvé ou non
            if(login == null){
                //page connection avec message d'erreur l'adresse mail ou le mot de passe est inconnu
            }else {
                //page general
                //if à changer en fonction de la fonction de l'utilisateur admin , utilisateur, consultation...
                if (login.getFonction()!=null){
                    //mise de l'utilisateur en session
                    request.getSession().setAttribute("utilisateur", login);

                    //aller à la page générale de l'utilisateur
                    String url = "pages/utilisateur_general.jsp";
                    forward(url, request, response);

                }
            }


        }catch (SQLException ex){
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void doAfficheInscription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String url = "pages/inscription.jsp";
        forward(url, request, response);
    }

    private void doInit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String url = "pages/connection.jsp";
        forward(url, request, response);
    }
}