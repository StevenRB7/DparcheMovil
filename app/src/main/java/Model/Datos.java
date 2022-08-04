package Model;

import java.util.Date;

public class Datos {

    Date tiempo;
    String Url;
    String Descripciones;
    String Categorias;
    String Ides;
    String CorreoNom;
    String NomCorreo;
    String FotoCorreo;


    public Datos() {
    }

    public Datos(Date tiempo, String url, String descripciones, String categorias, String ides, String correoNom, String nomCorreo, String fotoCorreo) {
        this.tiempo = tiempo;
        Url = url;
        Descripciones = descripciones;
        Categorias = categorias;
        Ides = ides;
        CorreoNom = correoNom;
        NomCorreo = nomCorreo;
        FotoCorreo = fotoCorreo;
    }

    public Date getTiempo() {return tiempo;}

    public void setTiempo(Date tiempo) {this.tiempo = tiempo;}

    public String getUrl() {return Url;}

    public void setUrl(String url) {Url = url;}

    public String getDescripciones() {return Descripciones;}

    public void setDescripciones(String descripciones) {Descripciones = descripciones;}

    public String getCategorias() {return Categorias;}

    public void setCategorias(String categorias) {Categorias = categorias;}

    public String getIdes() {return Ides;}

    public void setIdes(String ides) {Ides = ides;}

    public String getCorreoNom() {return CorreoNom;}

    public void setCorreoNom(String correoNom) {CorreoNom = correoNom;}

    public String getNomCorreo() {return NomCorreo;}

    public void setNomCorreo(String nomCorreo) {NomCorreo = nomCorreo;}

    public String getFotoCorreo() {return FotoCorreo;}

    public void setFotoCorreo(String fotoCorreo) {FotoCorreo = fotoCorreo;}

    @Override
    public String toString() {
        return  tiempo + Url  + Descripciones + Categorias + Ides + CorreoNom + NomCorreo + FotoCorreo ;
    }
}
