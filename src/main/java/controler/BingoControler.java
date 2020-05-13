package controler;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Bingo;
import model.Bolla;
import model.Parrilla;
import model.Partida;
import model.Sala;
import Beans.Utils;
import java.util.ArrayList;
import java.util.List;
import model.Usuari;

@WebServlet("/BingoControler")
public class BingoControler extends HttpServlet {

    private Bingo bingo;
    private boolean debug;

    @Override
    public void init(ServletConfig config) throws ServletException {

        // Store the ServletConfig object and log the initialization
        super.init(config);
        //Hem de crear el Bingo el primer pic que entram
        this.bingo = new Bingo();
        //Si no existeix com a variable d'aplicació, el cream
        if (getServletContext().getAttribute("bingo") == null) {
            //Cream el bingo
            this.bingo.setNom("SalvadorDali25");
            //Cream una variable global a l'app per a que tengui totes les dades de la partida compartides
            getServletContext().setAttribute("bingo", this.bingo);
        } //Sino, la carregam
        else {
            this.bingo = (Bingo) getServletContext().getAttribute("bingo");
        }
        //Si volem debugar el programa ho hem de posar a true
        this.debug = false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (this.debug) {
            System.out.println("BINGO CONTROLER - DOGET");
        }
        HttpSession session = request.getSession();
        Bolla bolla = new Bolla();
        //Cream una sala local
        Sala sala = new Sala();
        //Cream una partida local
        Partida partida = new Partida();
        //Cream una parrilla local
        Parrilla parrilla = new Parrilla();
        //Cream una utileria
        Utils util = new Utils();

        if (request.getParameterMap().containsKey("accio")) {
            String accio = request.getParameter("accio");
            int i;
            switch (accio) {
                case "iniciar":
                    //Hem de demanar el nom de la sala i els cartons que hi haurà a cada partida, per crear-la després
                    request.getRequestDispatcher("bingo_menu.jsp").forward(request, response);
                    break;
                case "reiniciar":
                    //Reiniciar vol dir que volem crear una nova partida dins la sala
                    sala = util.donaSala(session.getId(), this.bingo.getSales());
                    //Hem de crear una nova partida a aquesta sala 
                    //Collim les dades necessaries de la partida anterior
                    Partida partidaAnt = new Partida();
                    partidaAnt = util.donaDarreraPartida(sala);
                    //Posam un missatge a la partida anterior per a què els usuaris que encara hi estiguin canviïn
                    partidaAnt.afegirMissatgesEvent("Has de reiniciar la partda", "3");
                    partidaAnt.setMissatgesLog(partidaAnt.getMissatgesLog()+"Has de reiniciar la partda\r");
                    //Reiniciam la partida amb les dades de l'anterior
                    partida.copia(partidaAnt);
                    //Aumentam en un el número de la partida
                    partida.setnPartida(partidaAnt.getnPartida()+1);
                    sala.afegiexPartida(partida);

                    //Carregam la graella de la partida acabada de crear
                    parrilla = partida.getParrilla();
                    //Posam la bolla a 0
                    bolla.setValor(-1);

                    //Actualitzam la variable global del bingo. El servlet la coneix, però l'ha de coneixer també el servlet del jugadors
                    getServletContext().setAttribute("bingo", bingo);

                    request.setAttribute("sala", sala);
                    request.setAttribute("parrilla", parrilla);
                    request.setAttribute("bollaActual", bolla);
                    request.getRequestDispatcher("bingo.jsp").forward(request, response);
                    break;
                case "sortir":
                    //Vol dir que hem de llevar la sala on estam del bingo i anar a l'index
                    sala = util.donaSala(session.getId(), this.bingo.getSales());
                    //Hem d'eliminar aquesta sala
                    this.bingo.llevaSala(sala);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                default:
                    break;
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (this.debug) {
            System.out.println("BINGO CONTROLER - DOPOST");
        }

        HttpSession session = request.getSession();
        Bolla bolla = new Bolla();
        //Cream una sala local
        Sala sala = new Sala();
        //Cream una partida local
        Partida partida = new Partida();
        //Cream una parrilla local
        Parrilla parrilla = new Parrilla();

        //Si rebem el titol vol dir que hem d'iniciar una nova sala. 
        //Hem de crear la partida. Per això demanam titol i cartons.
        if (request.getParameterMap().containsKey("titol")) {
            //Hem de posar en marxa de la sala, nova sala i nova partida
            String titol = request.getParameter("titol");
            int cartons = Integer.parseInt(request.getParameter("cartons"));
            String easyon = request.getParameter("easyon");
            String battle = request.getParameter("battle");
            int fpowerups = Integer.parseInt(request.getParameter("powerups"));
            //Formatejam strings que no han de ser null
            if (easyon == null){
                easyon = "";
            }
            if (battle  == null){
                battle = "";
            }

            //Generam un ID per compartir amb els jugadors
            sala.generaID();
            //Guardam la sessio per identificar la sala cada pic
            sala.setIdSession(session.getId());
            //Li posam el nom i els cartons a la sala
            sala.setNom(titol);
            sala.setNcartons(cartons);
            if (easyon.equals("on")) {
                sala.setEasyOn(true);
            }
            else{
               sala.setEasyOn(false); 
            }
            if (battle.equals("on")) {
                sala.setBattleRoyale(true);
            }
            else{
                sala.setBattleRoyale(false);
            }
            //Indicam a la partida el numero de cartosn
            partida.setCartons(cartons);
            partida.setFrequenciaPowerups(fpowerups);
            //Afegim la partida a la sala
            sala.afegiexPartida(partida);
            //Afegim la sala al bingo
            this.bingo.afegeixSala(sala);
            //Inicialitzam la primera bolla que surt a 0
            bolla.setValor(-1);
            //Debug
            if (this.debug) {
                System.out.println("Entra a crear la sala i partida");
                System.out.println("Sala: " + sala);
                System.out.println("Partida: " + partida);
            }
        }
        //Si rebem accio=bolla, vol dir que hem de treure una nova bolla del bombo
        //Hem de donar una nova bolla
        if (request.getParameterMap().containsKey("accio")) {
            String accio = request.getParameter("accio");
            if (accio.equals("bolla")) {
                //Si encara se poden treure més bolles, collim la sala i parrilla
                Utils util = new Utils();
                sala = util.donaSala(session.getId(), this.bingo.getSales());
                partida = util.donaDarreraPartida(sala);
                parrilla = partida.getParrilla();
                //Si queden bolles per treure
                if (parrilla.getComptador() < parrilla.getNUMBOLLES()) {
                    //treim una bolla i la posam a la parrilla;
                    bolla = parrilla.treureBolla();
                    //També l'afegim a la partida per dur un compte de les bolles que han sortit
                    partida.afegeixBolla(bolla);

                    if (this.debug) {
                        System.out.println("Hem de donar una bolla");
                        System.out.println("Sala: " + sala);
                        System.out.println("Partida: " + partida);
                        System.out.println("Parrilla: " + parrilla);
                        System.out.println("Bolla: " + bolla);
                    }

                } else {
                    //TODO
                    //HEM DE DIR QUE HEM ACABAT
                    //PER ARA NOMÉS TREIM UNA BOLLA A 0
                    bolla.setValor(-1);
                    if (this.debug) {
                        System.out.println("HEM ARRIBAT AL FINAL DE LES BOLLES DEL BOMBO");
                    }
                }
            }
        }
        //Actualitzam la variable global del bingo. El servlet la coneix, però l'ha de coneixer també el servlet del jugadors
        getServletContext().setAttribute("bingo", bingo);
        //Publicam tot pel jsp
        //request.setAttribute("partida", partida);
        request.setAttribute("sala", sala);
        request.setAttribute("parrilla", parrilla);
        request.setAttribute("bollaActual", bolla);
        request.getRequestDispatcher("bingo.jsp").forward(request, response);
    }
}
