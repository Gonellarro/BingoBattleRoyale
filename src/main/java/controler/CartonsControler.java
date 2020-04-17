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
import model.Usuari;

@WebServlet("/CartonsControler")
public class CartonsControler extends HttpServlet {

    private static List<Usuari> usuaris = new ArrayList();
    private Usuari usuari;
    private static HashMap<String, String> invitacions = new HashMap<>();
    private static String missatges = "";
    private boolean invitacioNecessaria = false;
    private int numeroCartons = 2;
    private boolean lineaGlobal = false;

    //DOGET I DOPOST
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
                    this.usuari = usuariGetID(session.getId());
                    this.usuari.novaPartida();

                    request.setAttribute("jugadors", this.usuaris.size());
                    session.setAttribute("cartons", cartons);
                    request.setAttribute("avatar", this.usuari.getAvatar());
                    request.setAttribute("nom", this.usuari.getNom());
                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
                    break;
                /////////////////////ESTADISTIQUES                    
                case "estadistiques":
                    this.usuari = usuariGetID(session.getId());
                    this.usuari.calculaBingos();
                    this.usuari.calculaLinies();
                    System.out.println("Linies: " + this.usuari.getLinies());
                    System.out.println("Linies: " + this.usuari.getBingos());

                    request.setAttribute("nusuaris", this.usuaris.size());
                    request.setAttribute("usuaris", this.usuaris);
                    request.getRequestDispatcher("estadistiques.jsp").forward(request, response);
                    break;
                /////////////////////SORTIR                    
                case "sortir":
                    this.usuari = usuariGetID(session.getId());
                    this.usuaris.remove(this.usuari);
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

                //Si l'usuari no existeix
                if (!idUsuari(session.getId())) {
                    //Cream l'usuari i l'inicialitzam
                    this.usuari = new Usuari();
                    this.usuari.setNom(nom);
                    this.usuari.setAvatar(avatar);
                    this.usuari.setIdSession(session.getId());
                    this.usuari.getPartida().setCartons(numeroCartons);
                    //Introduim l'usuari a la llista d'usuaris que estan jugant
                    this.usuaris.add(usuari);
                    //Li donam els cartons
                    cartons = iniciaCartons();
                    //Ho publicam als missatges
                    this.missatges = this.missatges + "S'ha afegit el jugador " + nom + "\r\n";

                    //Si l'usuari SÍ existeix
                } else {
                    //Només passam les dades dels cartons que té (p.e. si s'ha fet un refrescar de la pàgina
                    this.usuari = usuariGetID(session.getId());
                    cartons = (List<Carto>) session.getAttribute("cartons");
                }

                session.setAttribute("cartons", cartons);
                request.setAttribute("nom", this.usuari.getNom());
                request.setAttribute("avatar", this.usuari.getAvatar());
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
                this.usuari = usuariGetID(session.getId());
                cartons = (List<Carto>) session.getAttribute("cartons");
                int i;
                for (i = 0; i < this.numeroCartons; i++) {
                    cartons.get(i).tachaNumero(num);
                    if (cartons.get(i).esLinea()) {
                        linia = true;
                        if (!this.lineaGlobal) {
                            this.missatges = this.missatges + this.usuari.getNom() + " ha cantado linea\r\n";
                            this.lineaGlobal = true;
                            this.usuari.insertaLinea();
                        }
                    }
                    if (cartons.get(i).isBingo()) {
                        cartons.get(i).bingo();
                        bingo = true;
                        linia = false;
                        this.usuari.insertaBingo();
                        this.missatges = this.missatges + this.usuari.getNom() + " ha cantado bingo\r\n";
                    }
                }

                System.out.println("USUARI EN AQUESTA SESSIO: " + this.usuari.getNom());
                session.setAttribute("cartons", cartons);
                session.setAttribute("linea", linia);
                session.setAttribute("bing", bingo);
                request.setAttribute("nom", this.usuari.getNom());
                request.setAttribute("avatar", this.usuari.getAvatar());
                request.setAttribute("jugadors", this.usuaris.size());
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

    public boolean idUsuari(String cadena) {
        boolean resultat = false;
        for (Usuari usuari : this.usuaris) {
            if (usuari.getIdSession().equals(cadena)) {
                resultat = true;
            }
        }
        return resultat;
    }

    public Usuari usuariGetID(String idSession) {
        Usuari usu = new Usuari();
        for (Usuari usuTmp : this.usuaris) {
            if (usuTmp.getIdSession().equals(idSession)) {
                usu = usuTmp;
            }
        }
        return usu;
    }

}
