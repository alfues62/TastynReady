package com.example.afusesc.tastynready.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.Platos;
import com.example.afusesc.tastynready.model.Reserva;

import java.util.List;

public class ReservasTrabajadorAdapter extends RecyclerView.Adapter<ReservasTrabajadorAdapter.ReservaViewHolder> {

    private Context context;
    private List<Reserva> reservas;
    private List<Reserva> reservasFiltradas; // Nueva lista para almacenar reservas filtradas


    public ReservasTrabajadorAdapter(Context context, List<Reserva> reservas) {
        this.context = context;
        this.reservas = reservas;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = reservas.get(position);

        holder.textViewComensales.setText("Comensales: " + reserva.getComensales());
        holder.textViewFecha.setText("Fecha: " + reserva.getFecha());
        holder.textViewHora.setText("Hora: " + reserva.getHora());
        holder.textViewSala.setText("Sala: " + reserva.getSala());
        holder.textViewUsuario.setText("Usuario: " + reserva.getUsuario());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtén la reserva seleccionada
                Reserva reservaSeleccionada = reservas.get(holder.getAdapterPosition());

                // Obtén la lista de platos de la reserva
                List<Platos> zPlatos = reservaSeleccionada.getzPlatos();

                // Ahora puedes hacer lo que quieras con la lista de platos,
                // por ejemplo, mostrarla en un diálogo, iniciar otra actividad, etc.
                mostrarZPlatos(zPlatos);
            }
        });
    }

    private void mostrarZPlatos(List<Platos> zPlatos) {
        // Implementa la lógica para mostrar la lista de platos,
        // por ejemplo, en un diálogo, en otra actividad, etc.
        // Aquí puedes mostrar la lista en un diálogo como ejemplo:
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Platos de la Reserva");

        // Construir el contenido del diálogo con los elementos de zPlatos
        StringBuilder stringBuilder = new StringBuilder();
        for (Platos plato : zPlatos) {
            // Asegúrate de que plato.getCantidad() no sea null antes de agregarlo al StringBuilder
            Integer cantidad = plato.getCantidad();
            if (cantidad != null) {
                stringBuilder.append("Nombre: ").append(plato.getNombre()).append("\n");
                stringBuilder.append("Cantidad: ").append(cantidad).append("\n");
                stringBuilder.append("Precio: ").append(plato.getPrecio()).append("\n\n");
            }
        }

        builder.setMessage(stringBuilder.toString());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Puedes agregar más lógica aquí si es necesario
            }
        });

        // Mostrar el diálogo
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return reservas.size();
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

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
        notifyDataSetChanged();
    }

    public void filterReservasPorFecha(List<Reserva> reservasFiltradas) {
        reservas.clear();
        reservas.addAll(reservasFiltradas);
        notifyDataSetChanged();
    }
}
