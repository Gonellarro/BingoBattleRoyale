package model;

import java.util.ArrayList;
import java.util.List;

public class Sala {

    //Nom de la sala
    private String nom;
    //Identificador de la sala
    private int id;
    //Identificador de la sessió del creador de la sala
    private String idSession;
    //Usuaris pertanyets a la sala
    private List<Usuari> usuaris = new ArrayList();
    //Partides creades a la sala
    private List<Partida> partides = new ArrayList();
    //Contrasenya per entrar a la sala
    private String contrasenya;
    //Numero de cartos amb els que se juga en aquesta sala
    private int ncartons;
    //Tipus de partida Easy o no
    private boolean easyOn;
    //Tipus de partida Battle o no
    private boolean battleRoyale;

    public Sala() {
        this.nom = "Razzmataz";
        this.usuaris.clear();
        this.partides.clear();
        this.contrasenya = "";
        this.id = 0;
        this.idSession = "";
        this.ncartons = 0;
        this.easyOn = false;
        this.battleRoyale = true;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public List<Usuari> getUsuaris() {
        return usuaris;
    }

    public void setUsuaris(List<Usuari> usuaris) {
        this.usuaris = usuaris;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNcartons() {
        return ncartons;
    }

    public void setNcartons(int ncartons) {
        this.ncartons = ncartons;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public List<Partida> getPartides() {
        return partides;
    }

    public void setPartides(List<Partida> partides) {
        this.partides = partides;
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

//Metodes
    public int generaID() {
        this.id = (int) Math.floor(Math.random() * (99999));
        return this.id;

    }

    public boolean afegeixUsuari(Usuari usuari) {
        return this.usuaris.add(usuari);
    }

    public boolean llevaUsuari(Usuari usuari) {
        return this.usuaris.remove(usuari);
    }

    public boolean afegiexPartida(Partida partida) {
        return this.partides.add(partida);
    }

    @Override
    public String toString() {
        return "Sala{" + "nom=" + nom + ", id=" + id + ", usuaris=" + usuaris + ", contrasenya=" + contrasenya + ", ncartons=" + ncartons + '}';
    }



}
