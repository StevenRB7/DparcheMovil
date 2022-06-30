package com.kamilo.deparche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;

public class Categoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

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
                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                animationView1.playAnimation();
            }
        });    animationView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                animationView2.playAnimation();
            }
        });    animationView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                animationView3.playAnimation();
            }
        });
        animationView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                animationView4.playAnimation();
            }
        });
        animationView5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                animationView5.playAnimation();
            }
        });
        animationView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categoria.this,InicioNav.class);
                startActivity(intent);
                animationView6.playAnimation();
            }
        });
    }
}