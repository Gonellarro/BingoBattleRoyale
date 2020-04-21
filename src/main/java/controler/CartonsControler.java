package controler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Carto;
import model.Parrilla;
import model.Partida;
import model.Usuari;

@WebServlet("/CartonsControler")
public class CartonsControler extends HttpServlet {

    private Usuari usuari;
    private Partida partida;
    private boolean invitacioNecessaria = false;
    private int numeroCartons;
    private static HashMap<String, String> invitacions = new HashMap<>();
    private static String missatges = "";
    private static String missatgeGlobal = "";
    private static List<Partida> partides = new ArrayList();
    private static boolean estrella = false;

    //Hem de menester 2 variables que controlin sa linea.
    //Una global i que , que indicarà a tots els jugadors que hi ha hagut linea
    //Una local que estarà apagada fins a que vegi que hi ha hagut linea i quan ho detecti, ho notificarà
    //Però s'encedrà per no tornar-ho a dir
    @Override
    public void init(ServletConfig config) throws ServletException {

        // Store the ServletConfig object and log the initialization
        super.init(config);
        //Cream una variable global a l'app per a que tengui totes les dades de la partida compartides
        this.partides = (List<Partida>) getServletContext().getAttribute("partides");
    }

    //DOGET I DOPOST
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameterMap().containsKey("accio")) {
            String accio = request.getParameter("accio");
            HttpSession session = request.getSession();
            this.partides = (List<Partida>) getServletContext().getAttribute("partides");
            this.usuari = consultaUsuari(session.getId());
            int idPartida = this.usuari.getPartida().getIdPartida();
            this.partida = consultaPartida(idPartida);
            switch (accio) {
                case "reiniciar":
                    List<Carto> cartons = new ArrayList();
                    cartons = iniciaCartons();
                    this.missatges = "";
                    this.missatgeGlobal = "";
                    this.estrella = false;
                    
                    //Actualitzam la partida dins la llista de partides
                    this.partides.remove(this.partida);
                    this.partida.reiniciar();
                    this.partides.add(this.partida);
                    getServletContext().setAttribute("partides", this.partides);

                    session.setAttribute("cartons", cartons);
                    session.setAttribute("partida", this.partida);
                    session.setAttribute("usuari", this.usuari);
                    request.setAttribute("jugadors", this.partida.getUsuaris().size());
                    request.setAttribute("missatges", this.missatges);
                    session.setAttribute("missatgeglobal", this.missatgeGlobal);
                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
                    break;
                case "graella":
                    Parrilla parrilla = new Parrilla();
                    parrilla.setBomboPartida(this.partida.getBolles());
                    request.setAttribute("parrilla",parrilla);
                    request.setAttribute("partida",this.partida);
                    request.getRequestDispatcher("bingoro.jsp").forward(request, response);
                    break;
                case "estadistiques":
                    request.setAttribute("nusuaris", this.partida.getUsuaris().size());
                    request.setAttribute("usuaris", this.partida.getUsuaris());
                    request.getRequestDispatcher("estadistiques.jsp").forward(request, response);
                    break;
                case "sortir":
                    this.partida.llevaUsuari(usuari);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //INICI - Insertam el nom i comprovam que no faci falta la invitació
        this.partides = (List<Partida>) getServletContext().getAttribute("partides");
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
                //Collim les dades de l'usuari
                String nom = request.getParameter("nom");
                String avatar = request.getParameter("avatar");
                int idPartida = Integer.parseInt(request.getParameter("idPartida"));
                this.partida = consultaPartida(idPartida);
                List<Carto> cartons = new ArrayList();

                //Si l'usuari no existeix
                if (!idUsuari(session.getId())) {
                    //Cream l'usuari i l'inicialitzam
                    this.usuari = new Usuari();
                    this.usuari.setNom(nom);
                    this.usuari.setAvatar(avatar);
                    this.usuari.setIdSession(session.getId());
                    this.usuari.setPartida(this.partida);
                    //Afegim l'usuari a la partida
                    this.partida.afegeixUsuari(usuari);
                    //Collim quants de cartons hem de tenir segons la partida
                    this.numeroCartons = this.partida.getCartons();
                    //Li donam els cartons
                    cartons = iniciaCartons();
                    //Ho publicam als missatges
                    this.missatges = this.missatges + "S'ha afegit el jugador " + nom + "\r\n";

                    //Si l'usuari SÍ existeix
                } else {
                    //Només passam les dades dels cartons que té (p.e. si s'ha fet un refrescar de la pàgina
                    this.usuari = consultaUsuari(session.getId());
                    cartons = (List<Carto>) session.getAttribute("cartons");
                }

                //Actualitzam la partida dins la llista de partides
                this.partides.remove(this.partida);
                this.partides.add(this.partida);
                getServletContext().setAttribute("partides", this.partides);
                //Passam la resta de dades al jsp

                session.setAttribute("cartons", cartons);
                session.setAttribute("partida", this.partida);
                session.setAttribute("usuari", this.usuari);
                request.setAttribute("jugadors", this.partida.getUsuaris().size());
                request.setAttribute("missatges", this.missatges);
                session.setAttribute("missatgeglobal", this.missatgeGlobal);
                request.getRequestDispatcher("cartons.jsp").forward(request, response);
            } else {
                //Enviar a jsp de no tenir codi o estar caducat
            }
        }

        //Running del sistema. Recollim el número i el marcam per al cartó
        //A part, també revisam si hi ha hagut linea o bingo
        if (request.getParameterMap().containsKey("numero")) {
            String numero = request.getParameter("numero");
            
            int num;
            if (numero.equals("")) {
                num = 0;
            } else {
                num = Integer.parseInt(numero);
            }
            List<Carto> cartons = new ArrayList();
            HttpSession session = request.getSession();
            this.usuari = consultaUsuari(session.getId());
            //Si és una altra partida, l'hem de posar com la nostra
            int idPartida = this.usuari.getPartida().getIdPartida();
            this.partida = consultaPartida(idPartida);

            cartons = (List<Carto>) session.getAttribute("cartons");

            if (this.usuari.isLinea()) {
                this.usuari.setLinea2(true);
                this.usuari.setLinea(false);
            }

            if (this.partida.isLinea() && !this.usuari.isLinea2()) {
                this.usuari.setLinea(true);
            }
            if (this.partida.isBingo()) {
                this.usuari.setBingo(true);
            }

            if (num > 0) {
                int i;
                for (i = 0; i < this.numeroCartons; i++) {
                    cartons.get(i).tachaNumero(num);
                    //Si cantam linea
                    if (cartons.get(i).esLinea()) {
                        if (!this.partida.isLinea()) {
                            this.usuari.setLinea(true);
                            this.missatges = this.missatges + this.usuari.getNom() + " ha cantat línea\r\n";
                            this.partida.setLinea(true);
                            this.missatgeGlobal = this.usuari.getNom() + " ha cantat linea!";
                            this.usuari.setLinies(this.usuari.getLinies() + 1);
                        }
                    }
                    //Si cantam bingo
                    if (cartons.get(i).isBingo()) {
                        cartons.get(i).bingo();
                        this.usuari.setBingo(true);
                        this.partida.setBingo(true);
                        this.missatges = this.missatges + this.usuari.getNom() + " ha cantat bingo\r\n";
                        this.missatgeGlobal = this.usuari.getNom() + " ha cantat bingo!";
                        this.usuari.setBingos(this.usuari.getBingos() + 1);
                    }
                    //Si només ens queda un número
                    if (cartons.get(i).getNumeros() == 14){
                        this.estrella = true;
                    }
                }
            }

            //Actualitzam la partida dins la llista de partides
            this.partides.remove(this.partida);
            this.partides.add(this.partida);
            getServletContext().setAttribute("partides", this.partides);

            session.setAttribute("cartons", cartons);
            session.setAttribute("partida", this.partida);
            session.setAttribute("usuari", this.usuari);
            request.setAttribute("estrella", this.estrella);
            request.setAttribute("jugadors", this.partida.getUsuaris().size());
            request.setAttribute("missatges", this.missatges);
            session.setAttribute("missatgeglobal", this.missatgeGlobal);
            request.getRequestDispatcher("cartons.jsp").forward(request, response);
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
        for (Usuari usuari : this.partida.getUsuaris()) {
            if (usuari.getIdSession().equals(cadena)) {
                resultat = true;
            }
        }
        return resultat;
    }

    public Usuari usuariGetID(String idSession) {
        Usuari usu = new Usuari();
        for (Usuari usuTmp : this.partida.getUsuaris()) {
            if (usuTmp.getIdSession().equals(idSession)) {
                usu = usuTmp;
            }
        }
        return usu;
    }

    public Partida consultaPartida(int id) {
        Partida partida = new Partida();
        for (Partida partidaTmp : this.partides) {
            if (partidaTmp.getIdPartida() == id) {
                partida = partidaTmp;
            }
        }
        return partida;
    }

    public Usuari consultaUsuari(String idSession) {
        Usuari usuari = new Usuari();
        for (Partida partidaTmp : this.partides) {
            for (Usuari usuTmp : partidaTmp.getUsuaris()) {
                if (usuTmp.getIdSession() == idSession) {
                    usuari = usuTmp;
                }
            }
        }
        return usuari;
    }

}
