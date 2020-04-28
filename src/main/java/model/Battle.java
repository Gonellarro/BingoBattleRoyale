package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Battle {

    public Partida bomba(Partida partida, String idSession, Parrilla graella) {
        //Primer, hem de veure quin usuari li falta nomes una bolla
        Usuari usuariVictima = new Usuari();
        usuariVictima = cercaVictima(idSession, partida);
        Usuari usuariAtacant = new Usuari();
        usuariAtacant = consultaUsuari(idSession, partida);

        //Un cop ja el tenim hem de veure si la victima té escut i de quin tipus
        String missatge = "";
        boolean ferit = true;
        boolean torna = false;
        int tipusEvent = 3;//0: Res, 1: Linea, 2: Bingo, 3:Atac ok, 4: Rebot, 5: Rebot i atac ok
        //Si la víctima tés escut, l'empara i perd 1 escut
        if (usuariVictima.getEscut() > 0) {
            usuariVictima.setEscut(usuariVictima.getEscut() - 1);
            missatge = usuariVictima.getNom() + " s'escuda de " + usuariAtacant.getNom() + "\r";
            ferit = false;
            tipusEvent = 4;
        }
        //Si la víctima té escut que rebota, l'empara i el torna, perdent un escut
        //Si l'atacant també té un escut, empara i el perd
        if (usuariVictima.getEscutRebot() > 0) {
            usuariVictima.setEscut(usuariVictima.getEscutRebot() - 1);
            torna = true;
            tipusEvent = 5;
            missatge = usuariVictima.getNom() + " s'escuda de " + usuariAtacant.getNom() + " i li torna\r";
            if ((usuariAtacant.getEscut() > 0) || (usuariAtacant.getEscutRebot() > 0)) {
                missatge = missatge + usuariAtacant.getNom() + " s'escuda també\r";
                ferit = false;
                tipusEvent = 4;
                if (usuariAtacant.getEscut() > 0) {
                    usuariAtacant.setEscut(usuariAtacant.getEscut() - 1);
                } else {
                    usuariAtacant.setEscutRebot(usuariAtacant.getEscutRebot() - 1);
                }
            }
        }

        //Ara hem de llevar la bolla a qui pertoqui, si s'ha de llevar
        if (ferit) {
            if (torna) {
                usuariAtacant = llevaBolla(usuariAtacant, graella);
                missatge = missatge + usuariAtacant.getNom() + " perd una bolla\r";
            } else {
                usuariVictima = llevaBolla(usuariVictima, graella);
                missatge = missatge + usuariVictima.getNom() + " perd una bolla\r";
            }
        }


        //Hem d'avisar a tots que hi ha hagut aquest atac
        //Per això emprarem pintarEvent i missatgeEvent de cada ususari
        partida.setMissatgesEvents(missatge);
        //Això pareix que no funciona
        for (Usuari usuTmp : partida.getUsuaris()) {
            usuTmp.setPintarEvent(true);
            usuTmp.setDesactivarEvent(false);
            usuTmp.setTipusEvent(tipusEvent);
        }
        
        //Llevam una bomba a l'atacant
        usuariAtacant.setBomba(usuariAtacant.getBomba() - 1);
        //Hem de marcar desadctivar, sinó queda 2 torns
        usuariAtacant.setDesactivarEvent(true);
        
        //Revisam si just hi ha un usuari amb tots els numeros menys un tapats
        //Per mantenir o no l'avís de possibilitat d'atac
        if (nomesUn(partida)) {
            partida.setEstrella(false);
        }
        partida.setMissatgesLog(partida.getMissatgesLog() + missatge);
        return partida;
    }

    public Usuari consultaUsuari(String idSession, Partida partida) {
        Usuari usuari = new Usuari();
        for (Usuari usuTmp : partida.getUsuaris()) {
            if (usuTmp.getIdSession().equals(idSession)) {
                usuari = usuTmp;
            }
        }
        return usuari;
    }

    public Usuari cercaVictima(String idSession, Partida partida) {
        Usuari usuari = new Usuari();
        List<Usuari> usuaris = new ArrayList();

        for (Usuari usuTmp : partida.getUsuaris()) {
            //Cercam com a víctima algíu que no som noltros
            if (usuTmp.getIdSession() != idSession) {
                for (Carto carTmp : usuTmp.getCartons()) {
                    //Si té 14 numeros, per tant li queda nomes 1, l'afegim al llistat de possibles víctimes
                    if (carTmp.getNumeros() == 14) {
                        usuaris.add(usuTmp);
                    }
                }
            }
        }
        //Si hi ha algú com a víctima
        if (usuaris.size() > 0) {
            //Collim un dels usuaris al atzar
            Collections.shuffle(usuaris);
            usuari = usuaris.get(0);
        } //Sino hi ha ningú més que jo, me l'he tirat damunt!!!
        else {
            usuari = consultaUsuari(idSession, partida);
        }
        return usuari;
    }

    public Usuari llevaBolla(Usuari usuari, Parrilla graella) {
        List<Bolla> bolles = new ArrayList();
        //Aquestes són les bolles que hi ha al bombo amb el seu estat i triam les que no han sortit
        for (Bolla bolla : graella.getBombo()) {
            if (!bolla.isSortit()) {
                bolles.add(bolla);
            }
        }
        //Dins la llista bolles tenim les bolles del bombo que mno han sortit
        //Ara hem de triar quina bolla de quin cartó hem de llevar
        //Els cartons són unes matrius de 3 x 9 que tenen: Valor si hi surt el valor i -1 si no hi ha res
        //Per tant, hem de cercar només dins els que siguin més grans que 0
        //També sabem, que han sortit si el seu valor > 100
        //Per tant, haurem de canviar només aquella que el seu valor sigui >100 i tinguem alguna bolla
        //De la seva columna al bombo
        Carto carto = new Carto();
        //Hem de fer que se trii el carto que li queden nomes una bolla per sortir!!!!
        int ncarto = cercaCarto(usuari.getCartons());
        //int ncarto = 0;
        carto = usuari.getCartons().get(ncarto);

        //El que pareix més convenient és treure bolles del bombo que no hagin sortit
        //Veure si la hi ha alguna posició de la columna del cartó que pugui conicidir
        //I canviar-lo
        boolean trobada = false;
        //Podem començar de l'index i que vulguem fent un random amb el size de bolles
        //i index de les bolles que queden per sortir (el collim aleatori), que NO el valor de la bolla
        int i = (int) Math.floor(Math.random() * bolles.size());

        int j;
        int valor = 0;
        int valorTmp = 0;
        int columna = 0;
        int columnaInici = 0;
        int cont = 0;
        //Mentre no la trobem, la cercam
        //HEM DE CONTROLAR QUE NO TENGUI CAP MARCAT
        while (!trobada) {
            //Collim el valor de la bolla del bombo
            valor = bolles.get(i).getValor() + 1;
            System.out.println("Valor bolla[" + i + "]: " + valor);
            //Hem de veure a quina columna cau
            columna = (int) valor / 10;
            if(cont == 0){
                columnaInici = columna;
            }
            //Si hem collit tantes bolles com les que hi ha al bombo, és que no podem llevar-a
            if (cont > bolles.size()){
                trobada = true;
                System.out.println("No se pot llevar");
                //No hi ha cap bolla per posar
                //Hem de veure que feim en aquest cas
            }
            System.out.println("Cont: " + cont);
            cont++;
            //Hem de recorre aquesta columna per veure si hi ha alguna bolla que cumpleixi que se pot canviar
            for (j = 0; j < 3; j++) {
                System.out.println("Ncarto: " + ncarto);
                System.out.println("J: " + j);
                System.out.println("Columna: " + columna);
                valorTmp = carto.getLinies()[j][columna];
                System.out.println("ValorCarto[" + ncarto + "][" + j + "][" + columna +"]: " + valorTmp);
                if (valorTmp > 100) {
                    //Hem trobat una bolla que podem eliminar
                    trobada = true;
                    carto.assignaValor(j, columna, valor);
                    carto.setNumeros(carto.getNumeros() - 1);
                    usuari.getCartons().set(ncarto, carto);
                    break;
                }
            }
            //Passam a la següent bolla del bombo
            i++;
            //Si hem arribat al final del números del bombo, hem de tornar pel principi
            if (i > bolles.size()) {
                System.out.println("Reiniciam el comptador del bombo");
                i = 0;
            }
        }
        return usuari;
    }

    public int cercaCarto(List<Carto> cartons) {
        int ncarto = 0;
        int i = 0;
        for (Carto carto : cartons) {
            if (carto.getNumeros() == 14) {
                ncarto = i;
                break;
            }
            i++;
        }
        return ncarto;
    }

    public boolean nomesUn(Partida partida) {
        boolean resultat = false;
        int comptador = 0;
        for (Usuari usuari : partida.getUsuaris()) {
            for (Carto carto : usuari.getCartons()) {
                if (carto.getNumeros() == 14) {
                    comptador++;
                }
            }
        }
        if (comptador == 0) {
            resultat = true;
        }
        return resultat;
    }
}
