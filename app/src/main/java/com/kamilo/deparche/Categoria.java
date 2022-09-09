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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import Model.Categorias;
import Model.EstadosAnimo;
import Model.Feliz;
import Model.Intereses;
import Model.Registro;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class Categoria extends AppCompatActivity {

    FloatingActionButton ini;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String usuario, interes, idCorreo;

    Integer ciclismo = 0,
            ambiente = 0;

    long id = 0;

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

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Ciclismo");
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

                String cate = "ciclismo";
                Categorias cicli = new Categorias(cate);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(cicli);

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

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Recreacion");
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

                String cate = "recreacion";
                Categorias recre = new Categorias(cate);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(recre);

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

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Cofeebar");
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

                String cate = "cafe bar";
                Categorias cofee = new Categorias(cate);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(cofee);

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

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Ambiente");
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

                String cate = "ambiente";
                Categorias ambi = new Categorias(cate);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(ambi);

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

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Pintura");
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

                String cate = "pintura";
                Categorias pintu = new Categorias(cate);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(pintu);

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

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Piscinas");
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

                String cate = "piscinas";
                Categorias pisi = new Categorias(cate);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(pisi);

                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                finish();
                animationView6.playAnimation();
            }
        });
    }

}
