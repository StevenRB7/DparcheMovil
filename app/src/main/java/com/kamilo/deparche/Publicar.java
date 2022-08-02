package com.kamilo.deparche;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.FileProvider;


import android.content.Intent;

import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Model.Datos;

public class Publicar extends AppCompatActivity {

    Spinner spinner;

    ImageView tomarfoto, subirfoto, obtenerubicacion, publicacion;
    TextView lati, longi;


    Button publicar, cancelar;

    EditText Descripcion;

    LocationManager locationManager;
    Location location;
    private static final int VALUE_UBI = 200;

    Date fechasub;

    int indice = 0;

    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;


    String id, urlObtenida,url,descripciones,categorias;
    private Uri imageUri = null;

    /*   @RequiresApi(api = Build.VERSION_CODES.M)*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        publicacion = findViewById(R.id.imgPubli);
        publicar = findViewById(R.id.Publicar);
        spinner = findViewById(R.id.spinnerCate);

        //editext
        Descripcion = findViewById(R.id.txtDescripcion);

        referenciar();
        funciontomarfoto();
        funcionsubirfoto();
        funcionUbicacion();
        funcionPublicar();
        funcionEliminarPublicacion();

        /*verificarPermiso();*/


    }

    private void funcionsubirfoto() {

        subirfoto = findViewById(R.id.imgGaleria);
        subirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,SELEC_IMAGEN);

            }
        });
    }

    private void funciontomarfoto() {
        tomarfoto = findViewById(R.id.imgTomarFoto);
        tomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.imgTomarFoto:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagenArchivo = null;
                try {
                    imagenArchivo = crearImagen();
                } catch (Exception x) {
                    x.printStackTrace();
                }

                    if (imagenArchivo != null) {
                     /*   Uri fotoUri = FileProvider.getUriForFile (Publicar.this, "com.kamilo.deparche", imagenArchivo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                        startActivityForResult(intent, TOMAR_FOTO);
                        imageUri = fotoUri;*/
                        Uri fotoUri = FileProvider.getUriForFile (Publicar.this, "com.kamilo.deparche", imagenArchivo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                        startActivityForResult(intent, TOMAR_FOTO);
                        imageUri = fotoUri;
                    }
                  break;

                    case R.id.imgGaleria:

                }
            }
        });
    }

    private void funcionUbicacion() {

       /* locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        obtenerubicacion = findViewById(R.id.imgUbicacion);
        obtenerubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                longi.setText(""+ String.valueOf(location.getLongitude()));
.
                if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                    try {
                        Geocoder geocoder = new Geocoder(Publicar.this, Locale.getDefault());
                        List<Address> list = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(),1);
                        if (!list.isEmpty()){
                            Address DirCalle = list.get(0);
                            lati.setText(DirCalle.getAddressLine(0));
                            stringlati = lati.getText().toString();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });*/

    }

    private void funcionPublicar() {

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //notificacion
                Notificacion();

                indice = indice +1;
                id = String.valueOf(indice);

                if (validarfor()) {

                    long timestamp = System.currentTimeMillis();
                    String filePathAndName = "publicaciones/" + timestamp;

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
                    storageReference.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                String uploadedImageUri = "" + uriTask.getResult();
                                Toast.makeText(Publicar.this, "foto enviada correctamente ", Toast.LENGTH_LONG).show();

                                url = uploadedImageUri;
                                descripciones = Descripcion.getText().toString();
                                categorias = spinner.getSelectedItem().toString();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE-LLL-aaaa", Locale.forLanguageTag("es_ES"));
                                Date date = new Date();

                                String fecha = dateFormat.format(date);

                                fechasub = date;

                                Datos datos = new Datos(fechasub, url, descripciones, categorias);

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("publicacion")
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
                                                Toast.makeText(Publicar.this, "Datos f", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                Intent intent = new Intent(Publicar.this, InicioNav.class);
                                intent.putExtra("URL", url);
                                startActivity(intent);

                            }).addOnFailureListener(e -> {
                            });

                }else {

                }if (!validarfor()){

                    Toast.makeText(Publicar.this, "Faltan campos por llenar ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Publicar.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                    Intent intentt = new Intent(Publicar.this, InicioNav.class);
                    startActivity(intentt);
                }
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("notificaciondeparche").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
        }
    private void Notificacion() {
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try {
            String urlfoto = "https://www.pexels.com/es-es/foto/grupo-de-personas-1587927/";
            json.put("to", "/topics/"+"notificaciondeparche");
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "D'Parche");
            notificacion.put("body", "¡Nuevo evento publicado!");
            //notificacion.put("foto", urlfoto);

            json.put("data",notificacion);

            String URL =    "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,json, null,null){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> header = new HashMap<>();

                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAA8Lmz2Lo:APA91bEZub8G0tub7X2-ojoxyk8KO2JvKnS2282DrML_DbFw2xlDaLPrMoV0YnDi0dsyOYfc00QtSAFieM08nqYK4OKOQgWch4Xykwvc0WqiwHi62G7QqsNhcUXOLZ34MWyOzof26G2v");
                    return header;

                }
            };

            myrequest.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void funcionEliminarPublicacion() {

        cancelar = findViewById(R.id.Cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Publicar.this,  InicioNav.class);
                Toast.makeText(Publicar.this, "Operacion cancelada correctamente ", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

    }

    private void referenciar() {

    }

    /* @RequiresApi(api = Build.VERSION_CODES.M)
     private void verificarPermiso() {

         int PermisoUbi = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
         if (PermisoUbi == PackageManager.PERMISSION_DENIED) {
             if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

             } else {
                 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, VALUE_UBI);
             }
         }

     }
     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         switch (requestCode) {
             case VALUE_UBI: {
                 if (grantResults.length > 0
                         && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                     referenciar();
                 } else {
                     Toast.makeText(this, "PORFAVOR", Toast.LENGTH_SHORT).show();
                 }
                 return;
             }
         }
     }*/

    public boolean validarfor(){
        Boolean esValido = true;

        if (TextUtils.isEmpty(Descripcion.getText().toString())){
            Descripcion.setError("campo requerido");
            esValido = false;
        } else {
            Descripcion.setError(null);
        }
        /*if (TextUtils.isEmpty(TxtLatitud.getText())){
            TxtLatitud.setError("campo requerido");
            esValido = false;
        }else{
            TxtLatitud.setError(null);
        }*/
       /* if (imageUri == null){
            publicacion.setVisibility(View.VISIBLE);
            esValido = false;
        }else{
            publicacion.setError(null);
        }*/

        return esValido;
    }




    private File crearImagen() throws IOException {
        String nombreImagen = "fotoLugar";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        urlObtenida = imagen.getAbsolutePath();
        return imagen;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Publicar.super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == RESULT_OK && requestCode == SELEC_IMAGEN) {

            assert data != null;
            imageUri = data.getData();
            publicacion.setImageURI(data.getData());

        }else if(resultCode == RESULT_OK && requestCode == TOMAR_FOTO) {

            Uri uri = Uri.parse(urlObtenida);
            publicacion.setImageURI(uri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}