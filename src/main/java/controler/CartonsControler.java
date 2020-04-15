package controler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Carto;

@WebServlet("/MenuControler")
public class CartonsControler extends HttpServlet {

    private static HashMap<String, String> usuaris = new HashMap<>();
    private static HashMap<String, String> avatars = new HashMap<>();
    private static HashMap<String, String> partida = new HashMap<>();
    private static HashMap<String, String> invitacions = new HashMap<>();
    private static String missatges = "";
    private boolean invitacioNecessaria = false;
    private int numeroCartons = 2;
    private boolean lineaGlobal = false;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameterMap().containsKey("accio")) {
            String accio = request.getParameter("accio");
            HttpSession session = request.getSession();
            switch (accio) {
                /////////////////////REINICIAR
                case "reiniciar":
                    List<Carto> cartons = new ArrayList();
                    cartons = iniciaCartons();
                    this.missatges = "";
                    this.lineaGlobal = false;

                    request.setAttribute("jugadors", this.usuaris.size());
                    session.setAttribute("cartons", cartons);
                    request.setAttribute("avatar", this.avatars.get(session.getId()));
                    request.setAttribute("nom", this.usuaris.get(session.getId()));
                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
                    break;
                /////////////////////SORTIR                    
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
        /////////////////////NOM -> LOGIN        
        if (request.getParameterMap().containsKey("nom")) {
            HttpSession session = request.getSession();
            boolean permes = false;
            carregaInvitacions();
            String invitacio = request.getParameter("invitacio");
            if (invitacioNecessaria) {
                //Cercam la invitació
                if (invitacions.containsKey(invitacio)) {
                    //Si la data no ha acabat
                    LocalDate avui = LocalDate.now();
                    LocalDate dataFi = LocalDate.parse(invitacions.get(invitacio));
                    if (avui.isAfter(dataFi)) {
                        //No se pot accedir al bingo
                        permes = false;
                    } else {
                        //Se pot accedir per no tenir
                        permes = true;
                    }
                }
            } else {
                //Si no és necessaria la invitacio
                permes = true;
            }

            if (permes) {
                String nom = request.getParameter("nom");
                String avatar = request.getParameter("avatar");
                List<Carto> cartons = new ArrayList();

                //Hem de veure si aquest usuari ja existeix
                if (!this.usuaris.containsKey(session.getId())) {
                    this.usuaris.put(session.getId(), nom);
                    this.avatars.put(session.getId(), avatar);
                    System.out.println("User: " + nom);
                    cartons = iniciaCartons();
                    this.missatges = this.missatges + "S'ha afegit el jugador " + nom + "\r\n";
                } else {
                    cartons = (List<Carto>) session.getAttribute("cartons");
                }

                session.setAttribute("cartons", cartons);
                request.setAttribute("avatar", avatar);
                request.setAttribute("nom", nom);
                request.setAttribute("jugadors", this.usuaris.size());
                request.setAttribute("missatges", missatges);
                request.getRequestDispatcher("cartons.jsp").forward(request, response);
            } else {
                //Enviar a jsp de no tenir codi o estar caducat
            }
        }
        
        /////////////////////NUMERO -> INTRUDUEIX EL NUMERO QUE HA SORTIT
        if (request.getParameterMap().containsKey("numero")) {
            String numero = request.getParameter("numero");
            int num = Integer.parseInt(numero);
            boolean linia = false;
            boolean bingo = false;
            List<Carto> cartons = new ArrayList();

            if (num > 0) {
                HttpSession session = request.getSession();
                cartons = (List<Carto>) session.getAttribute("cartons");
                int i;
                for (i = 0; i < this.numeroCartons; i++) {
                    cartons.get(i).tachaNumero(num);
                    if (cartons.get(i).esLinea()) {
                        linia = true;
                        if (!this.lineaGlobal) {
                            this.missatges = this.missatges + this.usuaris.get(session.getId()) + " ha cantado linea\r\n";
                            this.lineaGlobal = true;
                        }
                    }
                    if (cartons.get(i).isBingo()) {
                        cartons.get(i).bingo();
                        bingo = true;
                        linia = false;
                        this.missatges = this.missatges + this.usuaris.get(session.getId()) + " ha cantado bingo\r\n";
                    }
                }

                session.setAttribute("cartons", cartons);
                request.setAttribute("avatar", this.avatars.get(session.getId()));
                request.setAttribute("nom", this.usuaris.get(session.getId()));
                request.setAttribute("jugadors", this.usuaris.size());
                request.setAttribute("linia", linia);
                request.setAttribute("bingo", bingo);
                request.setAttribute("missatges", this.missatges);
                request.getRequestDispatcher("cartons.jsp").forward(request, response);
            }
        }
    }

    public List<Carto> iniciaCartons() {
        int i;
        List<Carto> cartonsX = new ArrayList();
        for (i = 0; i < this.numeroCartons; i++) {
            Carto cartoX = new Carto();
            cartoX.generaCarto();
            cartonsX.add(cartoX);
        }
        return cartonsX;
    }

    public void carregaInvitacions() {
        invitacions.put("permanent", "2050-04-13");
        invitacions.put("67890", "2020-04-11");
    }

}
