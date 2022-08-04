package com.kamilo.deparche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Frases;

public class CrearFrase extends AppCompatActivity {

    EditText etxtDos, etxtTres;
    Button btnUno, btnDos;
    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_frase);
        referenciar();
        referenciar2();
    }

    private void referenciar2() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_nav);

        bottomNavigationView.setSelectedItemId(R.id.frases);

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

    private void referenciar() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Frases");
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

        etxtDos = findViewById(R.id.etxtCategoria);
        etxtTres = findViewById(R.id.etxtMensaje);
        btnDos = findViewById(R.id.btnDos);
        btnUno = findViewById(R.id.btnUno);
        btnUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = etxtDos.getText().toString();
                String mensaje = etxtTres.getText().toString();
                Frases datos = new Frases(categoria, mensaje);
                myRef.child(String.valueOf(id = (int)(Math.random()*10000+1))).setValue(datos);

                Intent intent = new Intent(CrearFrase.this,  InicioNav.class);
                Toast ff = new Toast(getApplicationContext());

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.lytLayout));

                TextView txtMsg = (TextView)layout.findViewById(R.id.toasttxt);
                txtMsg.setText("Frase agregada");
                ff.setDuration(Toast.LENGTH_LONG);
                ff.setView(layout);
                ff.show();
                startActivity(intent);


            }
        });
        btnDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrearFrase.this, Listar.class);
                startActivity(intent);
            }
        });
    }
}