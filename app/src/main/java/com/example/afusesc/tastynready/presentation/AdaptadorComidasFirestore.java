package com.example.afusesc.tastynready.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.Platos;

import java.util.ArrayList;

public class AdaptadorComidasFirestore extends RecyclerView.Adapter<AdaptadorComidasFirestore.ViewHolderPlatosBD> {

    Context context;
    ArrayList<Platos> platosArrayList;

    public AdaptadorComidasFirestore(Context context, ArrayList<Platos> platosArrayList) {
        this.context = context;
        this.platosArrayList = platosArrayList;
    }

    @NonNull
    @Override
    public AdaptadorComidasFirestore.ViewHolderPlatosBD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);

        return new ViewHolderPlatosBD(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorComidasFirestore.ViewHolderPlatosBD holder, int position) {

        Platos platos = platosArrayList.get(position);

        holder.Nombre.setText(platos.getNombre());
        holder.Precio.setText(String.valueOf(platos.getPrecio()));


    }

    @Override
    public int getItemCount() {
        return platosArrayList.size();
    }

    public static class ViewHolderPlatosBD extends RecyclerView.ViewHolder{

        TextView Nombre, Precio;

        public ViewHolderPlatosBD(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.nombre);
            Precio = itemView.findViewById(R.id.precio);
        }
    }
}
