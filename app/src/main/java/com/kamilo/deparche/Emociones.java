package com.kamilo.deparche;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Emociones extends AppCompatActivity implements View.OnClickListener{


    ConstraintLayout bg;

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
                Intent intent = new Intent(Emociones.this,Categoria.class);
                startActivity(intent);

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
                Intent intent = new Intent(Emociones.this,Categoria.class);
                startActivity(intent);
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
                Intent intent = new Intent(Emociones.this,Categoria.class);
                startActivity(intent);
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