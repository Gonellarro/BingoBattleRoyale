package model;

public class PowerUp {

    private String nom;
    private int probabilitat;
    private static final int NUM_PWUPS = 4;

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
public void donaPowerUp(){
    /** Numero de powerups: 1.Bomba, 2.Escut, 3.Platan, 4.Canvi*/
    int powerupNumero = (int) Math.floor(Math.random()*NUM_PWUPS);
    switch(powerupNumero+1){
        case 1:
            this.nom="bomba";
            break;
        case 2:
            this.nom="escut";
            break;
        case 3:
            this.nom="platan";
            break;
        case 4:
            this.nom="canvi";
            break;
        default:
            break;
    }
}
}
