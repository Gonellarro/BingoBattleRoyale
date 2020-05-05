package model;

public class PowerUp {

    private String nom;
    private int probabilitat;

    public PowerUp() {
        this.nom = "FLASH";
        this.probabilitat = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getProbabilitat() {
        return probabilitat;
    }

    public void setProbabilitat(int probabilitat) {
        this.probabilitat = probabilitat;
    }

//Metdodes
    public PowerUp bomba() {
        this.nom = "bomba";
        this.probabilitat = 25;
        return this;
    }
    public PowerUp escut() {
        this.nom = "escut";
        this.probabilitat = 25;
        return this;
    }
    public PowerUp platan() {
        this.nom = "platan";
        this.probabilitat = 25;
        return this;
    }
    public PowerUp planta() {
        this.nom = "plamnta";
        this.probabilitat = 25;
        return this;
    }

}
