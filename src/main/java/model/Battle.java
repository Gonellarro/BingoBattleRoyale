package model;

import Beans.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Battle {

    public Partida bomba(Partida partida, String idSession, Parrilla graella) {
        //Primer, hem de veure quin usuari li falta nomes una bolla
        Usuari usuariAtacant = new Usuari();
        usuariAtacant = consultaUsuari(idSession, partida);
        Usuari usuariVictima = new Usuari();
        //usuariVictima = cercaVictima(idSession, partida);
        usuariVictima = cercaCartons(partida.getUsuaris(), usuariAtacant);

        //Un cop ja el tenim hem de veure si la victima té escut i de quin tipus
        String missatge = "";
        boolean ferit = true;
        boolean torna = false;
        int tipusEvent = 3;//0: Res, 1: Linea, 2: Bingo, 3:Atac ok, 4: Rebot, 5: Rebot i atac ok
        //Si la víctima tés escut, l'empara i perd 1 escut
        if (usuariVictima.getPwup().getNom().equals("escut")) {
            //Eliminam el powerup dela víctima
            usuariVictima.getPwup().setNom("FLASH");
            missatge = usuariVictima.getNom() + " s'escuda de " + usuariAtacant.getNom() + "\r";
            ferit = false;
            tipusEvent = 4;
        }
        //Si la víctima té escut que rebota, l'empara i el torna, perdent un escut
        //Si l'atacant també té un escut, empara i el perd
        if (usuariVictima.getPwup().getNom().equals("escutRebot")) {
            //Eliminam el powerup dela víctima
            usuariVictima.getPwup().setNom("FLASH");

            torna = true;
            tipusEvent = 5;
            missatge = usuariVictima.getNom() + " s'escuda de " + usuariAtacant.getNom() + " i li torna. ";
        }

        //Ara hem de llevar la bolla a qui pertoqui, si s'ha de llevar
        if (ferit) {
            if (torna) {
                usuariAtacant = llevaBolla(usuariAtacant, graella, idSession);
                missatge = missatge + usuariAtacant.getNom() + " rep la seva bomba\r";
            } else {
                usuariVictima = llevaBolla(usuariVictima, graella, idSession);
                missatge = missatge + usuariVictima.getNom() + " rep una bomba de " + usuariAtacant.getNom() + "\r";
            }
        }

        //Hem d'afegir el missatge al log i als events
        partida.afegirMissatgesEvent(missatge, "3");
        partida.setMissatgesLog(partida.getMissatgesLog() + missatge);

        //Llevam el PowerUp de l'atacant
        usuariAtacant.getPwup().setNom("FLASH");

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
                    //Volem que pegui al que vagi primer
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

    public Usuari llevaBolla(Usuari usuari, Parrilla graella, String idSession) {
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
        //Hem de fer que se trii el carto que li queden nomes una bolla per sortir en el cas d'atacar!!!!
        //En el cas d'un rebot, hem de triar un al atzar
        int ncarto = 0;
        if (usuari.getIdSession().equals(idSession)) {
            //ncarto = atzar();
        } else {
            ncarto = cercaCarto(usuari.getCartons());
        }
        if (ncarto > -1) {
            carto = usuari.getCartons().get(ncarto);
        } else {
            //No se pot llevar cap bolla perquè no en té cap per llevar
        }

        //El que pareix més convenient és treure bolles del bombo que no hagin sortit
        //Veure si hi ha alguna posició de la columna del cartó que pugui conicidir
        //I canviar-lo
        boolean trobada = false;
        //Podem començar de l'index "i" que vulguem fent un random amb el size de bolles

        //"i" index de les bolles que queden per sortir (el collim aleatori), que NO el valor de la bolla
        int i = (int) Math.floor(Math.random() * bolles.size());

        int j;
        int k;
        int valor = 0;
        int valorTmp = 0;
        int estatTmp = 0;
        int columna = 0;
        int columnaInici = 0;
        int cont = 0;
        //Mentre no la trobem, la cercam
        //HEM DE CONTROLAR QUE NO TENGUI CAP MARCAT
        while (!trobada) {
            //Collim el valor de la bolla del bombo
            valor = bolles.get(i).getValor() + 1;
            //Hem de veure a quina columna cau
            columna = (int) valor / 10;
            //El cas que el valor sigui 90
            if (columna == 9) {
                columna = 8;
            }
            //Si hem de començar, posam la columna d'inici al valor que ha sortit INICIALMENT de la columna
            if (cont == 0) {
                columnaInici = columna;
            }
            //Si hem collit tantes bolles com les que hi ha al bombo, és que no podem llevar-la
            if (cont > bolles.size()) {
                trobada = true;
                //No hi ha cap bolla per posar
                //Hem de veure que feim en aquest cas
            }
            // System.out.println("Cont: " + cont);
            cont++;
            //Hem de recorre aquesta columna per veure si hi ha alguna bolla que cumpleixi que se pot canviar
            for (j = 0; j < 3; j++) {
                valorTmp = carto.getLinies()[j][columna][0];
                estatTmp = carto.getLinies()[j][columna][1];
                //Si estatTmp = 1, vol dir que aquest està taxat
                if (estatTmp == 1) {
                    //Hem trobat una bolla que podem eliminar
                    //Ara hem de veure que no sigui cap de les que té més amunt
                    trobada = true;
                    for (k = 0; k < j; k++) {
                        if (carto.getLinies()[j][columna][0] == carto.getLinies()[k][columna][0]) {
                            trobada = false;
                        }
                    }
                    if (trobada) {
                        carto.assignaValor(j, columna, valor, 4);
                        carto.setNumeros(carto.getNumeros() - 1);
                        break;
                    }
                }
            }
            //Si no l'hem trobat
            if (!trobada) {
                //Passam a la següent bolla del bombo
                i++;
                //Si hem arribat al final del números del bombo, hem de tornar pel principi
                if (i == bolles.size()) {
                    i = 0;
                }
            }
        }
        return usuari;
    }

    //Cerca el cartó que està millor, és a dir, que li queden menys bolles per tapar
    public int cercaCarto(List<Carto> cartons) {
        int ncarto = 0;
        boolean fi = false;
        int comptador = 0;
        int maxim = -1;
        Carto cartoTmp = new Carto();

        while (!fi) {
            //Hem de llevar una bolla del cartó que li quedin menys bolles per tapar o que té més tapats
            for (Carto carto : cartons) {
                if (carto.getNumeros() > maxim) {
                    cartoTmp = carto;
                    maxim = carto.getNumeros();
                    ncarto = comptador;
                }
                comptador++;
            }
            //A cartoTmp tenim el cartó que li queden menys bolles per tapar o té més números tapats
            if (cartoTmp.getNumeros() > 0) {
                fi = true;
            } else {
                //A veure que feim si tiram a un carto que no té números tapats
                ncarto = -1;
                fi = true;
            }
        }
        return ncarto;
    }

    //Diu si hi ha al menys un usuari que li falten 1 bolla per sortir
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

    public Partida canvi(Partida partida, Usuari usuari) {
        //Hem de veure qui té els millors cartons
        List<Carto> cartonsBons = new ArrayList();
        List<Carto> cartonsDolents = new ArrayList();
        Usuari usuariBo = new Usuari();
        String missatge = "";
        usuariBo = cercaCartons(partida.getUsuaris(), usuari);
        if (!usuariBo.getPwup().getNom().equals("escut")) {
            //Quan els tenim, 1. els revisam i llavors els canviam 
            Utils ut = new Utils();

            //Revisam els cartons a canviar
            cartonsBons = usuariBo.getCartons();
            cartonsBons = ut.revisaCartons(cartonsBons, partida);

            cartonsDolents = usuari.getCartons();
            cartonsDolents = ut.revisaCartons(cartonsDolents, partida);

            //Un cop revisats, els intercanviam
            usuariBo.setCartons(cartonsDolents);
            usuari.setCartons(cartonsBons);
            missatge = usuari.getNom() + " canvia els cartons amb " + usuariBo.getNom() + "\r";
        } else {
            missatge = usuari.getNom() + " canvia els cartons amb " + usuariBo.getNom() + " però l'esquiva!\r";
            //Li llevam s'escut
            PowerUp pwup = new PowerUp();
            usuariBo.setPwup(pwup);
        }

        //Hem d'afegir el missatge al log i als events
        partida.afegirMissatgesEvent(missatge, "5");
        partida.setMissatgesLog(partida.getMissatgesLog() + missatge);

        //Llevam el PowerUp
        PowerUp pwup = new PowerUp();
        usuari.getPwup().setNom("FLASH");

        return partida;
    }

    //Cerca l'usuari que té millor puntuació
    public Usuari cercaCartons(List<Usuari> usuaris, Usuari usuari) {
        Usuari usuariBo = new Usuari();
        int maxim = -1;
        for (Usuari usuTmp : usuaris) {
            //Si no és ell mateix
            if (!usuTmp.getIdSession().equals(usuari.getIdSession())) {
                for (Carto cartoTmp : usuTmp.getCartons()) {
                    int numero = cartoTmp.getNumeros();
                    if (cartoTmp.getNumeros() > maxim) {
                        usuariBo = usuTmp;
                        maxim = cartoTmp.getNumeros();
                    }
                }
            }
        }
        return usuariBo;
    }

    public Partida platan(Partida partida, Usuari usuari) {
        String missatge = "";
        //Hem de seleccionar l'usuari a qui tirar-li el platan de forma atzarosa
        Usuari usuariVictima = cercaUsuariAtzar(partida, usuari.getIdSession());
        if (!usuariVictima.getPwup().getNom().equals("escut")) {
            //Ara li hem d'activar que té l'egfecte del platan
            usuariVictima.setAtacPlatan(true);
            missatge = usuari.getNom() + " llança un platan a " + usuariVictima.getNom() + "\r";
        } else {
            missatge = usuari.getNom() + " llança un platan a " + usuariVictima.getNom() + " però l'esquiva!\r";
            //Li llevam s'escut
            usuariVictima.getPwup().setNom("FLASH");
        }

        //Hem d'afegir el missatge al log i als events
        partida.afegirMissatgesEvent(missatge, "3");
        partida.setMissatgesLog(partida.getMissatgesLog() + missatge);

        //Llevam el PowerUp de l'atacant
        PowerUp pwup = new PowerUp();
        usuari.setPwup(pwup);
        return partida;
    }

    public Usuari cercaUsuariAtzar(Partida partida, String idSession) {
        Usuari usuari = new Usuari();
        List<Usuari> usuaris = new ArrayList();
        usuaris = partida.getUsuaris();
        Collections.shuffle(usuaris);
        usuari = usuaris.get(0);
        //no ens podem tirar el platan damunt
        if (usuari.getIdSession().equals(idSession)) {
            usuari = usuaris.get(1);
        }
        return usuari;
    }

    public void estrella(Partida partida, Usuari usuari) {
        /**
         * Hem de cercar un numero d'un cartó que no estigui marcat i canviar-lo
         * per una bolla que no tengui a altre banda de la columna i que hagi
         * sortit
         */
        /**
         * Primer cercarem el cartó on li quedin menys bolles
         */
        int numCarto = cercaCarto(usuari.getCartons());
        Carto carto = new Carto();
        carto = usuari.getCartons().get(numCarto);

        List<Bolla> bolles = partida.getBolles();
        int valor;
        int valorPosicioCarto;
        int columna = 0 ;
        int estat;
        boolean trobat = false;
        boolean fi;
        int i;
        int j;
        int index = 0;
        Bolla bolla = new Bolla();
        int nbolla = 0;
        while (!trobat) {
            bolla = bolles.get(nbolla);
            valor = bolla.getValor();
            for (i = 0; i < 3; i++) {
                columna = (int) valor / 10;
                //Si trobam una bolla que no ha tapat
                if (carto.getLinies()[i][columna][1] == 0) {
                    trobat = true;
                    index = i;
                    //Hem de recorrerr les altres files per veure que aquesta bolla no està a les altres que tenim marcades
                    for (j = 0; j < 3; j++) {
                        //Si no és la mateixa bolla
                        if (j != i) {
                            //Si la bolla ha sortit i resulta que també la tenim al cartó l'hem de descartar
                            if ((carto.getLinies()[i][columna][1] == 1) && (carto.getLinies()[i][columna][0] == valor)) {
                                //És igual a una que ja tenim marcada
                                //Hem de descartar aquesta bolla
                                trobat = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (trobat){
                carto.assignaValor(index, columna, valor, 1);
            }
            else{
                nbolla++;
            }
        }
    }

}
