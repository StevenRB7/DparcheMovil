package com.kamilo.deparche;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Datos;

public class InicioNav extends AppCompatActivity {

    List<Datos> listDatos;
    AdaptadorDatos adaptadorDatos;
    FirebaseStorage storageRef;
    FirebaseFirestore db;
    RecyclerView recyclerView;


    ImageView  crearFrases;

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_nav);

        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance();
        recyclerView = findViewById(R.id.rcpublicaciones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listDatos = new ArrayList<Datos>();

        llenarLista();
        referenciar();
        referenciar2();
    }

    private void llenarLista() {

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

                                adaptadorDatos = new AdaptadorDatos(InicioNav.this,listDatos);
                                recyclerView.setAdapter(adaptadorDatos);

                            }
                        }else {
                            Log.w(TAG,"Error getting documents.", task.getException());
                        }
                    }
                });
    }
    //navegation boton
    private void referenciar2() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_nav);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.busqueda:
                        startActivity(new Intent(getApplicationContext()
                                ,Busqueda.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }

                return false;
            }
        });
    }
//taptarget
    private void referenciar() {
        LottieAnimationView frases = findViewById(R.id.verFrases);
        crearFrases = findViewById(R.id.verFrases);

        fab = findViewById(R.id.agregar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioNav.this, Publicar.class);
//                startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),0);
                startActivity(intent);


            }
        });
        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(fab, "Publicar", "En este apartado puedes hacer una publicacion de un eventos, acontecimientos, etc..")
                                .outerCircleColor(R.color.onda)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.azuloscuro)
                                .titleTextSize(25)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.black)
                                .textColor(R.color.black)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(android.R.color.holo_blue_bright)
                                .drawShadow(true)
                                .cancelable(true)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(30),

                        TapTarget.forView(frases, "Frases", "Aqui puedes encontrar frases motiivacionales aleatorias")
                                .outerCircleColor(R.color.onda)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.azuloscuro)
                                .titleTextSize(25)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.black)
                                .textColor(R.color.black)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.azul)
                                .drawShadow(true)
                                .cancelable(true)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(50)).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                }).start();

        crearFrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioNav.this, CrearFrase.class);
                startActivity(intent);
            }
        });


        frases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioNav.this, Popud.class);
                startActivity(intent);
                frases.pauseAnimation();
                class Crear implements View.OnClickListener {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(InicioNav.this, CrearFrase.class);
                        startActivity(intent);
                    }
                }
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.verFrases),
                        R.string.snask, Snackbar.LENGTH_SHORT);
                mySnackbar.setAction(R.string.Crear, new Crear());
                mySnackbar.show();

            }
        });
    }
}