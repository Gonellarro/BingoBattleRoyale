package model;

import java.util.ArrayList;
import java.util.List;

public class Bingo {

    private String nom;
    private List<Sala> sales = new ArrayList();

    public Bingo() {
        this.nom = "Rosales";
        this.sales.clear();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Sala> getSales() {
        return sales;
    }

    public void setSales(List<Sala> sales) {
        this.sales = sales;
    }

//Metodes
    public boolean afegeixSala(Sala sala) {
        boolean resultat;
        resultat = this.sales.add(sala);
        return resultat;
    }
    
    public boolean llevaSala(Sala sala){
        boolean resultat;
        resultat = this.sales.remove(sala);
        return resultat;        
    }
}
