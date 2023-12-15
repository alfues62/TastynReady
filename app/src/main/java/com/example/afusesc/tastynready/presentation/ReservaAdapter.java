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

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {
    private List<Reserva> reservasList;

    public ReservaAdapter(List<Reserva> reservasList) {
        this.reservasList = reservasList;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = reservasList.get(position);

        // Configura la vista con los datos de la reserva
        holder.textViewComensales.setText(String.valueOf(reserva.getComensales()));
        holder.textViewFecha.setText(reserva.getFecha());
        holder.textViewHora.setText(reserva.getHora());
        holder.textViewSala.setText(reserva.getSala());
        holder.textViewUsuario.setText(reserva.getUsuario());
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
