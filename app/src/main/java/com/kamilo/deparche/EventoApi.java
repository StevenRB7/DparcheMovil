package com.kamilo.deparche;

import java.util.List;
import Model.Evento;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EventoApi {

    @GET("eventos")
   public Call<List<Evento>> leerTodo();
}
