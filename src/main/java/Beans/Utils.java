package Beans;

import java.util.List;
import model.Bingo;
import model.Carto;
import model.Partida;
import model.Sala;
import model.Usuari;

public class Utils {

    public Sala donaSala(String idSession, List<Sala> sales) {
        Sala resultat = new Sala();
        for (Sala sala : sales) {
            if (sala.getIdSession().equals(idSession)) {
                resultat = sala;
            }
        }
        return resultat;
    }

    public Sala donaSalaPerID(int id, List<Sala> sales) {
        Sala resultat = new Sala();
        for (Sala sala : sales) {
            if (sala.getId() == id) {
                resultat = sala;
            }
        }
        return resultat;

    }

    public Partida donaDarreraPartida(Sala sala) {
        Partida resultat = new Partida();
        int tamany = sala.getPartides().size();
        resultat = sala.getPartides().get(tamany - 1);
        return resultat;
    }

    /**
     * Aquest mètode serveix per comprovar si usuari està a una Sala abans de
     * que se doni d'alta Se passa com a paràmetre la identificació de la sala
     * on s'ha de ficar, la sessio de l'usuari i l'estructutura de dades del
     * bing per accedir a les sales Retorna la sala si l'usuari ja existeix dins
     * aquesta sala o null si no hi és
     */
    public Sala cercaSalaUsuari(int idSala, Bingo bingo, String idSessio) {
        Sala salaTrobada = new Sala();
        salaTrobada = null;
        /**
         * Miram de les sales que hi ha al bingo, quina és en la que jugam fent
         * un bucle de les sales del bingo i miram que coincideixi
         * l'identificatiu que ens ha passat l'usuari amb l'ID de la sala
         */
        for (Sala sala : bingo.getSales()) {
            if (sala.getId() == idSala) {
                /**
                 * Hem de veure que el jugador no existeixi ja d'abans Pot ser
                 * que l'usuari hagi sortit de la partida per algún error I no
                 * l'hem de tornar a crear. Li donarem el mateix usuari
                 */
                for (Usuari usuariTmp : sala.getUsuaris()) {
                    if (usuariTmp.getIdSession().equals(idSessio)) {
                        salaTrobada = sala;
                    }
                }
            }
        }
        /**
         * Aquest mètode torna la sala en funció de si l'ha pogut afegir o no
         */
        return salaTrobada;
    }

    public Partida donaPartidaUsuari(String idSession, List<Partida> partides) {
        Partida partida = new Partida();
        partida = null;
        for (Partida partidaTmp : partides) {
            for (Usuari usuariTmp : partida.getUsuaris()) {
                if (usuariTmp.getIdSession().equals(idSession)) {
                    partida = partidaTmp;
                }
            }
        }
        return partida;
    }

    public Usuari donaUsuariSala(Bingo bingo, int idSala, String idSession) {
        Usuari usuari = new Usuari();
        usuari = null;
        for (Sala salaTmp : bingo.getSales()) {
            /**
             * Cercam la sala on estam jugant
             */
            if (salaTmp.getId() == idSala) {
                /**
                 * Cercam l'usuari de dins la sala
                 */
                for (Usuari usuariTmp : salaTmp.getUsuaris()) {
                    if (usuariTmp.getIdSession().equals(idSession)) {
                        usuari = usuariTmp;
                    }
                }
            }
        }
        return usuari;
    }
    
    public Carto revisaCarto(Carto carto, Partida partida){
        /**Hem de recorrer el carto i veure si el número ha sortit o no
         * 
         */
        int i;
        int j;
        int valorBolla = 0;
        int comptador = 0;
        int nBolles = 0;
        boolean trobat = false;
        for(i = 0; i < 3; i++){
            for(j = 0; j < 9; j++){
                //Fins aquí recorrem el cartó. Ara hem de recorrer els números que han sortit a la partida
                //Primer revisam els que han sortit i no hem marcat
                comptador = 0;
                trobat = false;
                while((!trobat) && (comptador < partida.getNumeroBolles()) ){
                    //Si el valor ha sortit i l'estat és no taxat, l'hem de taxar
                    System.out.println("Comptador: " + comptador);
                    System.out.println("Total bolles: " + partida.getNumeroBolles());
                    valorBolla = partida.getBolles().get(comptador).getValor();
                    valorBolla = valorBolla + 1;
                    if ((valorBolla == carto.getLinies()[i][j][0]) && (carto.getLinies()[i][j][1] == 0)){
                        carto.assignaValor(i, j, carto.getLinies()[i][j][0], 1);
                        carto.setNumeros(carto.getNumeros()+1);
                        trobat = true;
                    }
                    comptador++;
                }
            }
        }
        return carto;
    }
    
    
    public List<Carto> revisaCartons(List<Carto> cartons, Partida partida){
        for(Carto carto:cartons){
            revisaCarto(carto, partida);
        }
        return cartons;
    }
}
