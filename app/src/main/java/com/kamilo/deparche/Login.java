package com.kamilo.deparche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Button bntGoogle;
    TextView TxtOlvide;
    int RC_SIGN_IN = 1;
    Button btningresar;
    public static String TAG = "GoogleSignIn", correoString, contraaseniaString;
    public static EditText correo, contrasenia;


    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInCliebt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        referenciar();

        bntGoogle = findViewById(R.id.btnSignIn);
        bntGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

                /*Intent intent = new Intent(Login.this, Emociones.class);
                startActivity(intent);*/


            }
        });

        TxtOlvide = findViewById(R.id.txtOlvideCon);
        TxtOlvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, OlvidarContra.class);
                startActivity(intent);

            }
        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInCliebt = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
    }

    private void referenciar() {

        correo = findViewById(R.id.idTxtCorreo);
        contrasenia = findViewById(R.id.idTxtContrasenia);

        btningresar = findViewById(R.id.button);
        btningresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                correoString = correo.getText().toString();
                contraaseniaString = contrasenia.getText().toString();
                if (!(validarEmail(correoString)) || !(validarcontrasenas(contraaseniaString))) {
                    //
                } else {
                    Intent intent = new Intent(Login.this, Emociones.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Resultado devuelto al iniciar el Intent de GoogleSignInApi.getSignInIntent (...);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In fallido, actualizar GUI
                    Log.w(TAG, "Google sign in failed", e);
                }
            } else {
                Log.d(TAG, "Error, login no exitoso:" + task.getException().toString());
                Toast.makeText(this, "Ocurrio un error. " + task.getException().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {



        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) { //si no es null el usuario ya esta logueado
            //mover al usuario al dashboard
            Intent dashboardActivity = new Intent(Login.this, Emociones.class);
            startActivity(dashboardActivity);
        }
        super.onStart();
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithCredential:success");
                                    //FirebaseUser user = mAuth.getCurrentUser();
//Iniciar DASHBOARD u otra actividad luego del SigIn Exitoso
                                    Intent dashboardActivity = new Intent(Login.this, Emociones.class);
                                    startActivity(dashboardActivity);
                                    Login.this.finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());

                                }
                            }
                        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInCliebt.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Boolean esValido = true;
        if (!pattern.matcher(email).find()){
            correo.setError("Email invalido");
            esValido = false;
        } else {
            esValido = true;
        }
        return pattern.matcher(email).matches();
    }

    public boolean validarcontrasenas(String contrasenas) {
        Boolean esValido = true;
        Pattern mayusculas = Pattern.compile("[A-Z]");
        Pattern minusculas = Pattern.compile("[a-z]");
        Pattern numeros = Pattern.compile("[0-9]");

        if (!minusculas.matcher(contrasenas).find()) {
            contrasenia.setError("contraseña invalida");
            esValido = false;
        } else {
            esValido = true;
        }

        if (!mayusculas.matcher(contrasenas).find()) {
            contrasenia.setError("contraseña invalida");

            esValido = false;
        } else {
            esValido = true;

        }
        if (!numeros.matcher(contrasenas).find()) {
            contrasenia.setError("contraseña invalida");

            esValido = false;

        } else {

            esValido = true;

        }
        if (contrasenas.length() < 8) {
            contrasenia.setError("contraseña invalida");
            esValido = false;
            //charcount.setTextColor(Color.RED);
        } else {
            esValido = true;
            // charcount.setTextColor(Color.GREEN);
        }
        return esValido;
    }


}