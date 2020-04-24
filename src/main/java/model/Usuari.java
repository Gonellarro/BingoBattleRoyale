package model;

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

    public Usuari() {
        this.nom = "Innombrable";
        this.avatar = "face13";
        this.linea = false;
        this.bingo = false;
        this.linies = 0;
        this.bingos = 0;
        this.pintarEvent = false;
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
}
