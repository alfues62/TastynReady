package com.example.afusesc.tastynready.presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PaginaTrabajadorActivity extends Activity {

    private Button botonCogerFecha;
    private TextView nombreUsuario;
    private RecyclerView recyclerView;
    private ReservaAdapter reservasAdapter;

    private Calendar calendar;
    private List<ReservasActivity> listaReservas;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_trabajador);

        // Inicializa Firebase
        db = FirebaseFirestore.getInstance();

        // Inicializa la lista de reservas
        listaReservas = new ArrayList<>();

        // Inicializa los elementos de la interfaz
        botonCogerFecha = findViewById(R.id.boton_seleccionarDia);
        nombreUsuario = findViewById(R.id.nombre_user);
        recyclerView = findViewById(R.id.recycler_reservas);

        // Configura el RecyclerView y su adaptador
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
/*
        reservasAdapter = new ReservaAdapter(listaReservas);
*/
        recyclerView.setAdapter(reservasAdapter);

        // Inicializa el objeto Calendar
        calendar = Calendar.getInstance();

        // Configura el OnClickListener para el botón
        botonCogerFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoFecha();
            }
        });
    }

    // Método para mostrar el diálogo de selección de fecha
    private void mostrarDialogoFecha() {
        // Obtén el año, mes y día actuales
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Crea un cuadro de diálogo de selección de fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Actualiza el TextView con la fecha seleccionada
                calendar.set(year, monthOfYear, dayOfMonth);
                actualizarFechaEnTextView();

                // Carga las reservas para la fecha seleccionada
                cargarReservasParaFechaSeleccionada();
            }
        }, year, month, day);

        // Muestra el cuadro de diálogo
        datePickerDialog.show();
    }

    // Método para actualizar el TextView con la fecha seleccionada
    private void actualizarFechaEnTextView() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaSeleccionada = sdf.format(calendar.getTime());
        nombreUsuario.setText("Fecha Seleccionada: " + fechaSeleccionada);
    }

    // Método para cargar las reservas para la fecha seleccionada
    private void cargarReservasParaFechaSeleccionada() {
        // Borra las reservas anteriores de la lista
        listaReservas.clear();
        reservasAdapter.notifyDataSetChanged();

        // Formatea la fecha seleccionada como "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fechaSeleccionada = sdf.format(calendar.getTime());

        // Consulta las reservas para la fecha seleccionada en Firebase
        db.collection("reservas")
                .whereEqualTo("fecha", fechaSeleccionada)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ReservasActivity reserva = document.toObject(ReservasActivity.class);
                            listaReservas.add(reserva);
                        }
                        // Notifica al adaptador que los datos han cambiado
                        reservasAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(PaginaTrabajadorActivity.this, "Error al cargar las reservas", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
