package com.example.afusesc.tastynready.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_carrito);

        back = findViewById(R.id.img_atras);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoConfirmacion();
            }
        });

        double totalPrecio = calcularTotalPrecio(platosReservados);

        TextView totalPrecioTextView = findViewById(R.id.cambiImporte);
        totalPrecioTextView.setText(String.format("%.2f", totalPrecio) + " € ");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Procede con la reserva ya que ambos campos están completos
                firebaseHandler.guardarReservaEnFirebase();
                // Llama al resetValues para restablecer los valores cuando retrocedes
                dataPicker.resetValues();
                // Terminas borras este activity y te lleva de vuelta a main
                Intent intent = new Intent(CarritoActivity.this, PagandoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sala.setText(DataPicker.obtenerIdSala());
        comensales.setText(String.valueOf(DataPicker.obtenerNumComensales()));
        fecha.setText(DataPicker.obtenerFechaSeleccionada());
        hora.setText(DataPicker.obtenerHoraSeleccionada());
    }

    private double calcularTotalPrecio(List<Platos> platos) {
        double total = 0;
        for (Platos plato : platos) {
            total += plato.getPrecioTotal();
        }
        return total;
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación")
                .setMessage("¿Estás seguro de que quieres salir? Los cambios no guardados se perderán.")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acciones a realizar si el usuario confirma (puede ser el regreso a la actividad principal)
                        irAMainActivity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acciones a realizar si el usuario cancela (puede ser nada en este caso)
                    }
                })
                .show();
    }

    private void irAMainActivity() {
        Intent intent = new Intent(CarritoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
