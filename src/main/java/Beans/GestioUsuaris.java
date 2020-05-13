package Beans;

import model.Bingo;
import model.Partida;
import model.Sala;
import model.Usuari;
import model.PowerUp;

public class GestioUsuaris {

    /**
     * Aquest mètode crea un usuari a partir del nom, avatar, id de la partida
     * Primer s'ha de crear l'usuari amb les dades que ens dona després, s'ha de
     * ficar l'usuari dins la sala i partida i aquests dins bingo si l'usuari ja
     * existeix dins la sala, tenim el dilema de si eliminar-lo i tornar-lo a
     * crear o actualitzar-lo amb les dades que ha posat ara. Decidim no
     * llevar-lo. Tornam l'usuari per tal d'actualitzar el bingo al servlet i no
     * al bean
     */
    public Usuari crearUsuari(String nom, String avatar, int idPartida, Bingo bingo, String idSessio) {
        /**
         * Necessitam tornar l'usuari, amb la qual cosa, ja el cream
         */
        Usuari usuari = new Usuari();
        usuari.setNom(nom);
        usuari.setAvatar(avatar);
        usuari.setIdSession(idSessio);
        PowerUp pwup = new PowerUp();
        pwup.donaPowerUp();
        usuari.setPwup(pwup);

        /**
         * Verificam que l'usuari no existeixi ja dins la darrera partida
         */
        Sala sala = new Sala();
        Partida partida = new Partida();
        Utils ut = new Utils();
        /**
         * Cercam la sala. Si és null, vol dir que l'usuari no hi és. Si trobam
         * una sala, vol dir que l'usuari ja hi estava i no l'hem de tornar a
         * posar
         */
        sala = ut.cercaSalaUsuari(idPartida, bingo, idSessio);
        if (sala == null) {
            /**
             * Podem ficar l'usuari nou a la sala i a la partida
             */
            sala = ut.donaSalaPerID(idPartida, bingo.getSales());
            partida = ut.donaDarreraPartida(sala);
            partida.afegeixUsuari(usuari);
            sala.afegeixUsuari(usuari);
            usuari.iniciaCartons(sala.getNcartons());
            System.out.println("<<GESTIO USUARIS-CREACIO USUARI>> NPartides sala: " + sala.getPartides().size());
            usuari.setnPartida(sala.getPartides().size());            
        } else {
            /**
             * Si hem trobat una sala, actualitzam les dades de l'usuari dins
             * sala i darrera partida
             */
            /**
             * Cercam l'usuari dins la sala
             */
            for (Usuari usuariTmp : sala.getUsuaris()) {
                if (usuariTmp.getIdSession().equals(idSessio)) {
                    usuari = usuariTmp;
                }
            }
            usuari.setNom(nom);
            usuari.setAvatar(avatar);
            int i;
            i = sala.getUsuaris().lastIndexOf(usuari);
            usuari.iniciaCartons(sala.getNcartons());
            usuari.setnPartida(sala.getPartides().size());
        }
        /**
         * Tornam l'usuari al servlet
         */
        return usuari;
    }

}
