package com.example.afusesc.tastynready.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.DataPicker;
import com.example.afusesc.tastynready.model.FirebaseHandler;
import com.example.afusesc.tastynready.model.Platos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoActivity extends AppCompatActivity {

    Button button;

    TextView sala, comensales, hora, fecha;

    // EFECTUACION DE RESERVA
    private DataPicker dataPicker;
    FirebaseHandler firebaseHandler;
    List<Platos> platosReservados;
    private RecyclerView recyclerView;
    private PlatoAdapter platoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_carrito);

        button = findViewById(R.id.guardarReserva);
        sala = findViewById(R.id.cambSala);
        comensales = findViewById(R.id.cambComens);
        hora = findViewById(R.id.cambHora);
        fecha = findViewById(R.id.cambFecha);
        dataPicker = new DataPicker();
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseHandler firebaseHandler = new FirebaseHandler();

        platosReservados = DataPicker.obtenerArray();

        platoAdapter = new PlatoAdapter(platosReservados);
        recyclerView.setAdapter(platoAdapter);

        double totalPrecio = calcularTotalPrecio(platosReservados);

        TextView totalPrecioTextView = findViewById(R.id.cambiImporte);
        totalPrecioTextView.setText(String.format("%.2f", totalPrecio) + " € ");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarConfirmacion();
            }
        });
        sala.setText(DataPicker.obtenerIdSala());
        comensales.setText(String.valueOf(DataPicker.obtenerNumComensales()));
        fecha.setText(DataPicker.obtenerFechaSeleccionada());
        hora.setText(DataPicker.obtenerHoraSeleccionada());
    }

    private void mostrarConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación")
                .setMessage("¿Estás seguro de que deseas proceder con la reserva?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Procede con la reserva ya que el usuario confirmó
                        firebaseHandler.guardarReservaEnFirebase();
                        // Llama al resetValues para restablecer los valores cuando retrocedes
                        dataPicker.resetValues();
                        // Terminas y borras este activity y te lleva de vuelta a main
                        Intent intent = new Intent(CarritoActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // No hagas nada si el usuario cancela
                    }
                })
                .show();
    }

    private double calcularTotalPrecio(List<Platos> platos) {
        double total = 0;
        for (Platos plato : platos) {
            total += plato.getPrecioTotal();
        }
        return total;
    }
}
