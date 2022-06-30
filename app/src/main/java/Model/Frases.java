package Model;

public class Frases {

    private String categoria;
    private String mensaje;

    public Frases() {
    }

    public Frases(String categoria, String mensaje) {
        this.categoria = categoria;
        this.mensaje = mensaje;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {

        return  mensaje ;
    }
}
