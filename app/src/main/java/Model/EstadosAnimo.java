package Model;

public class EstadosAnimo {

    String Usuario;
    String Estado;
    String IdCorreo;


    public EstadosAnimo() {
    }

    public EstadosAnimo(String usuario, String estado, String idCorreo) {
        Usuario = usuario;
        Estado = estado;
        IdCorreo = idCorreo;

    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getIdCorreo() {
        return IdCorreo;
    }

    public void setIdCorreo(String idCorreo) {
        IdCorreo = idCorreo;
    }



    @Override
    public String toString() {
        return  Usuario +  Estado +  IdCorreo ;
    }
}
