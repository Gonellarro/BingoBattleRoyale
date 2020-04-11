package controler;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameterMap().containsKey("nom")) {
            HttpSession session = request.getSession();

            //TODO:
            //Guardar l'usuari en un hashmap
            Carto carto1 = new Carto();
            Carto carto2 = new Carto();
            carto1.generaCarto();
            carto2.generaCarto();

            session.setAttribute("carto1", carto1);
            session.setAttribute("carto2", carto2);
            request.getRequestDispatcher("cartons.jsp").forward(request, response);
        }
        if (request.getParameterMap().containsKey("numero")) {
            String numero = request.getParameter("numero");
            int num = Integer.parseInt(numero);
            boolean linia = false;
            
            if (num > 0) {
                Carto carto1 = new Carto();
                Carto carto2 = new Carto();

                HttpSession session = request.getSession();

                carto1 = (Carto) session.getAttribute("carto1");
                carto2 = (Carto) session.getAttribute("carto2");
                carto1.tachaNumero(num);
                carto2.tachaNumero(num);
                    
                linia = false;
                if ((carto2.esLinea()) || (carto1.esLinea())){
                   linia = true;
                }

                //TODO: 
                //Hem de comprovar si té bingo
                request.setAttribute("linia", linia);
                request.setAttribute("carto1", carto1);
                request.setAttribute("carto2", carto2);
                request.getRequestDispatcher("cartons.jsp").forward(request, response);
            }
        }

    }

}
