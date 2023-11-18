package com.example.afusesc.tastynready.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.Platos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPedidosFirestore extends RecyclerView.Adapter<AdaptadorPedidosFirestore.ViewHolderPedidosBD> {

    Context context;
    ArrayList<Platos> pedidosArrayList;

    public AdaptadorPedidosFirestore(Context context, ArrayList<Platos> platosArrayList) {
        this.context = context;
        this.pedidosArrayList = platosArrayList;
    }

    @NonNull
    @Override
    public AdaptadorPedidosFirestore.ViewHolderPedidosBD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_select_layout, parent, false);

        return new AdaptadorPedidosFirestore.ViewHolderPedidosBD(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPedidosFirestore.ViewHolderPedidosBD holder, int position) {

        Platos platos = pedidosArrayList.get(position);

        holder.Nombre.setText(platos.getNombre());
        holder.Precio.setText(String.valueOf(platos.getPrecio()));
        Picasso.get().load(platos.getImg()).into(holder.img);

        final int minValue = 0;
        final int maxValue = 12;
        holder.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(holder.valueEditText.getText().toString());
                if (currentValue < maxValue) {
                    currentValue++;
                }
                holder.valueEditText.setText(String.valueOf(currentValue));
                platos.setCantidad(currentValue);
            }
        });
        holder.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(holder.valueEditText.getText().toString());
                if (currentValue > minValue) {
                    currentValue--;
                }
                holder.valueEditText.setText(String.valueOf(currentValue));
                platos.setCantidad(currentValue);
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return pedidosArrayList.size();
    }

    public static class ViewHolderPedidosBD extends RecyclerView.ViewHolder{
        TextView Nombre, Precio;
        ImageView img;

        public Button incrementButton;
        public Button decrementButton;
        public EditText valueEditText;

        public ViewHolderPedidosBD(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.textView);
            Precio = itemView.findViewById(R.id.textView2);
            img = itemView.findViewById(R.id.img_entrante);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            decrementButton = itemView.findViewById(R.id.decrementButton);
            valueEditText = itemView.findViewById(R.id.cantidad);
        }
    }

    public List<Platos> obtenerPlatosConCantidadMayorACero() {
        List<Platos> platosSeleccionados = new ArrayList<>();
        for (Platos plato : pedidosArrayList) {
            int cantidad = plato.getCantidad();
            if (cantidad > 0) {
                platosSeleccionados.add(plato);
            }
        }
        return platosSeleccionados;
    }
}