package com.kamilo.deparche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kamilo.deparche.Mensajes;
import com.kamilo.deparche.R;
import com.kamilo.deparche.pojos.Chats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterChats extends RecyclerView.Adapter<AdapterChats.viewHolderAdapter>{
    List<Chats> chatsList;
    Context context;
    public static final int MENSAJE_RIGHT = 1;
    public static final int MENSAJE_LEFT = 0;
    Boolean soloright = false;
    FirebaseUser fuser;

    public AdapterChats(List<Chats> chatsList, Context context) {
        this.chatsList = chatsList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterChats.viewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MENSAJE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_rigth,parent,false);
            return new AdapterChats.viewHolderAdapter(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_legth,parent,false);
            return new AdapterChats.viewHolderAdapter(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChats.viewHolderAdapter holder, int position) {

        Chats chats = chatsList.get(position);

        holder.txt_mensaje.setText(chats.getMensaje());

        if(soloright){

            if(chats.getVisto().equals("si")){
                holder.img_entregado.setVisibility(View.GONE);
                holder.img_visto.setVisibility(View.VISIBLE);
            }else {
                holder.img_entregado.setVisibility(View.VISIBLE);
                holder.img_visto.setVisibility(View.GONE);
            }

            final Calendar c = Calendar.getInstance();
            final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            if(chats.getFecha().equals(dateFormat.format(c.getTime()))){
                holder.txt_fecha.setText("hoy "+chats.getHora());
            }else {
                holder.txt_fecha.setText(chats.getFecha()+ " " + chats.getHora());
            }


        }//fin solo right


    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public class viewHolderAdapter extends RecyclerView.ViewHolder {

        TextView txt_mensaje,txt_fecha;
        ImageView img_entregado,img_visto;


        public viewHolderAdapter(@NonNull View itemView) {
            super(itemView);

            txt_mensaje = itemView.findViewById(R.id.txt_mensajee);
            txt_fecha = itemView.findViewById(R.id.txt_fecha);
            img_entregado = itemView.findViewById(R.id.img_entregado);
            img_visto = itemView.findViewById(R.id.img_visto);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatsList.get(position).getEnvia().equals(fuser.getUid())){
            soloright = true;
            return MENSAJE_RIGHT;
        }else{
            soloright= false;
            return MENSAJE_LEFT;
        }
    }
}
