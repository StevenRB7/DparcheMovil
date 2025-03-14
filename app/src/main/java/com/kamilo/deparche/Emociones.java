package com.kamilo.deparche;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import Model.EstadosAnimo;
import Model.Feliz;
import Model.Frases;

public class Emociones extends AppCompatActivity implements View.OnClickListener{


    ConstraintLayout bg;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String usuario, estado, idCorreo, fechaa;

    Date fechasub;

    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emociones);

        bg = (ConstraintLayout) findViewById(R.id.bg);
        bg.setOnClickListener(this);

        LottieAnimationView animationView1 = (LottieAnimationView) findViewById(R.id.imgfeliz);
        LottieAnimationView animationView2 = (LottieAnimationView) findViewById(R.id.imgenojado);
        LottieAnimationView animationView3 = (LottieAnimationView) findViewById(R.id.imgtriste);

        animationView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = user.getDisplayName();
                idCorreo = user.getUid();
                estado = "Feliz";


                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE-LLL-aaaa", Locale.forLanguageTag("es_ES"));
                Date date = new Date();
                String fecha = dateFormat.format(date);
                fechasub = date;
                fechaa = date.toString();

                EstadosAnimo datos = new EstadosAnimo(usuario,estado,idCorreo,fechasub,fechaa);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Estados De Animo")
                        .add(datos)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(Emociones.this, "Datos f", Toast.LENGTH_LONG).show();
                            }
                        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Feliz");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            id = (snapshot.getChildrenCount());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                String animo = "Feliz como una lombriz";
                Feliz feli = new Feliz(animo);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(feli);

                Intent intent = new Intent(Emociones.this,Categoria.class);
                startActivity(intent);
                finish();

                animationView1.playAnimation();
                //VUELVE INVISIBLE ALA IMAGEN Y MUESTRA EL TOAST Y CAMBIA AL SIGUIENTE VISTA
                if (view == animationView1) {
                    bg.setVisibility(View.GONE);
                    tine tine = new tine();
                    tine.execute();
                  //  Toast.makeText(Emociones.this, "cada 15 dias ", Toast.LENGTH_SHORT).show();
                }

            }
        });    animationView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = user.getDisplayName();
                idCorreo = user.getUid();
                estado = "Enojado";


                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE-LLL-aaaa", Locale.forLanguageTag("es_ES"));
                Date date = new Date();
                String fecha = dateFormat.format(date);
                fechasub = date;
                fechaa = date.toString();

                EstadosAnimo datos = new EstadosAnimo(usuario,estado,idCorreo,fechasub,fechaa);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Estados De Animo")
                        .add(datos)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(Emociones.this, "Datos f", Toast.LENGTH_LONG).show();
                            }
                        });


                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Enojado");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            id = (snapshot.getChildrenCount());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                String animo = "Estamos enojados";
                Feliz eno = new Feliz(animo);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(eno);

                Intent intent = new Intent(Emociones.this,Categoria.class);
                startActivity(intent);
                finish();

                animationView2.playAnimation();
                //VUELVE INVISIBLE ALA IMAGEN Y MUESTRA EL TOAST Y CAMBIA AL SIGUIENTE VISTA
                if (view == animationView2) {
                    bg.setVisibility(View.GONE);
                    tine tine = new tine();
                    tine.execute();
                   // Toast.makeText(Emociones.this, "cada 15 dias ", Toast.LENGTH_SHORT).show();
                }
            }
        });    animationView3.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                usuario = user.getDisplayName();
                idCorreo = user.getUid();
                estado = "Trizte";

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE-LLL-aaaa", Locale.forLanguageTag("es_ES"));
                Date date = new Date();
                String fecha = dateFormat.format(date);
                fechasub = date;
                fechaa = date.toString();

                EstadosAnimo datos = new EstadosAnimo(usuario,estado,idCorreo,fechasub,fechaa);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Estados De Animo")
                        .add(datos)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(Emociones.this, "Datos f", Toast.LENGTH_LONG).show();
                            }
                        });


                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Trizte");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            id = (snapshot.getChildrenCount());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                String animo = "Tamos trustez";
                Feliz tite = new Feliz(animo);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(tite);

                Intent intent = new Intent(Emociones.this,Categoria.class);
                startActivity(intent);
                finish();

                animationView3.playAnimation();
                //VUELVE INVISIBLE ALA IMAGEN Y MUESTRA EL TOAST Y CAMBIA AL SIGUIENTE VISTA
                if (view == animationView3) {
                    bg.setVisibility(View.GONE);
                    tine tine = new tine();
                    tine.execute();
                  //  Toast.makeText(Emociones.this, "cada 15 dias ", Toast.LENGTH_SHORT).show();
                }
            }
        });
//QUEMA FECHA ACTUAL
        long miliseconds = System.currentTimeMillis();
        Date date = new Date(miliseconds);
        System.out.println(date);
       // Toast.makeText(Emociones.this, "Fecha" + date, Toast.LENGTH_SHORT).show();

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent(Emociones.this,Categoria.class);
                startActivity(intent);
                finish();
            }
        };
//        Timer timer = new Timer();
//        timer.schedule( tarea,7000);
    }

    public void hilo() {
        try {
            //subproceso que se está ejecutando antes comienza a ejecutarse nuevamente.
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ejecutar() {
        tine tine = new tine();
        tine.execute();
    }

    @Override
    public void onClick(View v) {

    }

    public class tine extends AsyncTask<Void, Integer, Boolean> {

        @Override
        //tiempo de recarga layaut
        protected Boolean doInBackground(Void... voids) {
            for (int i = 1; i < 1296000; i++) {
                hilo();
            }
            return true;
        }

        @Override
        //visibilidad
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
/*
            Toast.makeText(MainActivity.this, "cada 15 dias ",Toast.LENGTH_SHORT).show();
*/
            bg.setVisibility(View.VISIBLE);
        }
    }
}