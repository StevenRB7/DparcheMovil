package Model;

import java.util.Date;

public class Datos {

    Date tiempo;
    String Url;
    String Descripciones;

    public Datos() {
    }

    public Datos(Date tiempo, String url, String descripciones) {
        this.tiempo = tiempo;
        Url = url;
        Descripciones = descripciones;
    }

    public Date getTiempo() {
        return tiempo;
    }

    public void setTiempo(Date tiempo) {
        this.tiempo = tiempo;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDescripciones() {
        return Descripciones;
    }

    public void setDescripciones(String descripciones) {
        Descripciones = descripciones;
    }


    @Override
    public String toString() {
        return tiempo + Url  + Descripciones ;
    }
}
