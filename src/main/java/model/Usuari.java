package model;

import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.List;

public class Usuari {

    private String nom;
    private String avatar;
    private String idSession;
    private Partida partida;
    private int linies;
    private int bingos;
    private boolean linea;
    private boolean bingo;
    private boolean pintarEvent;
    private boolean desactivarEvent;
    private String missatgeEvent;
    private int tipusEvent; //0: Res, 1: Linea, 2: Bingo, 3:Atac ok, 4: Rebot, 5: Rebot i atac ok
    private List<Carto> cartons;
    private int bomba;
    private int escut;
    private int escutRebot;
    private int canvi;
    private boolean atac;

    public Usuari() {
        this.nom = "Innombrable";
        this.avatar = "face1";
        this.linea = false;
        this.bingo = false;
        this.linies = 0;
        this.bingos = 0;
        this.pintarEvent = false;
        this.desactivarEvent = false;
        this.bomba = 0;
        this.escut = 0;
        this.escutRebot = 0;
        this.canvi = 0;
        this.missatgeEvent = "";
        this.tipusEvent = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public int getLinies() {
        return linies;
    }

    public void setLinies(int linies) {
        this.linies = linies;
    }

    public int getBingos() {
        return bingos;
    }

    public void setBingos(int bingos) {
        this.bingos = bingos;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public boolean isLinea() {
        return linea;
    }

    public void setLinea(boolean linea) {
        this.linea = linea;
    }

    public boolean isBingo() {
        return bingo;
    }

    public void setBingo(boolean bingo) {
        this.bingo = bingo;
    }

    public boolean isPintarEvent() {
        return pintarEvent;
    }

    public void setPintarEvent(boolean pintarEvent) {
        this.pintarEvent = pintarEvent;
    }

    public List<Carto> getCartons() {
        return cartons;
    }

    public void setCartons(List<Carto> cartons) {
        this.cartons = cartons;
    }

    public boolean isAtac() {
        return atac;
    }

    public void setAtac(boolean atac) {
        this.atac = atac;
    }

    public int getBomba() {
        return bomba;
    }

    public void setBomba(int bomba) {
        this.bomba = bomba;
    }

    public int getEscut() {
        return escut;
    }

    public void setEscut(int escut) {
        this.escut = escut;
    }

    public int getEscutRebot() {
        return escutRebot;
    }

    public void setEscutRebot(int escutRebot) {
        this.escutRebot = escutRebot;
    }

    public String getMissatgeEvent() {
        return missatgeEvent;
    }

    public void setMissatgeEvent(String missatgeEvent) {
        this.missatgeEvent = missatgeEvent;
    }
    
    public int getTipusEvent() {
        return tipusEvent;
    }

    public void setTipusEvent(int tipusEvent) {
        this.tipusEvent = tipusEvent;
    }
    
    public boolean isDesactivarEvent() {
        return desactivarEvent;
    }

    public void setDesactivarEvent(boolean desactivarEvent) {
        this.desactivarEvent = desactivarEvent;
    }
    
    public int getCanvi() {
        return canvi;
    }

    public void setCanvi(int canvi) {
        this.canvi = canvi;
    }    

//Mètodes    
    public void assignaPowerUps(int bombaP, int escutP, int escutRebotP, int canviP) {
        int atzar = (int) Math.floor(Math.random() * 100);
        if (atzar < bombaP) {
            this.bomba = 1;
        }
        if ((bombaP < atzar) && (atzar < bombaP + escutP)) {
            this.escut = 1;
        }
        if ((bombaP + escutP < atzar) && (atzar < bombaP + escutP + escutRebotP)) {
            this.escutRebot = 1;
        }
        if ((bombaP + escutP + escutRebotP < atzar) && (atzar < bombaP + escutP + escutRebotP + canviP)) {
            this.canvi = 1;
        }
    }


}
