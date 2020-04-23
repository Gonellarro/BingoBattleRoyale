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
import javax.servlet.http.HttpSession;
import model.Bolla;
import model.Parrilla;
import model.Partida;

@WebServlet("/BingoControler")
public class BingoControler extends HttpServlet {

    private List<Partida> partides = new ArrayList();
    private Partida partida;
    private Parrilla parrilla;

    @Override
    public void init(ServletConfig config) throws ServletException {

        // Store the ServletConfig object and log the initialization
        super.init(config);
        //Cream una variable global a l'app per a que tengui totes les dades de la partida compartides
        getServletContext().setAttribute("partides", this.partides);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Bingo virtual
        if (request.getParameterMap().containsKey("virtual")) {
            String virtual = request.getParameter("virtual");
            Bolla bolla = new Bolla();
            int i;
            switch (virtual) {
                case "iniciar":
                    //Hem de demanar el nom de la partida i cartons, per crear-la després
                    request.setAttribute("tipus", "virtual");
                    request.getRequestDispatcher("bingo_menu.jsp").forward(request, response);
                    break;
                case "reiniciar":
                    this.parrilla = new Parrilla();
                    
                    //Actualitzam la partida dins la llista de partides
                    this.partides.remove(this.partida);
                    this.partida.reiniciar();
                    this.partides.add(this.partida);
                    getServletContext().setAttribute("partides", this.partides);

                    bolla.setValor(-1);
                    request.setAttribute("parrilla", this.parrilla);
                    request.setAttribute("bollaActual", bolla);
                    request.getRequestDispatcher("bingo.jsp").forward(request, response);
                    break;
                case "sortir":
                    this.partides.remove(this.partida);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                default:
                    break;
            }

        }

        if (request.getParameterMap().containsKey("manual")) {
            System.out.println("MANUAL");
            String manual = request.getParameter("manual");
            //TODO
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameterMap().containsKey("tipus")) {
            String tipus = request.getParameter("tipus");
            //Si és un bingo virtual
            if (tipus.equals("virtual")) {
                HttpSession session = request.getSession();
                Bolla bolla = new Bolla();
                //Inici. Hem de crear la partida. Per això demanam titol i cartons
                if (request.getParameterMap().containsKey("titol")) {
                    iniciar(request);
                    bolla.setValor(-1);
                }
                //Hem de donar una nova bolla
                if (request.getParameterMap().containsKey("accio")) {
                    String accio = request.getParameter("accio");
                    if (accio.equals("bolla")) {
                        //Si encara se poden treure més bolles
                        if (this.parrilla.getComptador() < this.parrilla.getNUMBOLLES()) {
                            //treim una bolla i la posam a la parrilla;
                            bolla = this.parrilla.treureBolla();
                            //També l'afegim a la partida per dur un compte de les bolles que han sortit
                            this.partida.afegeixBolla(bolla);
                        } else {
                            //TODO
                            //HEM DE DIR QUE HEM ACABAT
                        }
                    }
                }
                //Guardam com a variable global dels servlets la partida en curs, ja que ha pogut ser modificada
                getServletContext().setAttribute("partida", this.partida);
                request.setAttribute("parrilla", this.parrilla);
                request.setAttribute("bollaActual", bolla);
                request.getRequestDispatcher("bingo.jsp").forward(request, response);

            } else if (tipus.equals("manual")) {
                //TODO
            }
        }
    }

    public void iniciar(HttpServletRequest request) {
        int cartons = Integer.parseInt(request.getParameter("cartons"));
        String titol = request.getParameter("titol");
        Bolla bolla = new Bolla();
        int i;

        int idPartida = (int) Math.floor(Math.random() * (99999));

        //Cream la partida
        this.partida = new Partida();
        this.partida.setTitol(titol);
        this.partida.setIdPartida(idPartida);
        this.partida.setCartons(cartons);
        //L'afegim al lliostat de partides que tenim en marxa
        this.partides.add(partida);

        //Cream una nova parrilla
        this.parrilla = new Parrilla();
    }
}
