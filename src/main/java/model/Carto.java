package model;

import java.util.Arrays;
import java.util.Random;

public class Carto {

    //Tenim 3 linies que són un array multidimensional de sencers i un valor més que indica l'estat del núemro:
    //-1: buid, 0: sense taxar, 1: taxat, 2: linea, 3: bingo, 4: modificada
    private int[][][] linies = new int[3][9][2];
    //Indica si aquesta linea ha sigut cantada o no
    private int[] linea = new int[3];
    //Indica si hi ha hagut bingo al carto
    private boolean bingo;
    //Indica el maxim valor de??? REVISAR
    private int maxValor;
    //Serveix per saber quants de números hem taxat/ens falten per taxar
    private int numeros;

    public Carto() {
        int i;
        int j;
        for (j = 0; j < 3; j++) {
            this.linea[j] = 0;
            for (i = 0; i < 9; i++) {
                //Posam el valor del cartó a -1
                this.linies[j][i][0] = -1;
                //Posam el valor de l'estat a buid
                this.linies[j][i][1] = -1;
            }
        }
        //Podem canviar el màxim del valor del bingo aquí
        this.maxValor = 90;
        this.bingo = false;
        this.numeros = 0;
    }

    public int[][][] getLinies() {
        return linies;
    }

    public void setLinies(int[][][] linies) {
        this.linies = linies;
    }

    public int getMaxValor() {
        return maxValor;
    }

    public void setMaxValor(int maxValor) {
        this.maxValor = maxValor;
    }

    public int[] getLinea() {
        return linea;
    }

    public void setLinea(int[] linea) {
        this.linea = linea;
    }

    public boolean isBingo() {
        return bingo;
    }

    public void setBingo(boolean bingo) {
        this.bingo = bingo;
    }

    public int getNumeros() {
        return numeros;
    }

    public void setNumeros(int numeros) {
        this.numeros = numeros;
    }

    @Override
    public String toString() {
        String sortida = "";
        int i;
        int j;
        for (j = 0; j < 3; j++) {
            for (i = 0; i < 9; i++) {
                sortida = sortida + this.linies[j][i][0] + "/" + this.linies[j][i][1] + "-";
            }
            sortida = sortida + "***";
        }
        return "Carto{" + "linies=" + sortida + '}';
    }

    public void generaCarto() {
        int i;
        int j;
        int[][] vectors = new int[9][3];

        //1.Generar els 9 vectors
        for (i = 0; i < 9; i++) {
            vectors[i] = crearVector(3, 10, i);
            Arrays.sort(vectors[i]);
        }

        //2.Assignar-los de forma atzarosa dins el cartó 
        for (i = 0; i < 3; i++) {
            //Cercam els 5 que han d'estar descubert
            //Per això collim 9 números, els mesclam i collim els 5 primers
            int[] mascaraFila = {0, 1, 2, 3, 4, 5, 6, 7, 8};
            mascaraFila = mesclarPoscionsX(mascaraFila);
            for (j = 0; j < 5; j++) {
                this.linies[i][mascaraFila[j]][0] = vectors[mascaraFila[j]][i];
                //Posam que la linea se pot veure
                this.linies[i][mascaraFila[j]][1] = 0;
                
            }
        }
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

        valor = randomValor(columna, valorMaxim);
        //Posam el valor a l'atzar comprovant que no està repetit
        for (i = 0; i < longitud; i++) {
            repeteix = true;
            while (repeteix) {
                repeteix = false;
                for (j = 0; j < i; j++) {
                    //Miram tots els valors d'aquesta linea
                    //Si es valor ja ha sortit
                    if (vector[j] == valor) {
                        repeteix = true;
                        valor = randomValor(columna, valorMaxim);
                    }
                }
                //Si el primer número que ha sortit ha sigut un 0, el bucle j no l'ha tingut en compte, per tant, ho hem de tractar a part
                if (valor == 0) {
                    repeteix = true;
                    valor = randomValor(columna, valorMaxim);
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

    public void taxaNumero(int numero) {
        int i;
        int j;
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 3; j++) {
                if (this.linies[j][i][0] == numero) {
                    //Per taxar el número, ho indicam a la columna[1] amb un 1
                    this.linies[j][i][1] = 1;
                    this.numeros++;
                }
            }
        }
    }

    public boolean esLinea() {
        int i;
        int j;
        int aux;
        boolean linea = false;
        //Revisam les 3 files
        for (i = 0; i < 3; i++) {
            aux = 0;
            //Si aquesta fila no ha sigut cantada
            if (this.linea[i] == 0) {
                for (j = 0; j < 9; j++) {
                    if (this.linies[i][j][1] == 1) {
                        aux++;
                    }
                }
                if (aux == 5) {
                    //Linea. Indicam al valor [1] = 2
                    for (j = 0; j < 9; j++) {
                        //Hem de fer aquest if, per no posar a 2 tots els valors (pensem que hi ha caselles sense numero
                        if (this.linies[i][j][1] == 1) {
                            this.linies[i][j][1] = 2;
                        }
                    }
                    this.linea[i] = 1;
                    linea = true;
                }
            }
        }
        return linea;
    }

    public boolean esBingo() {
        //Comprovam si tenim 15 numeros tapats
        boolean bingo = false;
        if (this.numeros == 15) {
            bingo = true;
            marcaBingo();
        }
        
        return bingo;
    }

    public void marcaBingo() {
        int i;
        int j;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 9; j++) {
                //Hem de tenir en compte que hi ha números que no surten
                if (this.linies[i][j][1] != -1) {
                    this.linies[i][j][1] = 3;
                }
            }
        }
    }

    public int randomValor(int columna, int valorMaxim) {
        int valor;
        if (columna == 8) {
            //Si estam en la darrera columna, hem d'arribar fins a 10 en comptes de fins a 9 
            //Per contemplar el cas del 90
            valor = (int) Math.floor(Math.random() * (valorMaxim + 1)) + (columna * 10);
        } else {
            valor = (int) Math.floor(Math.random() * (valorMaxim)) + (columna * 10);
        }
        return valor;
    }

    public void assignaValor(int i, int j, int valor, int estat) {
        this.linies[i][j][0] = valor;
        this.linies[i][j][1] = estat;
    }
}
