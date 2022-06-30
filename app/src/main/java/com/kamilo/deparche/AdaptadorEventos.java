package com.kamilo.deparche;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Model.Evento;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorEventos  extends RecyclerView.Adapter<AdaptadorEventos.ViewHolder> {

    private List<Evento> lista;
    private Context context;

    public AdaptadorEventos(List<Evento> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_eventos,parent, false);

        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Evento event = lista.get(position);
        holder.nombre.setText(event.getNombre());
        Picasso.get().load(event.getImagen()).resize(1000, 1000).centerInside().into(holder.imagen);
        holder.descripcion.setText(event.getDescripcion());
        holder.fecha.setText(event.getFecha());
        holder.direccion.setText(event.getDireccion());
    }

    @Override
    public int getItemCount() {

        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private ImageView imagen;
        private TextView descripcion;
        private TextView fecha;
        private TextView direccion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.name);
            imagen = itemView.findViewById(R.id.image);
            descripcion = itemView.findViewById(R.id.description);
            fecha = itemView.findViewById(R.id.date);
            direccion = itemView.findViewById(R.id.direcci);
        }
    }
}
