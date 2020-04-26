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
import model.Battle;
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
    private List<Carto> cartons = new ArrayList();
    private static HashMap<String, String> invitacions = new HashMap<>();
    private static List<Partida> partides = new ArrayList();

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
                    this.cartons = iniciaCartons();
                    this.usuari.setPintarEvent(false);
                    this.usuari.setLinea(false);
                    this.usuari.setBingo(false);
                    this.usuari.setCartons(iniciaCartons());
                    this.usuari.setAtac(false);

                    session.setAttribute("partida", this.partida);
                    session.setAttribute("usuari", this.usuari);
                    request.setAttribute("jugadors", this.partida.getUsuaris().size());
                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
                    break;
                case "graella":
                    Parrilla parrilla = new Parrilla();
                    parrilla.setBomboPartida(this.partida.getBolles());
                    request.setAttribute("parrilla", parrilla);
                    request.setAttribute("partida", this.partida);
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
                case "bomba":
                    Parrilla graella = new Parrilla();
                    graella.setBomboPartida(this.partida.getBolles());
                    Battle batalla = new Battle();
                    this.partida = batalla.bomba(this.partida, session.getId(), graella);

                    //Actualitzam la partida dins la llista de partides
                    this.partides.remove(this.partida);
                    this.partides.add(this.partida);
                    getServletContext().setAttribute("partides", this.partides);

                    session.setAttribute("partida", this.partida);
                    session.setAttribute("usuari", this.usuari);
                    request.setAttribute("jugadors", this.partida.getUsuaris().size());
                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.partides = (List<Partida>) getServletContext().getAttribute("partides");
        HttpSession session = request.getSession();

        //Si trobam nom, és que hem de crear un usuari nou 
        if (request.getParameterMap().containsKey("nom")) {
            //Hem de veure si hem de crear o no un nou usuari
            if (permetre(request)) {
                crearUsuari(request, session);
            } else {
                //jsp_error;
            }
        }

        //Si el que arriba és el número, hem de tachar-ho
        if (request.getParameterMap().containsKey("numero")) {
            String numero = request.getParameter("numero");

            this.usuari = consultaUsuari(session.getId());
            int idPartida = this.usuari.getPartida().getIdPartida();
            this.partida = consultaPartida(idPartida);

            marcaNumero(numero, request, session);
            comprovaLinea();
            comprovaBingo();
            comprovaDarrerNumero();
            comprovaAtac();
            comprovaPintar();
        }

        //Actualitzam la partida dins la llista de partides
        this.partides.remove(this.partida);
        this.partides.add(this.partida);
        getServletContext().setAttribute("partides", this.partides);
        //Enviam les dades al jsp
        session.setAttribute("partida", this.partida);
        session.setAttribute("usuari", this.usuari);
        request.setAttribute("estrella", this.partida.isEstrella());
        request.setAttribute("jugadors", this.partida.getUsuaris().size());
        request.getRequestDispatcher("cartons.jsp").forward(request, response);

    }

    //Mètodes per fer funcionar el controlador
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

    public boolean permetre(HttpServletRequest request) {
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
                    //enviar al jsp amb error
                } else {
                    //Se pot accedir per no tenir
                    permes = true;
                }
            }
        } else {
            //Si no és necessaria la invitacio
            permes = true;
        }
        return permes;
    }

    public void crearUsuari(HttpServletRequest request, HttpSession session) {
        //Collim les dades de l'usuari
        String nom = request.getParameter("nom");
        String avatar = request.getParameter("avatar");
        int idPartida = Integer.parseInt(request.getParameter("idPartida"));
        this.partida = consultaPartida(idPartida);

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
            this.usuari.setCartons(iniciaCartons());
            //Li assignam un perfil al atzar
            this.usuari.assignaPerfil();
            //Ho publicam als missatges
            this.partida.setMissatgesLog(this.partida.getMissatgesLog() + "S'ha afegit el jugador " + nom + "\r\n");

            //Si l'usuari SÍ existeix
        } else {
            //Només passam les dades dels cartons que té (p.e. si s'ha fet un refrescar de la pàgina
            this.usuari = consultaUsuari(session.getId());
            this.usuari.setCartons((List<Carto>) session.getAttribute("cartons"));
        }
    }

    public void marcaNumero(String numero, HttpServletRequest request, HttpSession session) {
        int num;
        if (numero.equals("")) {
            num = 0;
        } else {
            num = Integer.parseInt(numero);
        }

        //List<Carto> cartons = new ArrayList();
        this.usuari = consultaUsuari(session.getId());
        //Si és una altra partida, l'hem de posar com la nostra
        int idPartida = this.usuari.getPartida().getIdPartida();
        this.partida = consultaPartida(idPartida);

        //cartons = (List<Carto>) session.getAttribute("cartons");
        if (num > 0) {
            int i;
            for (i = 0; i < this.numeroCartons; i++) {
                this.usuari.getCartons().get(i).tachaNumero(num);
            }
        }
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
                if (usuTmp.getIdSession().equals(idSession)) {
                    usuari = usuTmp;
                }
            }
        }
        return usuari;
    }

    public void comprovaLinea() {
        int i;
        for (i = 0; i < this.numeroCartons; i++) {
            if (this.usuari.getCartons().get(i).esLinea()) {
                if (!this.partida.isLinea()) {
                    this.partida.setMissatgesLog(this.partida.getMissatgesLog() + this.usuari.getNom() + " ha cantat línea\r\n");
                    this.partida.setLinea(true);
                    this.partida.setMissatgesEvents(this.usuari.getNom() + " ha cantat linea!");
                    //Aumentam en 1 el número de linies cantades
                    this.usuari.setLinies(this.usuari.getLinies() + 1);
                }
            }
        }
    }

    public void comprovaBingo() {
        int i;
        for (i = 0; i < this.numeroCartons; i++) {
            if (this.usuari.getCartons().get(i).esBingo()) {
                this.usuari.getCartons().get(i).bingo();
                this.partida.setBingo(true);
                this.partida.setMissatgesLog(this.partida.getMissatgesLog() + this.usuari.getNom() + " ha cantat bingo\r\n");
                this.partida.setMissatgesEvents(this.usuari.getNom() + " ha cantat bingo!");
                //Aumentam en 1 els números de bingos cantats
                this.usuari.setBingos(this.usuari.getBingos() + 1);
            }
        }
    }

    public void comprovaDarrerNumero() {
        int i;
        for (i = 0; i < this.partida.getCartons(); i++) {
            if (this.usuari.getCartons().get(i).getNumeros() == 14) {
                this.partida.setEstrella(true);
            }
        }
    }

    public void comprovaAtac() {

    }

    public void comprovaPintar() {
        this.usuari.setPintarEvent(false);
        if ((this.partida.isLinea()) && (!this.usuari.isLinea())) {
            this.usuari.setPintarEvent(true);
            this.usuari.setLinea(true);
        }
        if ((this.partida.isBingo()) && (!this.usuari.isBingo())) {
            this.usuari.setPintarEvent(true);
            this.usuari.setBingo(true);
        }
        if (this.partida.isAtac() && (!this.usuari.isAtac())) {
            this.usuari.setPintarEvent(true);
            this.usuari.setAtac(true);
        }
    }
}
