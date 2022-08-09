package Model;

public class Intereses {

    String Interes;
    String Usuario;
    String IdCorreo;

    public Intereses() {
    }

    public Intereses(String interes, String usuario, String idCorreo) {
        Interes = interes;
        Usuario = usuario;
        IdCorreo = idCorreo;
    }

    public String getInteres() {
        return Interes;
    }

    public void setInteres(String interes) {
        Interes = interes;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getIdCorreo() {
        return IdCorreo;
    }

    public void setIdCorreo(String idCorreo) {
        IdCorreo = idCorreo;
    }

    @Override
    public String toString() {
        return  Interes +  Usuario + IdCorreo ;
    }
}
