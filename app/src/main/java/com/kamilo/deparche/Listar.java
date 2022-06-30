package com.kamilo.deparche;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import Model.Frases;

public class Listar extends AppCompatActivity {

    ListView lstUno;
    ArrayList<Frases> arrayList = new ArrayList<>();
    ArrayAdapter<Frases> arrayAdapter;
    TextView mensajeje;

    String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_listar);
        mensajeje = findViewById(R.id.mensaje);

        obtenerMensaje();
    }

    private void obtenerMensaje() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Frases");
        lstUno = findViewById(R.id.lstUno);
        arrayAdapter = new ArrayAdapter<Frases>(this, android.R.layout.simple_list_item_1, arrayList);
        lstUno.setAdapter(arrayAdapter);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Frases valores = snapshot.getValue(Frases.class);
                arrayList.add(valores);
                int min = 0;
                int max = arrayList.size();
                Random random = new Random();
                int value = random.nextInt(max + min) + min;
                // Toast.makeText(Listar.this, ""+ arrayList.get(value).getMensaje(), Toast.LENGTH_SHORT).show();
                mensaje = arrayList.get(value).getMensaje();
                mensajeje.setText(mensaje);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}