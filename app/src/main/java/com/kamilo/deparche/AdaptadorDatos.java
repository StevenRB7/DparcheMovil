package com.kamilo.deparche;

import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import Model.Datos;

public class AdaptadorDatos  extends RecyclerView.Adapter<AdaptadorDatos.ViewHolder> {

    List<Datos> listDatos;
    Context context;
    List<Datos> originalItems;


    public AdaptadorDatos(Context context, List<Datos> listDatos) {
        this.listDatos = listDatos;
        this.context = context;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(listDatos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datos datos = listDatos.get(position);

        Glide.with(context).load(datos.getUrl()).into(holder.foto);
        holder.observa.setText(datos.getDescripciones());
        holder.ubicacion.setText(datos.getUbicacion());
        holder.tiempito.setText(DateFormat.format("EEEE  dd LLL yyyy",datos.getTiempo()));
        holder.cate.setText(datos.getCategorias());

        //gmail
        holder.nomGmail.setText(datos.getNomCorreo());
        Glide.with(context).load(datos.getFotoCorreo()).into(holder.perfil);
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {
            listDatos.clear();
            listDatos.addAll(originalItems);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Datos> collect = originalItems.stream()
                        .filter(i -> i.getCategorias().toLowerCase().contains(strSearch.toLowerCase()))
                        .collect(Collectors.toList());
                listDatos.clear();
                listDatos.addAll(collect);
            }
            else {
                for (Datos i : originalItems) {
                    if (i.getCategorias().toLowerCase().contains(strSearch.toLowerCase())){
                        listDatos.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView observa, tiempito, cate, nomGmail, ubicacion;
        ImageView foto, perfil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tiempito = itemView.findViewById(R.id.txtFecha);
            observa = itemView.findViewById(R.id.itemdescripcion);
            foto = (ImageView) itemView.findViewById(R.id.itemimgPublicacion);
            cate = itemView.findViewById(R.id.txtCate);
            nomGmail = itemView.findViewById(R.id.nomUsuarioItem);
            perfil = itemView.findViewById(R.id.imgPerfilItem);
            ubicacion = itemView.findViewById(R.id.cardubicacion);

        }
    }

}
