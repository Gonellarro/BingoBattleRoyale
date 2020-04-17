package model;

public class Bolla {

    private int valor;
    private boolean sortit;
    private String color;
    private static String[] colors = {"primary", "danger", "secondary", "warning", "secondary"};

    public Bolla() {
        this.valor = 0;
        this.sortit = false;
        this.color = "primary";
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isSortit() {
        return sortit;
    }

    public void setSortit(boolean sortit) {
        this.sortit = sortit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static String[] getColors() {
        return colors;
    }

    public static void setColors(String[] aColors) {
        colors = aColors;
    }

    @Override
    public String toString() {
        return "Bolla{" + "valor=" + valor + ", sortit=" + sortit + ", color=" + color + '}';
    }

}
