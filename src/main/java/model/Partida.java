package model;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    //Definim si hi ha hagut bingo i linea
    private boolean bingo;
    private boolean linea;
    //Definim quants cartons hi ha a la partida
    private int cartons;
    //Definim totes les bolles que han sortit
    private List<Bolla> bolles = new ArrayList();
    //Definim un petit buffer de les 3 darreres bolles que han sortit
    private List<Bolla> tresBolles = new ArrayList();
    //Definim els usuaris de la partida
    private List<Usuari> usuaris = new ArrayList();
    //Definim els missatges que surten tant al log com als events momentanis
    private String missatgesLog;
    /**
     * Tipus d'events: 1. Linea, 2. Bingo, 3. Atac bomba/platan, 4. Info
     */
    private String[][] missatgesEvent = new String[50][2];
    private int numeroEvents;
    //Cada partida ha de tenir una parrilla associada
    private Parrilla parrilla;
    //Usuari que ha cantat el bingo (a nivell estadístiques)
    private Usuari usuariBingo;
    //Usuari que ha cantat linea (a nivell estadístiques)
    private Usuari usuariLinea;
    //Numero de bolles que han sortit
    private int numeroBolles;
    //Numero que indica cada quantes bolles hem de regenerar els powerups
    private int frequenciaPowerups;
    //Ve de la sala
    private boolean easyOn;
    private boolean battleRoyale;
    //Numero de la partida
    private int nPartida;

    public Partida() {
        this.bingo = false;
        this.linea = false;
        this.cartons = 0;
        //Inicialitzam les bolles
        this.bolles.clear();
        //Inicialitzam els usuaris
        this.usuaris.clear();
        //Hem de posar les 3 bolles a 0
        //per dur el comptador de les darreres bolles
        Bolla bolla = new Bolla();
        bolla.setValor(-1);
        this.tresBolles.clear();
        this.tresBolles.add(bolla);
        this.tresBolles.add(bolla);
        this.tresBolles.add(bolla);
        //Inicialitzam els usuaris a 0
        this.usuaris.clear();
        //Posam els missatges a ""
        this.missatgesLog = "";
        //Inicialitzam la parrilla
        this.parrilla = new Parrilla();
        //Inicialitzam els usuaris que han cantat bingo i linea
        this.usuariBingo = null;
        this.usuariLinea = null;
        //Inicialitzam el número dels events
        this.numeroEvents = 0;
        //Inicialitzam el número de bolles que han sortit
        this.numeroBolles = 0;
        //Inicialitzam a 10 la frequencia de canvi dels PowerUps
        this.frequenciaPowerups = 10;
        this.easyOn = false;
        this.battleRoyale = true;
        this.nPartida = 0;
    }

    public boolean isBingo() {
        return bingo;
    }

    public void setBingo(boolean bingo) {
        this.bingo = bingo;
    }

    public boolean isLinea() {
        return linea;
    }

    public void setLinea(boolean linea) {
        this.linea = linea;
    }

    public int getCartons() {
        return cartons;
    }

    public void setCartons(int cartons) {
        this.cartons = cartons;
    }

    public List<Bolla> getBolles() {
        return bolles;
    }

    public void setBolles(List<Bolla> bolles) {
        this.bolles = bolles;
    }

    public List<Bolla> getTresBolles() {
        return this.tresBolles;
    }

    public List<Usuari> getUsuaris() {
        return usuaris;
    }

    public void setUsuaris(List<Usuari> usuaris) {
        this.usuaris = usuaris;
    }

    public String getMissatgesLog() {
        return missatgesLog;
    }

    public void setMissatgesLog(String missatgesLog) {
        this.missatgesLog = missatgesLog;
    }

    public String[][] getMissatgesEvent() {
        return missatgesEvent;
    }

    public void setMissatgesEvents(String[][] missatgesEvent) {
        this.missatgesEvent = missatgesEvent;
    }

    public Parrilla getParrilla() {
        return parrilla;
    }

    public void setParrilla(Parrilla parrilla) {
        this.parrilla = parrilla;
    }

    public int getNumeroBolles() {
        return this.bolles.size();
    }

    public void setNumeroBolles(int numeroBolles) {
        this.numeroBolles = numeroBolles;
    }

//Metodes  
    public int donaBollesSortit() {
        return this.bolles.size();
    }

    public void afegeixBolla(Bolla bolla) {
        this.bolles.add(bolla);
        this.tresBolles.remove(0);
        this.tresBolles.add(bolla);
    }

    public void afegeixUsuari(Usuari usuari) {
        this.usuaris.add(usuari);
    }

    public void llevaUsuari(Usuari usuari) {
        this.usuaris.remove(usuari);
    }

    public Usuari getUsuariBingo() {
        return usuariBingo;
    }

    public void setUsuariBingo(Usuari usuariBingo) {
        this.usuariBingo = usuariBingo;
    }

    public Usuari getUsuariLinea() {
        return usuariLinea;
    }

    public void setUsuariLinea(Usuari usuariLinea) {
        this.usuariLinea = usuariLinea;
    }

    public int getNumeroEvents() {
        return numeroEvents;
    }

    public void setNumeroEvents(int numeroEvents) {
        this.numeroEvents = numeroEvents;
    }

    public int getFrequenciaPowerups() {
        return frequenciaPowerups;
    }

    public void setFrequenciaPowerups(int frequenciaPowerups) {
        this.frequenciaPowerups = frequenciaPowerups;
    }

    public boolean isEasyOn() {
        return easyOn;
    }

    public void setEasyOn(boolean easyOn) {
        this.easyOn = easyOn;
    }

    public boolean isBattleRoyale() {
        return battleRoyale;
    }

    public void setBattleRoyale(boolean battleRoyale) {
        this.battleRoyale = battleRoyale;
    }
    
    public int getnPartida() {
        return nPartida;
    }

    public void setnPartida(int nPartida) {
        this.nPartida = nPartida;
    }    

    @Override
    public String toString() {
        return "Partida{" + "bingo=" + bingo + ", linea=" + linea + ", cartons=" + cartons + ", tresBolles=" + tresBolles + ", usuaris=" + usuaris + ", missatgesLog=" + missatgesLog + ", parrilla=" + parrilla + '}';
    }

//Mètodes
    public void afegirMissatgesEvent(String missatge, String tipus) {
        if (this.numeroEvents < 50) {
            this.missatgesEvent[this.numeroEvents][0] = missatge;
            this.missatgesEvent[this.numeroEvents][1] = tipus;
            this.numeroEvents++;
        }
    }

    public boolean comprovaDarrerNumero(List<Usuari> usuaris) {
        boolean resultat = false;
        for (Usuari usuariTmp : usuaris) {
            if (usuariTmp.comprovaDarrerNumero()) {
                resultat = true;
            }
        }
        return resultat;
    }
    
    public void copia(Partida partidaFont){
        this.cartons = partidaFont.getCartons();
        this.easyOn = partidaFont.isEasyOn();
        this.battleRoyale = partidaFont.isBattleRoyale();
        this.frequenciaPowerups = partidaFont.getFrequenciaPowerups();
    }



}
