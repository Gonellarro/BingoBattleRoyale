package model;

import java.util.ArrayList;
import java.util.List;

public class Parrilla {

    private List<Bolla> bombo = new ArrayList();
    private static final int NUMBOLLES = 90;
    private int comptador;

    public Parrilla() {
        int i;
        for (i = 0; i < this.NUMBOLLES; i++) {
            Bolla bolla = new Bolla();
            bolla.setValor(i);
            bolla.setSortit(false);
            bolla.setColor("primary");
            this.bombo.add(bolla);
            this.comptador = 0;
        }
    }

    public List<Bolla> getBombo() {
        return bombo;
    }

    public void setBombo(List<Bolla> bombo) {
        this.bombo = bombo;
    }

    public int getComptador() {
        return comptador;
    }

    public void setComptador(int comptador) {
        this.comptador = comptador;
    }
    
    public int getNUMBOLLES(){
        return this.NUMBOLLES;
    }

    public Bolla treureBolla() {
        boolean haSortit = true;
        Bolla bolla = new Bolla();

        int valor = (int) Math.floor(Math.random() * this.NUMBOLLES);
        while (haSortit) {
            if (this.bombo.get(valor).isSortit()) {
                valor = (int) Math.floor(Math.random() * this.NUMBOLLES);
            } else {
                haSortit = false;
                this.bombo.get(valor).setSortit(true);
                bolla = this.bombo.get(valor);
            }
        }
        this.comptador++;
        return bolla;
    }
    
    public void insertarBolla(Bolla bolla){
        this.bombo.get(bolla.getValor()).setSortit(true);
        this.comptador++;
    }

}
