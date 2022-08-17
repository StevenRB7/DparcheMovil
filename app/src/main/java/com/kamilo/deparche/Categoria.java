package com.kamilo.deparche;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import Model.EstadosAnimo;
import Model.Intereses;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class Categoria extends AppCompatActivity {

    FloatingActionButton ini;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String usuario, interes, idCorreo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        LottieAnimationView animationView1 = (LottieAnimationView) findViewById(R.id.imageciclismo);
        LottieAnimationView animationView2 = (LottieAnimationView) findViewById(R.id.imageclub);
        LottieAnimationView animationView3 = (LottieAnimationView) findViewById(R.id.imagecofeebar);
        LottieAnimationView animationView4 = (LottieAnimationView) findViewById(R.id.imageambiente);
        LottieAnimationView animationView5 = (LottieAnimationView) findViewById(R.id.imagepintura);
        LottieAnimationView animationView6 = (LottieAnimationView) findViewById(R.id.imagepiscinas);

        animationView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = user.getDisplayName();
                idCorreo = user.getUid();
                interes = "Ciclismo";

                Intereses datos = new Intereses(interes,usuario,idCorreo);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Intereses")
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
                                Toast.makeText(Categoria.this, "Datos f", Toast.LENGTH_LONG).show();
                            }
                        });

                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                finish();
                animationView1.playAnimation();
            }
        });    animationView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = user.getDisplayName();
                idCorreo = user.getUid();
                interes = "Recreacion";

                Intereses datos = new Intereses(interes,usuario,idCorreo);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Intereses")
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
                                Toast.makeText(Categoria.this, "Datos f", Toast.LENGTH_LONG).show();
                            }
                        });

                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                finish();
                animationView2.playAnimation();
            }
        });    animationView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = user.getDisplayName();
                idCorreo = user.getUid();
                interes = "Cofeebar";

                Intereses datos = new Intereses(interes,usuario,idCorreo);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Intereses")
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
                                Toast.makeText(Categoria.this, "Datos f", Toast.LENGTH_LONG).show();
                            }
                        });

                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                finish();
                animationView3.playAnimation();
            }
        });
        animationView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = user.getDisplayName();
                idCorreo = user.getUid();
                interes = "Ambiente";

                Intereses datos = new Intereses(interes,usuario,idCorreo);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Intereses")
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
                                Toast.makeText(Categoria.this, "Datos f", Toast.LENGTH_LONG).show();
                            }
                        });

                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                finish();
                animationView4.playAnimation();
            }
        });
        animationView5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                usuario = user.getDisplayName();
                idCorreo = user.getUid();
                interes = "Pintura";

                Intereses datos = new Intereses(interes,usuario,idCorreo);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Intereses")
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
                                Toast.makeText(Categoria.this, "Datos f", Toast.LENGTH_LONG).show();
                            }
                        });

                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                finish();
                animationView5.playAnimation();
            }
        });
        animationView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = user.getDisplayName();
                idCorreo = user.getUid();
                interes = "Piscinas";

                Intereses datos = new Intereses(interes,usuario,idCorreo);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Intereses")
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
                                Toast.makeText(Categoria.this, "Datos f", Toast.LENGTH_LONG).show();
                            }
                        });

                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                finish();
                animationView6.playAnimation();
            }
        });
    }

}
