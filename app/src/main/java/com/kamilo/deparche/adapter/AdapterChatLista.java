package com.kamilo.deparche.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamilo.deparche.Mensajes;
import com.kamilo.deparche.R;
import com.kamilo.deparche.pojos.Users;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterChatLista extends RecyclerView.Adapter<AdapterChatLista.viewHolderAdapterChatList> {

   List<Users> userList;
   Context context;

   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
   FirebaseDatabase database = FirebaseDatabase.getInstance();

   SharedPreferences mPref;


    public AdapterChatLista(List<Users> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderAdapterChatList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chatlista,parent,false);
        viewHolderAdapterChatList holder = new viewHolderAdapterChatList(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderAdapterChatList holder, int position) {

        Users userss = userList.get(position);

        final Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        holder.tv_usuario.setText(userss.getNombre());
        Glide.with(context).load(userss.getFoto()).into(holder.img_user);

        DatabaseReference ref_mis_solis = database.getReference("Solicitudes").child(user.getUid());
        ref_mis_solis.child(userss.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String estado = snapshot.child("estado").getValue(String.class);
                if(snapshot.exists()){
                    if(estado.equals("amigos")){
                        holder.cardView.setVisibility(View.VISIBLE);
                    }else {
                        holder.cardView.setVisibility(View.GONE);
                    }
                }else {
                    holder.cardView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

        DatabaseReference ref_estado = database.getReference("Estado").child(userss.getId());
        ref_estado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String estado = snapshot.child("estado").getValue(String.class);
                String fecha = snapshot.child("fecha").getValue(String.class);
                String hora = snapshot.child("hora").getValue(String.class);

                if(snapshot.exists()){

                    if(estado.equals("conectado")){
                        holder.tv_conectado.setVisibility(View.VISIBLE);
                        holder.conectado.setVisibility(View.VISIBLE);
                        holder.desconectado.setVisibility(View.GONE);
                        holder.tv_desconectado.setVisibility(View.GONE);
                    }else {
                        holder.desconectado.setVisibility(View.VISIBLE);
                        holder.tv_desconectado.setVisibility(View.VISIBLE);
                        holder.tv_conectado.setVisibility(View.GONE);
                        holder.conectado.setVisibility(View.GONE);

                        if (fecha.equals(dateformat.format(c.getTime()))){
                            holder.tv_desconectado.setText("ult.vez hoy a las " + hora);
                        }else {
                            holder.tv_desconectado.setText("ult.vez " + fecha+" a las " +hora);
                        }

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPref = view.getContext().getSharedPreferences("usuario_sp",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPref.edit();

                DatabaseReference ref = database.getReference("Solicitudes").child(user.getUid()).child(userss.getId()).child("idchat");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String id_unico = snapshot.getValue(String.class);

                        if (snapshot.exists()){
                            Intent intent = new Intent(view.getContext(), Mensajes.class);
                            intent.putExtra("nombre",userss.getNombre());
                            intent.putExtra("img_user",userss.getFoto());
                            intent.putExtra("id_user",userss.getId());
                            intent.putExtra("id_unico",id_unico);
                            editor.putString("usuario_sp",userss.getId());
                            editor.apply();
                            view.getContext().startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class viewHolderAdapterChatList extends RecyclerView.ViewHolder {

        TextView tv_usuario;
        ImageView img_user;
        CardView cardView;

        TextView tv_conectado,tv_desconectado;
        ImageView conectado, desconectado;


        public viewHolderAdapterChatList(@NonNull View itemView) {
            super(itemView);

            tv_usuario = itemView.findViewById(R.id.recy_txtuser);
            img_user = itemView.findViewById(R.id.recy_img_user);
            cardView = itemView.findViewById(R.id.cardviewUser);

            tv_conectado = itemView.findViewById(R.id.txt_conectado);
            tv_desconectado = itemView.findViewById(R.id.txt_desconectado);

            conectado = itemView.findViewById(R.id.icon_conectado);
            desconectado = itemView.findViewById(R.id.icon_desconectado);

        }
    }
}
