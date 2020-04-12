package controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Carto;

@WebServlet("/MenuControler")
public class MenuControler extends HttpServlet {

    private static HashMap<String, String> usuaris = new HashMap<>();
    private static HashMap<String, String> avatars = new HashMap<>();
    private static String missatges;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameterMap().containsKey("accio")) {
            String accio = request.getParameter("accio");
            HttpSession session = request.getSession();

            switch (accio) {
                case "reiniciar":

                    Carto carto1 = new Carto();
                    Carto carto2 = new Carto();
                    carto1.generaCarto();
                    carto2.generaCarto();
                    
                    this.missatges="";

                    session.setAttribute("carto1", carto1);
                    session.setAttribute("carto2", carto2);
                    request.setAttribute("avatar", this.avatars.get(session.getId()));
                    request.setAttribute("nom", this.usuaris.get(session.getId()));
                    System.out.println("Nom: " + this.usuaris.get(session.getId()));
                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
                    break;
                case "sortir":
                    this.usuaris.remove(session.getId());
                    this.avatars.remove(session.getId());
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameterMap().containsKey("nom")) {

            HttpSession session = request.getSession();
            String nom = request.getParameter("nom");
            String avatar = request.getParameter("avatar");

            this.usuaris.put(session.getId(), nom);
            this.avatars.put(session.getId(), avatar);

            Carto carto1 = new Carto();
            Carto carto2 = new Carto();
            carto1.generaCarto();
            carto2.generaCarto();
            this.missatges="";

            session.setAttribute("carto1", carto1);
            session.setAttribute("carto2", carto2);
            request.setAttribute("avatar", avatar);
            request.setAttribute("nom", nom);
            request.setAttribute("missatges", missatges);
            request.getRequestDispatcher("cartons.jsp").forward(request, response);
        }
        if (request.getParameterMap().containsKey("numero")) {
            String numero = request.getParameter("numero");
            int num = Integer.parseInt(numero);
            boolean linia = false;
            boolean bingo = false;

            if (num > 0) {
                Carto carto1 = new Carto();
                Carto carto2 = new Carto();

                HttpSession session = request.getSession();

                carto1 = (Carto) session.getAttribute("carto1");
                carto2 = (Carto) session.getAttribute("carto2");
                carto1.tachaNumero(num);
                carto2.tachaNumero(num);

                if ((carto2.esLinea()) || (carto1.esLinea())) {
                    linia = true;
                    this.missatges=this.missatges + this.usuaris.get(session.getId()) + " ha cantado linea\r\n";
                }

                if (carto1.isBingo()) {
                    carto1.bingo();
                    bingo = true;
                    linia = false;
                    this.missatges=this.missatges + this.usuaris.get(session.getId()) + " ha cantado bingo";
                }
                if (carto2.isBingo()) {
                    carto2.bingo();
                    bingo = true;
                    linia = false;
                    this.missatges=this.missatges + this.usuaris.get(session.getId()) + " ha cantado bingo";
                }
                request.setAttribute("avatar", this.avatars.get(session.getId()));
                request.setAttribute("nom", this.usuaris.get(session.getId()));
                request.setAttribute("linia", linia);
                request.setAttribute("bingo", bingo);
                request.setAttribute("missatges", this.missatges);
                request.setAttribute("carto1", carto1);
                request.setAttribute("carto2", carto2);
                request.getRequestDispatcher("cartons.jsp").forward(request, response);
            }
        }

    }

}
