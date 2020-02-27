package controle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                case "init" :
                    doInit(request, response);
                    break;
                case "afficheInscription" :
                    doAfficheInscription(request, response);
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
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
