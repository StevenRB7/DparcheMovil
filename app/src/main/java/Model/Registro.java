package Model;

public class Registro {

    Integer ciclismo;
    Integer Ambiente;

    public Registro() {
    }

    public Registro(Integer ciclismo, Integer ambiente) {
        this.ciclismo = ciclismo;
        Ambiente = ambiente;
    }

    public Integer getCiclismo() {
        return ciclismo;
    }

    public void setCiclismo(Integer ciclismo) {
        this.ciclismo = ciclismo;
    }

    public Integer getAmbiente() {
        return Ambiente;
    }

    public void setAmbiente(Integer ambiente) {
        Ambiente = ambiente;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "ciclismo=" + ciclismo +
                ", Ambiente=" + Ambiente +
                '}';
    }
}
