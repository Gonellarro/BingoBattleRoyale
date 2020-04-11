package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Carto {

    //Tenim 3 linies que són un array multidimensional de sencers
    private int[][] linies = new int[3][9];
    private int maxValor;

    public Carto() {
        int i;
        int j;
        for (j = 0; j < 3; j++) {
            for (i = 0; i < 9; i++) {
                this.linies[j][i] = 0;
            }
        }
        //Podem canviar el màxim del valor del bingo aquí
        this.maxValor = 90;
    }

    public int[][] getLinies() {
        return linies;
    }

    public void setLinies(int[][] linies) {
        this.linies = linies;
    }

    @Override
    public String toString() {
        String sortida = "";
        int i;
        int j;
        for (j = 0; j < 3; j++) {
            for (i = 0; i < 9; i++) {
                sortida = sortida + this.linies[j][i] + "-";
            }
            sortida = sortida + "***";
        }
        return "Carto{" + "linies=" + sortida + '}';
    }

    public void generaCarto() {
        int i;
        int j;
        int[][] vectors = new int[9][3];
        int[] posicionsX = {1, 1, 1, 2, 2, 2, 2, 2, 2};

        //1.Generar els 9 vectors
        for (i = 0; i < 9; i++) {
            vectors[i] = crearVector(3, 10, i);
            Arrays.sort(vectors[i]);
            int k;
            System.out.println("Vector[" + i + "]:");
            for(k = 0; k< 3; k++){
                System.out.println(vectors[i][k] +" ");
            }
              System.out.println("***");  
        }

        //2.Assignar-los de forma atzarosa dins el cartó 
        posicionsX = mesclarPoscionsX(posicionsX);
        for (i = 0; i < 9; i++) {
            int posicionsY = (int) Math.floor(Math.random() * 2) + 1;
            for (j = 0; j < 3; j++) {
                if (posicionsX[i] == 1) {
                    if (j == posicionsY) {
                        this.linies[j][i] = vectors[i][j];
                    } 
                } else {
                    if (posicionsY == 1) {
                        if ((j == 0) || (j == 1)) {
                            this.linies[j][i] = vectors[i][j];
                        }
                    }
                    if (posicionsY == 2) {
                        if ((j == 0) || (j == 2)) {
                            this.linies[j][i] = vectors[i][j];
                        }
                    }
                    if (posicionsY == 3) {
                        System.out.println("j:" + j);
                        if ((j == 1) || (j == 2)) {
                            this.linies[j][i] = vectors[i][j];
                        }
                    }
                }
            }
        }
        System.out.println("Fi de iterar");

    }

    public int[] crearVector(int longitud, int valorMaxim, int columna) {
        int[] vector = new int[longitud];
        int i;
        int j;
        int valor;
        boolean repeteix = false;

        //Inicialitzam el vector a 0
        for (i = 0; i < longitud; i++) {
            vector[i] = 0;
        }

        //Posam el valor a l'atzar comprovant que no està repetit
        for (i = 0; i < longitud; i++) {
            valor = (int) Math.floor(Math.random() * valorMaxim + 1) + (columna * 10);
            repeteix = true;
            while (repeteix) {
                repeteix = false;
                for (j = 0; j < i; j++) {
                    //Miram tots els valors d'aquesta linea
                    if (vector[j] == valor) {
                        repeteix = true;
                        valor = (int) Math.floor(Math.random() * valorMaxim + 1) + (columna * 10);
                    }
                }
            }
            //Si no s'ha repetit, assignam el valor a la cel·la de la linea en curs
            vector[i] = valor;
        }
        return vector;
    }

    public int[] mesclarPoscionsX(int[] posicions) {
        Random rand = new Random();

        for (int i = 0; i < posicions.length; i++) {
            int randomIndexToSwap = rand.nextInt(posicions.length);
            int temp = posicions[randomIndexToSwap];
            posicions[randomIndexToSwap] = posicions[i];
            posicions[i] = temp;
        }
        return posicions;
    }
    
    public void tachaNumero(int numero){
        int i;
        int j;
        for(i = 0; i < 9; i++){
            for(j = 0; j < 3; j++){
                if (this.linies[j][i] == numero){
                    this.linies[j][i] = numero + 100;
                }
            }
        }
    }

}
