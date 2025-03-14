package com.kamilo.deparche;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    ImageView tomarfoto, subirfoto, publicacion, imgUbicacion;
    TextView lati, longi, TxtLatitud;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//public static EtxObservaciones;
    Button publicar, cancelar;

    EditText Descripcion;

    private static final int VALUE_UBI = 200;

    Date fechasub;

    int indice = 0;

    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;


    String id, urlObtenida,url,descripciones,categorias,ides,correoname,namecorreo,fotocorreo,stringlati,ubicacion;

    String ubicacionVal;

    private Uri imageUri = null;



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
        TxtLatitud = findViewById(R.id.textlatitudpublicacion);

        referenciar();
        funciontomarfoto();
        funcionsubirfoto();
        funcionPublicar();
        funcionEliminarPublicacion();
        formulario();



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
    private void formulario() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
    }else {
        imgUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {UbicacionStart();}
        });
        }
    }

    //metodos para ubicacion/////////////////////////////////////////////
    private void UbicacionStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(Publicar.this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        lati.setText("Obteniendo Ubicación...");
        longi.setText("");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                UbicacionStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    lati.setText(""+DirCalle.getAddressLine(0));
                    stringlati=lati.getText().toString();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Localizacion implements LocationListener {
        Publicar mainActivity;

        public Publicar getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(Publicar mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String Text = "" + loc.getLatitude();
            String Text2 = "" + loc.getLongitude();
            lati.setText(Text);
            longi.setText(Text2);
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            lati.setText("Ubicacion Desactivada");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            longi.setText("Ubicacion Activada");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

    private void funcionPublicar() {

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //notificacion

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
                                //Toast.makeText(Publicar.this, "foto enviada correctamente ", Toast.LENGTH_LONG).show();
                                Toast toast4 = new Toast(getApplicationContext());

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_layout,
                                        (ViewGroup) findViewById(R.id.lytLayout));

                                TextView txtMsg = (TextView)layout.findViewById(R.id.toasttxt);
                                txtMsg.setText("Publicación realizada");
                                toast4.setDuration(Toast.LENGTH_SHORT);
                                toast4.setView(layout);
                                toast4.show();

                                url = uploadedImageUri;
                                descripciones = Descripcion.getText().toString();
                                categorias = spinner.getSelectedItem().toString();
                                ides = user.getUid();
                                correoname = user.getEmail();
                                namecorreo = user.getDisplayName();
                                fotocorreo = user.getPhotoUrl().toString();
                                ubicacion = lati.getText().toString();

                                ubicacionVal = TxtLatitud.getText().toString();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE-LLL-aaaa", Locale.forLanguageTag("es_ES"));
                                Date date = new Date();

                                String fecha = dateFormat.format(date);

                                fechasub = date;

                                Datos datos = new Datos(fechasub, url, descripciones, categorias,ides,correoname,namecorreo,fotocorreo,ubicacion);

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

                                Notificacion();

                                Intent intent = new Intent(Publicar.this, InicioNav.class);
                                intent.putExtra("URL", url);
                                startActivity(intent);

                            }).addOnFailureListener(e -> {
                            });

                }else {

                }if (!validarfor()){

                   // Toast.makeText(Publicar.this, "Faltan campos por llenar ", Toast.LENGTH_SHORT).show();
                    Toast campos= new Toast(getApplicationContext());

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_layout,
                            (ViewGroup) findViewById(R.id.lytLayout));

                    TextView txtMsg = (TextView)layout.findViewById(R.id.toasttxt);
                    txtMsg.setText("Faltan campos por llenar ");
                    campos.setDuration(Toast.LENGTH_SHORT);
                    campos.setView(layout);
                    campos.show();

                }else {
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
            notificacion.put("body", "¡Nuevo sitío publicado! ven disfruta con nosotros");
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
                Toast cancel = new Toast(getApplicationContext());

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.lytLayout));

                TextView txtMsg = (TextView)layout.findViewById(R.id.toasttxt);
                txtMsg.setText("Publicacion descartada");
                cancel.setDuration(Toast.LENGTH_SHORT);
                cancel.setView(layout);
                cancel.show();

                startActivity(intent);

            }
        });

    }

    private void referenciar() {

        imgUbicacion=findViewById(R.id.imgUbicacion);
        lati=findViewById(R.id.textlatitudpublicacion);
        longi=findViewById(R.id.textlongitudpublicacion);


    }


    public boolean validarfor(){
        boolean esValido = true;

        if (TextUtils.isEmpty(Descripcion.getText().toString())){
            Descripcion.setError("campo requerido");
            esValido = false;
        } else {
            Descripcion.setError(null);
        }
        if (TextUtils.isEmpty(TxtLatitud.getText())){
            TxtLatitud.setError("campo requerido");
            esValido = false;
        }else{
            TxtLatitud.setError(null);
        }

        if (imageUri == null){
            publicacion.setVisibility(View.VISIBLE);
            esValido = false;
        } else {
            TxtLatitud.setError(null);
        }

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