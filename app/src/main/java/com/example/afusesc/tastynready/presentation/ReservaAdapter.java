package com.example.afusesc.tastynready.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.Reserva;

import java.util.List;

interface OnItemClickListener {
    void onItemClick(Reserva reserva);
}

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {
    private List<Reserva> reservasList;
    private OnItemClickListener listener;
    public ReservaAdapter(List<Reserva> reservasList, OnItemClickListener listener) {
        this.reservasList = reservasList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        final Reserva reserva = reservasList.get(position);

        // Configura la vista con los datos de la reserva
        holder.textViewComensales.setText("Comensales: " + String.valueOf(reserva.getComensales()));
        holder.textViewFecha.setText("Fecha: " + reserva.getFecha());
        holder.textViewHora.setText("Hora: " + reserva.getHora());
        holder.textViewSala.setText("En la Sala: " + reserva.getSala());
        holder.textViewUsuario.setText("Tu Usuario: " + reserva.getUsuario());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(reserva);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return reservasList.size();
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewComensales, textViewFecha, textViewHora, textViewSala, textViewUsuario;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewComensales = itemView.findViewById(R.id.textViewComensales);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            textViewHora = itemView.findViewById(R.id.textViewHora);
            textViewSala = itemView.findViewById(R.id.textViewSala);
            textViewUsuario = itemView.findViewById(R.id.textViewUsuario);
        }
    }
}
