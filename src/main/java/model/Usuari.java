package model;

import java.util.ArrayList;
import java.util.List;

public class Usuari {

    private String idSession;
    private String nom;
    private String avatar;
    private List<Partida> partides = new ArrayList();
    private Partida partida;
    private int linies;
    private int bingos;

    public Usuari() {
        this.nom = "Innombrable";
        this.avatar = "default";
        this.partida = new Partida();
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
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

    public List<Partida> getPartides() {
        return partides;
    }

    public void setPartides(List<Partida> partides) {
        this.partides = partides;
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

    @Override
    public String toString() {
        return "Usuari{" + "nom=" + nom + ", avatar=" + avatar + ", partides=" + partides + '}';
    }

    public void novaPartida() {
        this.partides.add(partida);
        this.partida = new Partida();
    }

    public void insertaBingo() {
        this.partida.setBingo(true);
    }

    public void insertaLinea() {
        this.partida.setLinea(true);
    }

    public void insertaCartons(int cartons) {
        this.partida.setCartons(cartons);
    }

    public void calculaLinies() {
        int suma = 0;
        
        for (Partida partida : this.partides) {
            if (partida.isLinea()) {
                suma++;
            }
        }
        
        if (this.partida.isLinea()){
            suma++;
        }
        
        this.linies = suma;
    }

    public void calculaBingos() {
        int suma = 0;
        for (Partida partida : this.partides) {
            if (partida.isBingo()) {
                suma++;
                System.out.println("SumaB: " +  suma);
            }
        }

        if (this.partida.isBingo()){
            suma++;
        }        
        
        this.bingos = suma;
    }

}
