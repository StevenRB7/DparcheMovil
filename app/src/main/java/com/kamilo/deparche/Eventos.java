package com.kamilo.deparche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import Model.Evento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Eventos extends AppCompatActivity {


    private List<Evento> lista = new ArrayList<>();
    private AdaptadorEventos adapter;
    private RecyclerView rvlista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        referenciar2();
        leerTodo();
    }
    private void referenciar2() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_nav);

        bottomNavigationView.setSelectedItemId(R.id.eventos);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.busqueda:
                        startActivity(new Intent(getApplicationContext()
                                ,Busqueda.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext()
                                ,CrearFrase.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.eventos:
                        return true;

                    case R.id.perfil:
                        startActivity(new Intent(getApplicationContext()
                                ,Perfil.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,InicioNav.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }

    private void leerTodo() {

        adapter = new AdaptadorEventos(lista, this);
        rvlista = findViewById(R.id.listaEventos);
        rvlista.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rvlista.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://deparche-api.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EventoApi eventoApi = retrofit.create(EventoApi.class);

        Call<List<Evento>> res = eventoApi.leerTodo();

        res.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                lista.clear();
                lista.addAll(response.body());
                adapter.notifyDataSetChanged();
               // Toast.makeText(Eventos.this, "Eventos " + lista.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(Eventos.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}