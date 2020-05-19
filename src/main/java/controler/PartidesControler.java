package controler;

import Beans.GestioUsuaris;
import Beans.Utils;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Battle;
import model.Parrilla;
import model.Partida;
import model.Usuari;
import model.Bingo;
import model.Carto;
import model.Sala;

@WebServlet("/PartidesControler")
public class PartidesControler extends HttpServlet {

    private Bingo bingo;
    private Usuari usuari;
    private Partida partida;
    private Sala sala;
    private boolean debug;

    @Override
    public void init(ServletConfig config) throws ServletException {

        // Store the ServletConfig object and log the initialization
        super.init(config);
        //Carregam la variable amb el Bingo
        this.bingo = (Bingo) getServletContext().getAttribute("bingo");
        this.debug = true;
    }

    //DOGET I DOPOST
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Partida Controler: -------------DOGET------------");
        HttpSession session = request.getSession();
        this.bingo = (Bingo) getServletContext().getAttribute("bingo");

        /**
         * Miram quina accio hem de tractar
         */
        if (request.getParameterMap().containsKey("accio")) {
            String accio = request.getParameter("accio");
            /**
             * Sempre hem de rebre la id de la sala on jugam així que ja el
             * collim
             */
            int idSala = Integer.parseInt(request.getParameter("idSala"));
            Utils ut = new Utils();
            if (idSala != 0) {
                this.sala = ut.donaSalaPerID(idSala, this.bingo.getSales());
                this.partida = ut.donaDarreraPartida(sala);
                this.usuari = ut.donaUsuariSala(this.bingo, idSala, session.getId());
            }

            switch (accio) {
                case "reiniciar":
                    /**
                     * Per reiniciar el que hem de fer és trobar l'usuari i
                     * reiniciar-lo
                     */
                    /**
                     * Per reiniciar, hem de saber quants de cartomns té la
                     * sala.Collim el número de la sala del parametre sala
                     */
                    //Hem de llevar l'usuari de la sala per després afegir-lo actualitzat
                    this.sala.getUsuaris().remove(this.usuari);
                    //Cream un usuari copia
                    Usuari usuariTmp = new Usuari();
                    usuariTmp.copia(this.usuari);
                    this.usuari = usuariTmp;
                    //this.usuari.reinicia(sala.getNcartons());
                    this.usuari.setnPartida(sala.getPartides().size());
                    //Afegim l'usuari a la sala
                    this.sala.afegeixUsuari(this.usuari);
                    //Afegim l'usuari a la partida nova
                    this.partida.afegeixUsuari(this.usuari);
                    pintaJsp(request, response);
                    break;
                case "graella":
                    Parrilla parrilla = new Parrilla();
                    this.partida = ut.donaDarreraPartida(this.sala);
                    parrilla.setBomboPartida(this.partida.getBolles());
                    request.setAttribute("parrilla", parrilla);
                    request.setAttribute("sala", this.sala);
                    request.setAttribute("partida", this.partida);
                    request.getRequestDispatcher("bingoro.jsp").forward(request, response);
                    break;
                case "estadistiques":
                    this.partida = ut.donaDarreraPartida(this.sala);
                    request.setAttribute("sala", this.sala);
                    request.setAttribute("nusuaris", this.partida.getUsuaris().size());
                    request.setAttribute("usuaris", this.partida.getUsuaris());
                    request.getRequestDispatcher("estadistiques.jsp").forward(request, response);
                    break;
                case "sortir":
                    this.sala.llevaUsuari(this.usuari);
                    this.partida.llevaUsuari(this.usuari);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                case "bomba":
                    Parrilla graella = new Parrilla();
                    graella.setBomboPartida(this.partida.getBolles());
                    Battle batalla = new Battle();
                    this.partida = batalla.bomba(this.partida, session.getId(), graella);
                    pintaJsp(request, response);
                    break;
                case "canvi":
                    Battle batalla2 = new Battle();
                    this.partida = batalla2.canvi(this.partida, this.usuari);
                    this.usuari.setCanvi(0);
                    pintaJsp(request, response);

                    break;
                case "platan":
                    Battle batalla3 = new Battle();
                    this.partida = batalla3.platan(this.partida, this.usuari);
                    pintaJsp(request, response);
                    break;
                case "estrella":
                    Battle batalla4 = new Battle();
                    batalla4.estrella(this.partida, this.usuari);
                    pintaJsp(request, response);
                    break;
                case "panell":
                    request.setAttribute("bingo", this.bingo);
                    request.getRequestDispatcher("panelldecontrol.jsp").forward(request, response);
                default:
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Partida Controler: ------------DOPOST-------------");
        HttpSession session = request.getSession();
        this.bingo = (Bingo) getServletContext().getAttribute("bingo");
        int idSala = Integer.parseInt(request.getParameter("idSala"));
        Utils ut = new Utils();
        this.sala = ut.donaSalaPerID(idSala, this.bingo.getSales());

        /**
         * Començam en el POST revisant si s'ha de crear un usuari nou o no En
         * aquest cas, rebem 3 paràmetres del jsp de menu: nom, avatar i id de
         * la sala on vol entrar Si l'usuari no existeix dins aquesta sala, hem
         * de crear-ne un, afegir-lo a la sala i a la partida Si l'usuari ja
         * existia, no l'hem de crear de nou, símplement hem d'assignar-li al
         * jsp nou
         */
        if (request.getParameterMap().containsKey("nom")) {
            String nom = request.getParameter("nom");
            String avatar = request.getParameter("avatar");
            /**
             * Per tractar la gestio de l'usuari, tenim un BEAN: gestio
             * d'usuaris, amb el mètode crearUsuari Aquest mètode ens hauria de
             * retornar l'usuari, de forma que es pugui publicar al jsp dels
             * cartons
             */
            GestioUsuaris gs = new GestioUsuaris();
            this.usuari = gs.crearUsuari(nom, avatar, idSala, this.bingo, session.getId());
            this.partida = ut.donaDarreraPartida(sala);

            if(this.debug){
                System.out.println("Usuari creat: " + this.usuari.getNom());
                System.out.println("Sala: " + idSala);
                System.out.println("Partida: " + this.partida.getnPartida());
            }            
            //Usuari creat i assignat a la sala i partida
            pintaJsp(request, response);
        }

        /**
         * Si ens ens arriba que hem de taxar un número, hem de collir l'usuari
         * collir els seus cartons i taxar el numero que toca Un cop taxat,
         * haurem de revisar si teni linea, bingo o si ens apliquen els powerups
         */
        if (request.getParameterMap().containsKey("numero")) {
            String numero = request.getParameter("numero");
            this.usuari = ut.donaUsuariSala(this.bingo, idSala, session.getId());
            //Llevam un al numero de la partida, ja que l'usuari guarda el SIZE de partides i no el número de la partida
            this.partida = sala.getPartides().get(this.usuari.getnPartida() - 1);
            //Si es número de sa partida és sa mateixa que en la que estam 
            if (this.usuari.getnPartida() == this.sala.getPartides().size()) {
                //Si estam en un bingo cantat
                if (this.partida.isBingo()) {

                    pintaSenseComprovarJsp(request, response);
                } else {
                    int num;
                    if (numero.equals("")) {
                        num = 0;
                    } else {
                        num = Integer.parseInt(numero);
                    }
                    /**
                     * Seleccionam l'usuari al que hem de taxar els cartons
                     */
                    //this.usuari = ut.donaUsuariSala(this.bingo, idSala, session.getId());
                    /**
                     * Taxam els numneros dels cartons de l'usuari
                     */
                    this.usuari.taxaNumeros(num, this.partida.getBolles());

                    pintaJsp(request, response);
                }
            } //Si ja hi ha bingo, no hem de deixar que es faci res
            else {
                //Hem de dir que ha reiniciar ja que hi ha una altra partida en marxa
                System.out.println("Ha de reiniciar ja que hi ha una altra partida en marxa");
                pintaSenseComprovarJsp(request, response);
            }
        }
    }

    public void pintaJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utils ut = new Utils();

        /**
         * Comprovam si hem de verificar els cartons
         */
        if ((sala.isEasyOn()) && partida.getBolles().size() > 0) {
            ut.revisaCartons(this.usuari.getCartons(), partida);

        }

        if (!this.partida.isLinea()) {
            if (this.usuari.comprovaLinea()) {
                this.partida.setLinea(true);
                String missatgeTmp = this.usuari.getNom() + " ha cantat linea!\r";
                this.partida.setMissatgesLog(this.partida.getMissatgesLog() + missatgeTmp);
                this.partida.afegirMissatgesEvent(missatgeTmp, "1");
                this.usuari.setLinies(this.usuari.getLinies()+1);
            }
        }

        /**
         * Si no s'ha cantat bingo, comprovam si en té l'usuari
         */
        if (!this.partida.isBingo()) {
            if (this.usuari.comprovaBingo()) {
                this.partida.setBingo(true);
                String missatgeTmp = this.usuari.getNom() + " ha cantat bingo!\r";
                this.partida.setMissatgesLog(this.partida.getMissatgesLog() + missatgeTmp);
                this.partida.afegirMissatgesEvent(missatgeTmp, "2");
                this.usuari.setBingos(this.usuari.getBingos()+1);
            }
        }
        /**
         * Hem de comprovar si s'han de mostrar missatges d'events
         */
        int nMissatgesEventPartida = this.partida.getNumeroEvents();
        int nMissatgesEventUsuari = this.usuari.getDarrerMissatgeVist();
        int nMissatgesEventInici = nMissatgesEventUsuari;
        this.usuari.setPintarEvent(false);
        if (nMissatgesEventUsuari < nMissatgesEventPartida) {
            System.out.println("Hi ha missatges per pintar");
            this.usuari.setPintarEvent(true);
            this.usuari.setDarrerMissatgeVist(nMissatgesEventPartida);
        }
        /**
         * Comprovam si hem de canviar els powerups
         */
        String avisPwrUp = "";
        if (this.partida.getnPowerUp() > this.usuari.getnPowerUp()) {
            this.usuari.setCollirPwUp(true);
            this.usuari.setnPowerUp(this.partida.getnPowerUp());
            avisPwrUp = "success";
        }
        if ((this.partida.getNumeroBolles() + 1) % this.partida.getFrequenciaPowerups() == 0) {
            avisPwrUp = "danger";
        }
        if ((this.partida.getNumeroBolles() + 2) % this.partida.getFrequenciaPowerups() == 0) {
            avisPwrUp = "warning";
        }
        if (this.usuari.isCollirPwUp()) {
            this.usuari.getPwup().donaPowerUp();
            this.usuari.setCollirPwUp(false);
        }

        /**
         * Finalment, comprovam que no hem sigut atacats per deshabilitar tots
         * els modificadors d'atac
         */
        if (!this.usuari.isPintarEvent()) {
            this.usuari.setAtacPlatan(false);
        }

        getServletContext().setAttribute("bingo", this.bingo);
        //Enviam les dades al jsp
        request.setAttribute("avisPwrUp", avisPwrUp);
        request.setAttribute("idSala", sala.getId());
        request.setAttribute("sala", sala);
        request.setAttribute("partida", this.partida);
        request.setAttribute("usuari", this.usuari);
        request.setAttribute("estrella", this.partida.comprovaDarrerNumero(this.partida.getUsuaris()));
        request.setAttribute("missatgesEventFinal", nMissatgesEventPartida);
        request.setAttribute("missatgesEventInici", nMissatgesEventInici);
        request.setAttribute("jugadors", this.partida.getUsuaris().size());
        request.getRequestDispatcher("cartons.jsp").forward(request, response);
    }

    public void pintaSenseComprovarJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Així eliminam el powerup i no el pot tornar a tirar
        this.usuari.getPwup().setNom("FLASH");
        //No hem de colorejar el temsp que queda de poweup
        String avisPwrUp = "";
        //Hem de mostrar el missatge del bingo sempre
        this.usuari.setPintarEvent(true);
        //Hem de veure si hem arribat aquí perque ha de reiniciar o perque ha de dir que estam en bingo
        int nMissatgesEventPartida = this.partida.getNumeroEvents();
        this.usuari.setDarrerMissatgeVist(nMissatgesEventPartida - 1);
        int nMissatgesEventInici = this.usuari.getDarrerMissatgeVist();
        System.out.println("Numero de missatges: " + nMissatgesEventPartida);
        System.out.println("Primer missatge no vist: " + nMissatgesEventInici);

        request.setAttribute("avisPwrUp", avisPwrUp);
        request.setAttribute("idSala", sala.getId());
        request.setAttribute("sala", sala);
        request.setAttribute("partida", this.partida);
        request.setAttribute("usuari", this.usuari);
        //Posam que no hi ha ningú amb 1 sol número per acabar
        request.setAttribute("estrella", false);
        request.setAttribute("missatgesEventFinal", nMissatgesEventPartida);
        request.setAttribute("missatgesEventInici", nMissatgesEventInici);
        request.setAttribute("jugadors", this.partida.getUsuaris().size());
        request.getRequestDispatcher("cartons.jsp").forward(request, response);
    }
}
