package Model;

import java.util.Date;

public class EstadosAnimo {

    String Usuario;
    String Estado;
    String IdCorreo;
    Date Fecha;
    String Fechaa;


    public EstadosAnimo() {
    }

    public EstadosAnimo(String usuario, String estado, String idCorreo,Date fecha, String fechaa) {
        this.Fecha = fecha;
        Usuario = usuario;
        Estado = estado;
        IdCorreo = idCorreo;
        Fechaa = fechaa;

    }

    public String getFechaa() {
        return Fechaa;
    }

    public void setFechaa(String fechaa) {
        Fechaa = fechaa;
    }

    public Date getFecha() {return Fecha;}

    public void setFecha(Date fecha) {Fecha = fecha;}

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
        return  Usuario +  Estado +  IdCorreo + Fecha + Fechaa ;
    }
}
