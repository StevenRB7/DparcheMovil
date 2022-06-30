package com.kamilo.deparche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class OlvidarContra extends AppCompatActivity {

    TextView txtsi;
    EditText txtnumero;
    Button btncancelado, btnvere;
    String TAG = "GoogleSignIn",numeroString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contra);
        referenciar();
        
    }

    private void referenciar() {

        txtsi = findViewById(R.id.si);
        txtsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OlvidarContra.this, vereficarsi.class);
                startActivity(intent);
            }
        });
        txtnumero = findViewById(R.id.número);

        btnvere=findViewById(R.id.ver);
        btnvere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroString = txtnumero.getText().toString();
                if ( !(validarnumero(numeroString))) {


                }
            }
        });




        btncancelado = findViewById(R.id.btnCancelado);
        btncancelado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OlvidarContra.this, Login.class);
                startActivity(intent);
            }
        });
    }
    private boolean validarnumero(String numero) {
        Boolean esValido = true;

        Pattern numeros = Pattern.compile("[0-10]");


        if (!numeros.matcher(numero).find()) {
            txtnumero.setError("contraseña invalida");

            esValido = false;

        } else {

            esValido = true;

        }
        if (numero.length() > 10) {
            txtnumero.setError("contraseña invalida");
            esValido = false;
            //charcount.setTextColor(Color.RED);
        } else {
            esValido = true;
            // charcount.setTextColor(Color.GREEN);
        }
        return esValido;
    }
}