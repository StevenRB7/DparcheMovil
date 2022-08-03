package com.kamilo.deparche.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.kamilo.deparche.pojos.Solicitudes;
import com.kamilo.deparche.pojos.Users;

import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.viewHolderAdapter> {

    List<Users> userList;
    Context context;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    SharedPreferences mPref;

    public AdapterUsuarios(List<Users> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_usuarios,parent,false);
        viewHolderAdapter holder = new viewHolderAdapter(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderAdapter holder, int position) {

        Users userss = userList.get(position);

        final Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);


        Glide.with(context).load(userss.getFoto()).into(holder.img_user);
        holder.tv_usuario.setText(userss.getNombre());

        if(userss.getId().equals(user.getUid())){
            holder.cardView.setVisibility(View.GONE);
        }else {
            holder.cardView.setVisibility(View.VISIBLE);
        }

        DatabaseReference ref_mis_botones = database.getReference("Solicitudes").child(user.getUid());
        ref_mis_botones.child(userss.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String estado = snapshot.child("estado").getValue(String.class);

                if (snapshot.exists()){
                    if(estado.equals("enviado")){

                        holder.send.setVisibility(View.VISIBLE);
                        holder.add.setVisibility(View.GONE);
                        holder.amigos.setVisibility(View.GONE);
                        holder.solicitud.setVisibility(View.GONE);
                        holder.progressBar.setVisibility(View.GONE);

                    }
                    if(estado.equals("amigos")){

                        holder.send.setVisibility(View.GONE);
                        holder.add.setVisibility(View.GONE);
                        holder.amigos.setVisibility(View.VISIBLE);
                        holder.solicitud.setVisibility(View.GONE);
                        holder.progressBar.setVisibility(View.GONE);

                    }
                    if(estado.equals("solicitud")){

                        holder.send.setVisibility(View.GONE);
                        holder.add.setVisibility(View.GONE);
                        holder.amigos.setVisibility(View.GONE);
                        holder.solicitud.setVisibility(View.VISIBLE);
                        holder.progressBar.setVisibility(View.GONE);

                    }
                }else {

                    holder.send.setVisibility(View.GONE);
                    holder.add.setVisibility(View.VISIBLE);
                    holder.amigos.setVisibility(View.GONE);
                    holder.solicitud.setVisibility(View.GONE);
                    holder.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference A = database.getReference("Solicitudes").child(user.getUid());
                A.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Solicitudes sol = new Solicitudes("enviado","");

                        A.child(userss.getId()).setValue(sol);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference B = database.getReference("Solicitudes").child(userss.getId());
                A.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Solicitudes sol = new Solicitudes("solicitud","");

                        B.child(user.getUid()).setValue(sol);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference count = database.getReference("Contador").child(userss.getId());
                count.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Integer val = snapshot.getValue(Integer.class);
                            if(val == 0){
                                count.setValue(1);
                            }else {
                                count.setValue(val+1);
                            }
                        }else {
                            count.setValue(1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                vibrator.vibrate(1000);


            }//final del onclick
        });

        holder.solicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String idchat = ref_mis_botones.push().getKey();

                DatabaseReference A = database.getReference("Solicitudes").child(userss.getId()).child(user.getUid());
                A.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Solicitudes sol = new Solicitudes("amigos",idchat);

                        A.setValue(sol);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference B = database.getReference("Solicitudes").child(user.getUid()).child(userss.getId());
                A.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Solicitudes sol = new Solicitudes("amigos",idchat);

                        B.setValue(sol);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                vibrator.vibrate(1000);

            }
        });

        holder.amigos.setOnClickListener(new View.OnClickListener() {
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
    public int getItemCount() {return userList.size();}

    public class viewHolderAdapter extends RecyclerView.ViewHolder {

        TextView tv_usuario;
        ImageView img_user;
        CardView cardView;

        Button add,send,amigos,solicitud;
        ProgressBar progressBar;

        public viewHolderAdapter(@NonNull View itemView) {
            super(itemView);

            tv_usuario = itemView.findViewById(R.id.recy_txtuser);
            img_user = itemView.findViewById(R.id.recy_img_user);
            cardView = itemView.findViewById(R.id.cardviewUser);

            add = itemView.findViewById(R.id.btn_add);
            send = itemView.findViewById(R.id.btn_send);
            amigos = itemView.findViewById(R.id.btn_amigos);
            solicitud = itemView.findViewById(R.id.btn_tengosoli);

            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }
}
