package controler;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Bolla;
import model.Parrilla;

@WebServlet("/BingoControler")
public class BingoControler extends HttpServlet {

    private String idPartida;
    private Parrilla parrilla;
    private static List<Bolla> tresBolles = new ArrayList();

    @Override
    public void init(ServletConfig config) throws ServletException {

        // Store the ServletConfig object and log the initialization
        super.init(config);
        //Cream una variable global a l'app per a que tengui les darreres 3 bolles
        getServletContext().setAttribute("tresbolles", this.tresBolles);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DOGET BINGO");
        if (request.getParameterMap().containsKey("virtual")) {
            String virtual = request.getParameter("virtual");
            Bolla bolla = new Bolla();
            int i;
            switch (virtual) {
                case "iniciar":
                    this.idPartida = "XXXX";
                    this.parrilla = new Parrilla();
                    bolla.setValor(-1);
                    //Iniciam a 0 les 3 bolles

                    this.tresBolles.clear();
                    for (i = 0; i < 3; i++) {
                        this.tresBolles.add(bolla);
                    }
                    //Guardam com a variable global dels servlets
                    getServletContext().setAttribute("tresbolles", this.tresBolles);
                    break;
                case "bolla":
                    if (this.parrilla.getComptador() < this.parrilla.getNUMBOLLES()) {
                        bolla = this.parrilla.treureBolla();
                        this.tresBolles.remove(0);
                        this.tresBolles.add(bolla);
                        //Guardam com a variable global dels servlets
                        getServletContext().setAttribute("tresbolles", this.tresBolles);
                    } else {
                        //HEM DE DIR QUE HEM ACABAT
                    }
                    break;
                case "reiniciar":
                    this.parrilla = new Parrilla();
                    bolla.setValor(-1);
                    //Iniciam a 0 les 3 bolles
                    this.tresBolles.clear();
                    for (i = 0; i < 3; i++) {
                        this.tresBolles.add(bolla);
                    }
                    //Guardam com a variable global dels servlets
                    getServletContext().setAttribute("tresbolles", this.tresBolles);
                    break;
                case "sortir":
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                default:
                    break;
            }
            request.setAttribute("parrilla", this.parrilla);
            request.setAttribute("bollaActual", bolla);
            request.setAttribute("idPartida", this.idPartida);
            request.getRequestDispatcher("bingo.jsp").forward(request, response);
        }

        if (request.getParameterMap().containsKey("manual")) {
            System.out.println("MANUAL");
            String manual = request.getParameter("manual");
            Bolla bolla = new Bolla();
            int i;
            switch (manual) {
                case "iniciar":
                    this.idPartida = "XXXX";
                    this.parrilla = new Parrilla();
                    bolla.setValor(-1);
                    //Iniciam a 0 les 3 bolles

                    this.tresBolles.clear();
                    for (i = 0; i < 3; i++) {
                        this.tresBolles.add(bolla);
                    }
                    //Guardam com a variable global dels servlets
                    getServletContext().setAttribute("tresbolles", this.tresBolles);
                    break;
                default:
                    break;
            }

            request.setAttribute("parrilla", this.parrilla);
            request.setAttribute("bollaActual", bolla);
            request.setAttribute("idPartida", this.idPartida);
            request.getRequestDispatcher("bingomanual.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameterMap().containsKey("numero")) {
            int numero = Integer.parseInt(request.getParameter("numero"));
            Bolla bolla = new Bolla();
            bolla.setValor(numero-1);
            this.parrilla.insertarBolla(bolla);

            this.tresBolles.remove(0);
            this.tresBolles.add(bolla);

            //Guardam com a variable global dels servlets
            getServletContext().setAttribute("tresbolles", this.tresBolles);

            request.setAttribute("parrilla", this.parrilla);
            request.setAttribute("bollaActual", bolla);
            request.setAttribute("idPartida", this.idPartida);
            request.getRequestDispatcher("bingomanual.jsp").forward(request, response);
        }

    }
}
