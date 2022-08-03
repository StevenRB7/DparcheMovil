package com.kamilo.deparche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamilo.deparche.adapter.AdapterChats;
import com.kamilo.deparche.pojos.Chats;
import com.kamilo.deparche.pojos.Estado;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mensajes extends AppCompatActivity {

    CircleImageView img_user;
    TextView user_name;
    ImageView ic_conectado,ic_desconectado;
    SharedPreferences mPref;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref_estado = database.getReference("Estado").child(user.getUid());
    DatabaseReference ref_chat = database.getReference("Chats");

    EditText et_mensaje;
    ImageButton ev_mensaje;

    //id chat global
    String id_chat_global;
    Boolean amigo_online = false;

    //rv
    RecyclerView rv_chats;
    AdapterChats adapter;
    ArrayList<Chats> chatslist;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPref = getApplicationContext().getSharedPreferences("usuario_sp",MODE_PRIVATE);

        img_user = findViewById(R.id.img_userT);
        user_name = findViewById(R.id.txtuser);

        ic_conectado = findViewById(R.id.iconConectado);
        ic_desconectado = findViewById(R.id.iconDesconectado);

        String usuario = getIntent().getExtras().getString("nombre");
        String foto = getIntent().getExtras().getString("img_user");
        String id_user = getIntent().getExtras().getString("id_user");
        id_chat_global = getIntent().getExtras().getString("id_unico");

        colocarenvisto();

        et_mensaje = findViewById(R.id.et_txt_mensaje);
        ev_mensaje = findViewById(R.id.btn_enviar);
        ev_mensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msj = et_mensaje.getText().toString();

                if(!msj.isEmpty()){
                    final Calendar c = Calendar.getInstance();
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

                    String idpush = ref_chat.push().getKey();

                    if (amigo_online){
                        Chats chatsmj = new Chats(idpush,user.getUid(),id_user,msj,"si",dateFormat.format(c.getTime()),timeFormat.format(c.getTime()));
                        ref_chat.child(id_chat_global).child(idpush).setValue(chatsmj);
                        Toast.makeText(Mensajes.this, " Mensaje enviado ", Toast.LENGTH_SHORT).show();
                        et_mensaje.setText("");
                    }else {
                        Chats chatsmj = new Chats(idpush,user.getUid(),id_user,msj,"no",dateFormat.format(c.getTime()),timeFormat.format(c.getTime()));
                        ref_chat.child(id_chat_global).child(idpush).setValue(chatsmj);
                        Toast.makeText(Mensajes.this, " Mensaje enviado ", Toast.LENGTH_SHORT).show();
                        et_mensaje.setText("");
                    }


                }else {
                    Toast.makeText(Mensajes.this, "El mensaje esta vacio", Toast.LENGTH_SHORT).show();
                }

            }
        });

        final  String id_user_sp = mPref.getString("usuario_sp","");

        user_name.setText(usuario);
        Glide.with(this).load(foto).into(img_user);

        DatabaseReference ref = database.getReference("Estado").child(id_user_sp).child("chatcon");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String chatcon = snapshot.getValue(String.class);

                if (snapshot.exists()){
                    if(chatcon.equals(user.getUid())){
                        amigo_online = true;
                        ic_conectado.setVisibility(View.VISIBLE);
                        ic_desconectado.setVisibility(View.GONE);
                    }else {
                        amigo_online = false;
                        ic_desconectado.setVisibility(View.VISIBLE);
                        ic_conectado.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //rv
        rv_chats = findViewById(R.id.rv);
        rv_chats.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv_chats.setLayoutManager(linearLayoutManager);

        chatslist = new ArrayList<>();
        adapter = new AdapterChats(chatslist,this);
        rv_chats.setAdapter(adapter);

        LeerMensajes();
    }
    private void colocarenvisto() {

        ref_chat.child(id_chat_global).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chats chats = snapshot.getValue(Chats.class);

                    if (chats.getRecibe().equals(user.getUid())){
                        ref_chat.child(id_chat_global).child(chats.getId()).child("visto").setValue("si");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LeerMensajes() {

        ref_chat.child(id_chat_global).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    chatslist.removeAll(chatslist);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Chats chat = snapshot.getValue(Chats.class);
                        chatslist.add(chat);
                        setScroll();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setScroll() {
        rv_chats.scrollToPosition(adapter.getItemCount()-1);
    }

    private void estadoUser(String estado) {

        ref_estado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final  String id_user_sp = mPref.getString("usuario_sp","");

                Estado est = new Estado(estado,"","",id_user_sp);
                ref_estado.setValue(est);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        estadoUser("conectado");
    }

    @Override
    protected void onPause() {
        super.onPause();
        estadoUser("desconectado");

        dameultimafecha();
    }

    private void dameultimafecha() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

        ref_estado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref_estado.child("fecha").setValue(dateformat.format(c.getTime()));
                ref_estado.child("hora").setValue(timeformat.format(c.getTime()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}