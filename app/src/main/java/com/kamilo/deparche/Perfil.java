package com.kamilo.deparche;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Datos;

public class Perfil extends Activity {

    List<Datos> listDatos;
    AdaptadorDatos adaptadorDatos;
    FirebaseStorage storageRef;
    FirebaseFirestore db;
    RecyclerView recyclerView;



    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    ImageButton imgCerarSesion,imgCerarSesion2, eliminarCuenta;
    //Button btnCerrar;
    /*Button btnEliCu;*/
    private FirebaseAuth mAuth;
    private TextView textid, textnombre, textemail;
    private ImageView imagenUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance();
        recyclerView = findViewById(R.id.rcperfil);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listDatos = new ArrayList<Datos>();


        referenciar2();
        condicional();

        imagenUser = findViewById(R.id.imagenUser);
        textid = findViewById(R.id.textid);
        textnombre = findViewById(R.id.textNom);
        textemail = findViewById(R.id.textCorreo);
        imgCerarSesion = findViewById(R.id.cerrarsesion);
        imgCerarSesion2 = findViewById(R.id.cerrarsesion2);
        eliminarCuenta = findViewById(R.id.eliminarcuenta);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        textid.setText(currentUser.getUid());
        textnombre.setText(currentUser.getDisplayName());
        textemail.setText(currentUser.getEmail());

        Glide.with(this).load(currentUser.getPhotoUrl()).into(imagenUser);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


       imgCerarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            Perfil.this.finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "No se pudo cerrar sesion con Google", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    imgCerarSesion2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Perfil.this, InicioNav.class);
            startActivity(intent);
        }
    });

        eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if (signInAccount != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("Perfil", "onSuccess::Usuario Eliminado");
                                        signOut();
                                    }
                                });
                            } else {
                                Log.e("Perfil", "onComplete: Error al eliminar el usuario",
                                        task.getException());
                            }
                        }
                    });
                } else {
                    Log.d("Perfil", "Error: reAuthenticateUser: user account is null");
                }
            }
        });
    }

    private void condicional() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String correo = user.getEmail();

        if (user.getEmail().equals(correo)){
            llenarLista();
        }else {
            Toast.makeText(this, "Crea una publicacion para que puedas verla", Toast.LENGTH_SHORT).show();
        }


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
                                String cadenaides = document.getString("ides");
                                String cadenacorrename = document.getString("correoNom");
                                String cadenanamecorreo = document.getString("nomCorreo");
                                String cadenafoto = document.getString("fotoCorreo");
                                String cadenaubi = document.getString("ubicacion");


                                Datos datos = new Datos(tiempo,cadenaurl,cadenadesc,cadenacate,cadenaides,cadenacorrename,cadenanamecorreo,cadenafoto,cadenaubi);
                                listDatos.add(datos);

                                adaptadorDatos = new AdaptadorDatos(Perfil.this,listDatos);
                                recyclerView.setAdapter(adaptadorDatos);

                            }
                        }else {
                            Log.w(TAG,"Error getting documents.", task.getException());
                        }
                    }
                });

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String correo = user.getEmail();

        if (user.getEmail().equals(correo)){

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
                                    String cadenaides = document.getString("ides");
                                    String cadenacorrename = document.getString("correoNom");
                                    String cadenanamecorreo = document.getString("nomCorreo");
                                    String cadenafoto = document.getString("fotoCorreo");
                                    String cadenaubi = document.getString("ubicacion");


                                    Datos datos = new Datos(tiempo,cadenaurl,cadenadesc,cadenacate,cadenaides,cadenacorrename,cadenanamecorreo,cadenafoto,cadenaubi);
                                    listDatos.add(datos);

                                    adaptadorDatos = new AdaptadorDatos(Perfil.this,listDatos);
                                    recyclerView.setAdapter(adaptadorDatos);

                                }
                            }else {
                                Log.w(TAG,"Error getting documents.", task.getException());
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Crea una publicacion para que puedas verla", Toast.LENGTH_SHORT).show();
        }*/

    }



    private void referenciar2() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_nav);
        bottomNavigationView.setSelectedItemId(R.id.perfil);
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
                                ,Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.eventos:
                        startActivity(new Intent(getApplicationContext()
                                ,Eventos.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.perfil:
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

    private void signOut() {
        //sign out de firebase
        FirebaseAuth.getInstance().signOut();
        //sign out de "google sign in"
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Perfil.this.finish();
            }
        });
    }
}