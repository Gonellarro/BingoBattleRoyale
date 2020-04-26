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
    private List<Carto> cartons;
    private String perfil;
    private List<String> perfils = asList("atacant", "defensor", "defensorp", "mag", "normal");
    private int aProb;
    private int dProb;
    private int dpProb;
    private int mProb;
    private int nProb;
    private boolean atac;

    public Usuari() {
        this.nom = "Innombrable";
        this.avatar = "face13";
        this.linea = false;
        this.bingo = false;
        this.linies = 0;
        this.bingos = 0;
        this.pintarEvent = false;
        this.perfil = "normal";
        this.aProb = 90;
        this.dProb = 0;
        this.dpProb = 0;
        this.mProb = 0;
        this.nProb = 100 - (aProb + dProb + dpProb + mProb);
        this.atac = false;
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

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public List<String> getPerfils() {
        return perfils;
    }

    public void setPerfils(List<String> perfils) {
        this.perfils = perfils;
    }

    public int getaProb() {
        return aProb;
    }

    public void setaProb(int aProb) {
        this.aProb = aProb;
    }

    public int getdProb() {
        return dProb;
    }

    public void setdProb(int dProb) {
        this.dProb = dProb;
    }

    public int getDpProb() {
        return dpProb;
    }

    public void setDpProb(int dpProb) {
        this.dpProb = dpProb;
    }

    public int getmProb() {
        return mProb;
    }

    public void setmProb(int mProb) {
        this.mProb = mProb;
    }

    public int getnProb() {
        return nProb;
    }

    public void setnProb(int nProb) {
        this.nProb = nProb;
    }

    public boolean isAtac() {
        return atac;
    }

    public void setAtac(boolean atac) {
        this.atac = atac;
    }

//Mètodes    
    public void assignaPerfil() {
        //Per defecte tindrem el perfil normal
        this.perfil = this.perfils.get(4);
        int valor;
        //Triam a l'atzar un numero de 0 a 99
        valor = (int) Math.floor(Math.random() * 100);
        //Ara assignam el valor en funcio de les probabilitats de cada perfil
        //Atacant
        if ((0 < valor) && (valor < aProb)) {
            this.perfil = this.perfils.get(0);
        }
        //Defensor
        if ((aProb < valor) && (valor < dProb + aProb)) {
            this.perfil = this.perfils.get(1);
        }
        //DefensorPlus
        if ((dProb + aProb < valor) && (valor < dpProb + dProb + aProb)) {
            this.perfil = this.perfils.get(2);
        }
        //Mag
        if ((dpProb + dProb + aProb < valor) && (valor < mProb + dpProb + dProb + aProb)) {
            this.perfil = this.perfils.get(3);
        }
        //Atancant
        if (this.nom.equals("atacant")) {
            this.perfil = this.perfils.get(0);
        }
        //Defensor
        if (this.nom.equals("defensor")) {
            this.perfil = this.perfils.get(1);
        }
        //Defensor +
        if (this.nom.equals("defensorp")) {
            this.perfil = this.perfils.get(2);
        }
    }

}
