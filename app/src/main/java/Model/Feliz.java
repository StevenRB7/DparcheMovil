package Model;

public class Feliz {

  String animo;

    public Feliz() {
    }

    public Feliz(String animo) {
        this.animo = animo;
    }

    public String getAnimo() {
        return animo;
    }

    public void setAnimo(String animo) {
        this.animo = animo;
    }

    @Override
    public String toString() {
        return  animo ;
    }
}
