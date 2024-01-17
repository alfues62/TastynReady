package com.example.afusesc.tastynready;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.model.Reserva;
import com.example.afusesc.tastynready.presentation.RegisterTrabajador;
import com.example.afusesc.tastynready.presentation.ReservasTrabajadorAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservasTrabajadorAdapter adapter;
    private Button botonSeleccionarDia;
    private TextView mensajeSinReservas;
    private List<Reserva> listaReservas;
    private List<Reserva> reservasFiltradas;
    private Calendar fechaSeleccionada;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Maneja los clics en los elementos del menú
        if (item.getItemId() == R.id.action_otra_actividad) {
            // Abre la otra actividad aquí
            abrirOtraActividad();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void abrirOtraActividad() {
        // Crea un Intent para abrir la otra actividad
        Intent intent = new Intent(this, RegisterTrabajador.class);
        startActivity(intent);
    }

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaReservas = new ArrayList<>();
        mensajeSinReservas = findViewById(R.id.mensaje_sin_reservas);

        recyclerView = findViewById(R.id.recycler_reservas);
        adapter = new ReservasTrabajadorAdapter(this, listaReservas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        botonSeleccionarDia = findViewById(R.id.boton_seleccionarDia);
        botonSeleccionarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorDeDia();
            }
        });

        // Obtener reservas desde Firebase
        obtenerReservasDesdeFirebase();

        // Inicializar fecha seleccionada al día actual
        fechaSeleccionada = Calendar.getInstance();
    }

    private void obtenerReservasDesdeFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reservasCollection = db.collection("reservas");

        reservasCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listaReservas.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    Reserva reserva = document.toObject(Reserva.class);
                    listaReservas.add(reserva);
                }

                // Filtrar las reservas por la fecha seleccionada
                filtrarReservasPorFecha(fechaSeleccionada);
            } else {
                // Manejar errores si es necesario
            }
        });
    }

    private void mostrarSelectorDeDia() {
        int anio = fechaSeleccionada.get(Calendar.YEAR);
        int mes = fechaSeleccionada.get(Calendar.MONTH);
        int dia = fechaSeleccionada.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                        fechaSeleccionada.set(anio, mes, dia);
                        filtrarReservasPorFecha(fechaSeleccionada);
                    }
                }, anio, mes, dia);

        datePickerDialog.show();
    }

    private void filtrarReservasPorFecha(Calendar fechaSeleccionada) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fechaSeleccionadaString = formatoFecha.format(fechaSeleccionada.getTime());

        // Crear una nueva lista filtrada para la fecha seleccionada
        reservasFiltradas = new ArrayList<>();
        for (Reserva reserva : listaReservas) {
            if (reserva.getFecha().equals(fechaSeleccionadaString)) {
                reservasFiltradas.add(reserva);
            }
        }

        // Actualizar el adaptador con las reservas filtradas
        adapter.setReservas(reservasFiltradas);

        // Mostrar mensaje si no hay reservas para el día seleccionado
        if (reservasFiltradas.isEmpty()) {
            mensajeSinReservas.setVisibility(View.VISIBLE);
        } else {
            mensajeSinReservas.setVisibility(View.GONE);
        }
    }
}