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
        System.out.println("-------------DOGET------------");
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
            this.sala = ut.donaSalaPerID(idSala, this.bingo.getSales());
            this.partida = ut.donaDarreraPartida(sala);
            
            switch (accio) {
                case "reiniciar":
                    /**
                     * Per reiniciar el que hem de fer és reiniciar els cartons de l'usuari
                     */
                    /**
                     * Collim el número de la sala del parametre sala
                     */

                    this.usuari = ut.donaUsuariSala(this.bingo, idSala, session.getId());
                    this.usuari.iniciaCartons(sala.getNcartons());
                    this.usuari.setDarrerMissatgeVist(0);

                    getServletContext().setAttribute("bingo", this.bingo);
                    //Enviam les dades al jsp
                    request.setAttribute("idSala", idSala);
                    request.setAttribute("sala", sala);
                    request.setAttribute("partida", this.partida);
                    request.setAttribute("usuari", this.usuari);
                    request.setAttribute("estrella", false);
                    request.setAttribute("jugadors", this.partida.getUsuaris().size());
                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
                    break;
                case "graella":
                    Parrilla parrilla = new Parrilla();
                    this.partida = ut.donaDarreraPartida(sala);
                    parrilla.setBomboPartida(this.partida.getBolles());
                    request.setAttribute("parrilla", parrilla);
                    request.setAttribute("partida", this.partida);
                    request.getRequestDispatcher("bingoro.jsp").forward(request, response);
                    break;
                case "estadistiques":
                    this.partida = ut.donaDarreraPartida(sala);
                    request.setAttribute("nusuaris", this.partida.getUsuaris().size());
                    request.setAttribute("usuaris", this.partida.getUsuaris());
                    request.getRequestDispatcher("estadistiques.jsp").forward(request, response);
                    break;
                case "sortir":
                    this.usuari = ut.donaUsuariSala(this.bingo, idSala, session.getId());
                    this.sala.llevaUsuari(this.usuari);
                    this.partida.llevaUsuari(this.usuari);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
//                case "bomba":
//                    Parrilla graella = new Parrilla();
//                    graella.setBomboPartida(this.partida.getBolles());
//                    Battle batalla = new Battle();
//                    this.partida = batalla.bomba(this.partida, session.getId(), graella);
//
//                    //Actualitzam la partida dins la llista de partides
//                    //this.partides.remove(this.partida);
//                    //this.partides.add(this.partida);
//                    getServletContext().setAttribute("partides", this.partides);
//
//                    session.setAttribute("partida", this.partida);
//                    session.setAttribute("usuari", this.usuari);
//                    request.setAttribute("jugadors", this.partida.getUsuaris().size());
//                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
//                    break;
//                case "canvi":
//                    Battle batalla2 = new Battle();
//                    this.partida = batalla2.canvi(this.partida, this.usuari);
//                    this.usuari.setCanvi(0);
//                    //Actualitzam la partida dins la llista de partides
//                    //this.partides.remove(this.partida);
//                    //this.partides.add(this.partida);
//
//                    getServletContext().setAttribute("partides", this.partides);
//                    session.setAttribute("partida", this.partida);
//                    session.setAttribute("usuari", this.usuari);
//                    request.setAttribute("jugadors", this.partida.getUsuaris().size());
//                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
//                    break;
//                case "platan":
//                    Battle batalla3 = new Battle();
//                    this.partida = batalla3.platan(this.partida, this.usuari);
//                    this.usuari.setPlatan(0);
//
//                    //Actualitzam la partida dins la llista de partides
//                    //this.partides.remove(this.partida);
//                    //this.partides.add(this.partida);
//                    getServletContext().setAttribute("partides", this.partides);
//                    session.setAttribute("partida", this.partida);
//                    session.setAttribute("usuari", this.usuari);
//                    request.setAttribute("jugadors", this.partida.getUsuaris().size());
//                    request.getRequestDispatcher("cartons.jsp").forward(request, response);
//                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("------------DOPOST-------------");
        HttpSession session = request.getSession();
        this.bingo = (Bingo) getServletContext().getAttribute("bingo");

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
            int idSala = Integer.parseInt(request.getParameter("idSala"));
            /**
             * Per tractar la gestio de l'usuari, tenim un BEAN: gestio
             * d'usuaris, amb el mètode crearUsuari Aquest mètode ens hauria de
             * retornar l'usuari, de forma que es pugui publicar al jsp dels
             * cartons
             */
            GestioUsuaris gs = new GestioUsuaris();
            this.usuari = gs.crearUsuari(nom, avatar, idSala, this.bingo, session.getId());
            Utils ut = new Utils();
            Sala sala = new Sala();
            sala = ut.donaSalaPerID(idSala, this.bingo.getSales());
            this.partida = ut.donaDarreraPartida(sala);
            //Usuari creat i assignat a la sala i partida

            /**
             * Hem d'enviar el número de sala, per recuperar-lo sempre, ja que
             * un mateix usuari pot estar jugant en varies sales a la vegada
             */
            getServletContext().setAttribute("bingo", this.bingo);
            request.setAttribute("idSala", idSala);
            request.setAttribute("sala", sala);
            request.setAttribute("partida", this.partida);
            request.setAttribute("usuari", this.usuari);
            request.setAttribute("estrella", false);
            request.setAttribute("jugadors", this.partida.getUsuaris().size());
            request.getRequestDispatcher("cartons.jsp").forward(request, response);
        }

        /**
         * Si ens ens arriba que hem de taxar un número, hem de collir l'usuari
         * collir els seus cartons i taxar el numero que toca Un cop taxat,
         * haurem de revisar si teni linea, bingo o si ens apliquen els powerups
         */
        if (request.getParameterMap().containsKey("numero")) {
            String numero = request.getParameter("numero");
            int num;
            if (numero.equals("")) {
                num = 0;
            } else {
                num = Integer.parseInt(numero);
            }
            /**
             * El jsp ens ha de donar de forma HIDDEN el número de la sala en la
             * que estam jugant i obtenim la partida
             */
            int idSala = Integer.parseInt(request.getParameter("idSala"));
            Sala sala = new Sala();
            Utils ut = new Utils();
            sala = ut.donaSalaPerID(idSala, this.bingo.getSales());
            this.partida = ut.donaDarreraPartida(sala);
            /**
             * Seleccionam l'usuari al que hem de taxar els cartons
             */

            this.usuari = ut.donaUsuariSala(this.bingo, idSala, session.getId());
            /**
             * Taxam els numneros dels cartons de l'usuari
             */
            this.usuari.taxaNumeros(num);
            /**
             * Ara hem de comprovar si hi ha linia/bingo per dir-ho com a
             * missatge a tots els usuaris
             */
            /**
             * Si no s'ha cantat linea, comprovam si en té l'usuari
             */

            if (!this.partida.isLinea()) {
                if (this.usuari.comprovaLinea()) {
                    this.partida.setLinea(true);
                    String missatgeTmp = this.usuari.getNom() + " ha cantat linea!\r";
                    this.partida.setMissatgesLog(this.partida.getMissatgesLog() + missatgeTmp);
                    this.partida.afegirMissatgesEvent(missatgeTmp, "1");                  
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
                }
            }
            /**Hem de comprovar si s'han de mostrar missatges d'events*/
            int nMissatgesEventPartida = this.partida.getNumeroEvents();
            int nMissatgesEventUsuari = this.usuari.getDarrerMissatgeVist();
            int nMissatgesEventInici = nMissatgesEventUsuari;
            this.usuari.setPintarEvent(false);
            if (nMissatgesEventUsuari < nMissatgesEventPartida){
                System.out.println("Hi ha missatges per pintar");
                this.usuari.setPintarEvent(true);
                this.usuari.setDarrerMissatgeVist(nMissatgesEventPartida);
            }
            System.out.println("Usuari: " + this.usuari.getNom());
            System.out.println("inicial: " + nMissatgesEventInici);
            System.out.println("final: " + nMissatgesEventPartida);
            
            getServletContext().setAttribute("bingo", this.bingo);
            //Enviam les dades al jsp
            request.setAttribute("idSala", idSala);
            request.setAttribute("sala", sala);
            request.setAttribute("partida", this.partida);
            request.setAttribute("usuari", this.usuari);
            request.setAttribute("estrella", this.partida.comprovaDarrerNumero(sala.getUsuaris()));
            request.setAttribute("missatgesEventFinal", nMissatgesEventPartida);
            request.setAttribute("missatgesEventInici", nMissatgesEventInici);
            request.setAttribute("jugadors", this.partida.getUsuaris().size());
            request.getRequestDispatcher("cartons.jsp").forward(request, response);
        }
    }
}
