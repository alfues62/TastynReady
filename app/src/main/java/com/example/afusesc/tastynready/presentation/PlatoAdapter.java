package com.example.afusesc.tastynready.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.Platos;

import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder> {

    private List<Platos> listaDePlatos;

    public PlatoAdapter(List<Platos> listaDePlatos) {
        this.listaDePlatos = listaDePlatos;
    }

    // Constructor y métodos necesarios

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservas_layout, parent, false);
        return new PlatoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {
        Platos plato = listaDePlatos.get(position);
        holder.nombreTextView.setText(plato.getNombre());
        holder.precio.setText("Total: " + String.valueOf( plato.getPrecioTotal()) + " €");
        holder.cantidad.setText(String.valueOf(plato.getCantidad()) + " Uds.");

    }

    @Override
    public int getItemCount() {
        return listaDePlatos.size();
    }

    public static class PlatoViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreTextView;
        public TextView precio;
        public TextView cantidad;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.name);
            precio = itemView.findViewById(R.id.price);
            cantidad = itemView.findViewById(R.id.cantidad);
        }
    }
}
