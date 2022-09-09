package Model;

public class Categorias {

    String categoria;

    public Categorias() {

    }

    public Categorias(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return  categoria ;
    }
}
