package model;

public class Usuari {

    private String nom;
    private String avatar;
    private String idSession;
    private Partida partida;
    private int linies;
    private int bingos;
    private boolean linea;
    private boolean linea2;
    private boolean bingo;

    public Usuari() {
        this.nom = "Innombrable";
        this.avatar = "default";
        this.linea = false;
        this.linea2 = false;
        this.bingo = false;
        this.linies = 0;
        this.bingos = 0;
        
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

    public boolean isLinea2() {
        return linea2;
    }

    public void setLinea2(boolean linea2) {
        this.linea2 = linea2;
    }
}
