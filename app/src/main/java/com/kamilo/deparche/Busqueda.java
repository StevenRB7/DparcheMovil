package com.kamilo.deparche;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Datos;

public class Busqueda extends Activity implements SearchView.OnQueryTextListener {

    List<Datos> listDatos;
    AdaptadorDatos adaptadorDatos;
    FirebaseStorage storageRef;
    FirebaseFirestore db;
    RecyclerView recyclerView;

    SearchView buscador;

    SwipeRefreshLayout refreshLayout;


    int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance();
        recyclerView = findViewById(R.id.rcbuscador);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listDatos = new ArrayList<Datos>();

        buscador = findViewById(R.id.buscador);

        //navigation button
        referenciar2();
        //refrescar layout
          referenciar3();
        //llenar arreglo
        llenarlista();
        //lista
        initListener();
    }


   private void referenciar3() {

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                number++;
                Toast.makeText(Busqueda.this, "No hay nada que mostrar", Toast.LENGTH_LONG).show();

                refreshLayout.setRefreshing(false);

            }
        });

    }

    private void llenarlista() {

        db.collection("publicacion").orderBy("tiempo", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Date tiempo = document.getDate("tiempo");
                                String cadenaurl = document.getString("url");
                                String cadenadesc = document.getString("descripciones");
                                String cadenacate = document.getString("categorias");



                                Datos datos = new Datos(tiempo,cadenaurl,cadenadesc,cadenacate);
                                listDatos.add(datos);

                                adaptadorDatos = new AdaptadorDatos(Busqueda.this,listDatos);
                                recyclerView.setAdapter(adaptadorDatos);

                            }
                        }else {
                            Log.w(TAG,"Error getting documents.", task.getException());
                        }
                    }
                });

    }

    private void initListener() {
        buscador.setOnQueryTextListener(this);
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adaptadorDatos.filter(s);
        return false;
    }
}