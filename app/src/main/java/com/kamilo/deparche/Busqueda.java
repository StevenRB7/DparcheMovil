package com.kamilo.deparche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Busqueda extends Activity {

    SwipeRefreshLayout refreshLayout;
    TextView textView;

    int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        //navigation button
        referenciar2();
        //refrescar layout
        referenciar3();

    }

    private void referenciar3() {

        refreshLayout = findViewById(R.id.refresh);
        textView = findViewById(R.id.txtre);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                number++;
                textView.setText("Buscando");
                Toast.makeText(Busqueda.this, "No hay nada que mostrar", Toast.LENGTH_LONG).show();

                refreshLayout.setRefreshing(false);

            }
        });

    }

    private void referenciar2() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_nav);

        bottomNavigationView.setSelectedItemId(R.id.busqueda);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.busqueda:
                        return true;

                    case R.id.frases:
                        startActivity(new Intent(getApplicationContext()
                                ,CrearFrase.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.eventos:
                        startActivity(new Intent(getApplicationContext()
                                ,Eventos.class));
                        overridePendingTransition(0,0);
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
}