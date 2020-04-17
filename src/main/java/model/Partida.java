package model;

public class Partida {

    private boolean bingo;
    private boolean linea;
    private int cartons;
    private String idPartida;

    public Partida() {
        bingo = false;
        linea = false;
        cartons = 0;
    }
    
    public boolean isBingo() {
        return bingo;
    }

    public void setBingo(boolean bingo) {
        this.bingo = bingo;
    }

    public boolean isLinea() {
        return linea;
    }

    public void setLinea(boolean linea) {
        this.linea = linea;
    }

    public int getCartons() {
        return cartons;
    }

    public void setCartons(int cartons) {
        this.cartons = cartons;
    }

    public String getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(String idPartida) {
        this.idPartida = idPartida;
    }

    @Override
    public String toString() {
        return "Partida{" + "bingo=" + bingo + ", linea=" + linea + ", cartons=" + cartons + '}';
    }

}
