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

        //Un cop ja el tenim hem de veure de quin tipus és
        boolean ferit = false;
        boolean torna = false;
        switch (usuariVictima.getPerfil()) {
            case "atacant":
                ferit = true;
                break;
            case "defensorp":
                torna = true;
                break;
            case "mag":
                ferit = true;
                break;
            case "normal":
                ferit = true;
                break;
            default:
                break;
        }
        //Un cop sabem qui rep, ho controlam
        //Collim l'index on és l'usuari victima
        //int i = partida.getUsuaris().lastIndexOf(usuariVictima);
        String missatge = "";
        if (torna) {
            missatge = usuariVictima.getNom() + " torna la bomba!\n\r";
            usuariVictima = usuariAtacant;
            //i = partida.getUsuaris().lastIndexOf(usuariVictima);
            ferit = true;
        }
        if (ferit) {
            missatge = missatge + usuariVictima.getNom() + " rep la bomba de " + usuariAtacant.getNom() + "!\n\r";
            usuariVictima = llevaBolla(usuariVictima, graella);
        } else {
            missatge = usuariVictima.getNom() + " atura la bomba de " + usuariAtacant.getNom() + "!\n\r";
        }
        
        //Actualitzam l'usuari victima a la partida
        int k = partida.getUsuaris().lastIndexOf(usuariVictima);
        partida.getUsuaris().set(k, usuariVictima);
        //Hem d'avisar a l'atacant que ha atacat
        int j = partida.getUsuaris().lastIndexOf(usuariAtacant);
        partida.getUsuaris().get(j).setAtac(true);
        //Hem de posar els valors a la partida
        partida.setAtac(true);
        //Revisam si just hi ha un usuari amb tots els numeros menys un tapats
        if (nomesUn(partida)) {
            partida.setEstrella(false);
        }
        partida.setMissatgesEvents(missatge);
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
        Bolla bollaTmp = new Bolla();
        //Mentre no la trobem, la cercam
        
        //HEM DE CONTROLAR QUE NO TENGUI CAP MARCAT
        while (!trobada) {
            bollaTmp = bolles.get(i);
            //Hem de veure a quina columna cau
            valor = bollaTmp.getValor() + 1;
            columna = (int) valor / 10;
            //Hem de recorre aquesta columna per veure si hi ha alguna bolla que cumpleixi que se pot canviar
            for (j = 0; j < 3; j++) {
                valorTmp = carto.getLinies()[j][columna];
                if (valorTmp > 100) {
                    //Hem trobat una bolla que podem eliminar
                    trobada = true;
                    carto.assignaValor(j, columna, valor);
                    carto.setNumeros(carto.getNumeros() - 1);
                    usuari.getCartons().set(ncarto, carto);
                    break;
                }
            }
            i++;
            //Si hem arribat al final del números del bombo, hem de tornar pel principi
            if (i>bolles.size()){
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
