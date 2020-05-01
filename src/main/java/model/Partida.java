package model;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    private boolean bingo;
    private boolean linea;
    private boolean atac;
    private int cartons;
    private int idPartida;
    private String titol;
    private List<Bolla> bolles = new ArrayList();
    private List<Bolla> tresBolles = new ArrayList();
    private List<Usuari> usuaris = new ArrayList();
    private String missatgesLog;
    private String missatgesEvents;
    private boolean estrella;
    private boolean canvi;
    private int bombaP;
    private int escutP;
    private int escutRebotP;
    private int canviP;
    private int platanP;
    private int numero;

    public Partida() {
        this.bingo = false;
        this.linea = false;
        this.cartons = 0;
        this.titol = "Orfe";
        this.bolles.clear();
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
        this.missatgesEvents = "";
        this.missatgesLog = "";
        this.atac = false;
        this.estrella = false;
        this.canvi = false;
        this.numero = 0;
        //Fixam les probabilitats de tenir bommba, escut o escut de rebot. La resta, és no tenir res
        //El número va de 0 fins 99, essent la probabilitat sobre 100
        this.bombaP = 25;
        this.escutP = 0;
        this.escutRebotP = 25;
        this.canviP = 25;
        this.platanP = 25;
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

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titiol) {
        this.titol = titiol;
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

    public String getMissatgesEvents() {
        return missatgesEvents;
    }

    public void setMissatgesEvents(String missatgesEvents) {
        this.missatgesEvents = missatgesEvents;
    }

    public boolean isAtac() {
        return atac;
    }

    public void setAtac(boolean atac) {
        this.atac = atac;
    }
    
    public boolean isEstrella() {
        return estrella;
    }

    public void setEstrella(boolean estrella) {
        this.estrella = estrella;
    }

    public int getNumero() {
        return this.bolles.size();
    }    
    
        public int getBombaP() {
        return bombaP;
    }

    public void setBombaP(int bombaP) {
        this.bombaP = bombaP;
    }

    public int getEscutP() {
        return escutP;
    }

    public void setEscutP(int escutP) {
        this.escutP = escutP;
    }

    public int getEscutRebotP() {
        return escutRebotP;
    }

    public void setEscutRebotP(int escutRebotP) {
        this.escutRebotP = escutRebotP;
    }
    
    public int getCanviP() {
        return canviP;
    }

    public void setCanviP(int canviP) {
        this.canviP = canviP;
    }   
    
    public boolean isCanvi() {
        return canvi;
    }

    public void setCanvi(boolean canvi) {
        this.canvi = canvi;
    }    
    
    @Override
    public String toString() {
        return "Partida{" + "bingo=" + bingo + ", linea=" + linea + ", cartons=" + cartons + '}';
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

    public void reiniciar() {
        this.bingo = false;
        this.linea = false;
        this.bolles.clear();
        this.tresBolles.clear();
        //Hem de posar les 3 bolles a 0
        //per dur el comptador de les darreres bolles
        Bolla bolla = new Bolla();
        bolla.setValor(-1);
        this.tresBolles.add(bolla);
        this.tresBolles.add(bolla);
        this.tresBolles.add(bolla);
        this.missatgesEvents = "";
        this.missatgesLog = "";
        this.atac = false;
        this.numero = 0;
        this.estrella=false;
        this.canvi = false;
    }




}
