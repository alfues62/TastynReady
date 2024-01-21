package com.example.afusesc.tastynready.presentation;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.example.afusesc.tastynready.model.DataPicker;
import com.example.afusesc.tastynready.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.afusesc.tastynready.model.FirebaseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ReservasActivity extends AppCompatActivity {

    DataPicker dataPicker = new DataPicker();

    private FirebaseFirestore db;
    FirebaseHandler firebaseHandler = new FirebaseHandler();

    private EditText valueEditText;
    private Button incrementButton;
    private Button decrementButton;
    private int value = 2;
    private static final int MAX_VALUE = 8;

    private String salaReservada;

    private TextView textHora;
    private Button btnHora;
    private TextView textFecha;
    private Button btnFecha;

    private ImageView back;
    private Button next;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        valueEditText = findViewById(R.id.valueEditText);
        incrementButton = findViewById(R.id.incrementButton);
        decrementButton = findViewById(R.id.decrementButton);
        valueEditText.setText(String.valueOf(value));

        textHora = findViewById(R.id.text_hora);
        btnHora = findViewById(R.id.timeButton);
        textFecha = findViewById(R.id.text_fecha);
        btnFecha = findViewById(R.id.fechaButton);

        back = findViewById(R.id.imageView2);
        next = findViewById(R.id.BotonContinuar);

        db = FirebaseFirestore.getInstance();
        eliminarReservasPasadas();

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value < MAX_VALUE) {
                    value++;
                    valueEditText.setText(String.valueOf(value));
                }
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value > 2) {
                    value--;
                    valueEditText.setText(String.valueOf(value));
                }
            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker();
            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReservasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataPicker.obtenerFechaSeleccionada() == null || DataPicker.obtenerHoraSeleccionada() == null) {
                    mostrarDialogoCamposFaltantes();
                    return;
                }


                // Convierte el valor de valueEditText a un entero
                int numComensales = Integer.parseInt(valueEditText.getText().toString());

                if (numComensales > 4) {
                    salaReservada = "ID_Sala_2";
                } else {
                    salaReservada = "ID_Sala_1";
                }

                DataPicker.guardarNumComensales(numComensales);
                DataPicker.guardarIdSala(salaReservada);


                comprobarDisponibilidad();


            }
        });


        DataPicker.resetValues();

        //Obtener fechaMañana
        Calendar calendarioManana = obtenerCalendarioManana();

        // Formato de fecha deseado (puedes ajustar según tus necesidades).
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Convertir el objeto Calendar a String.
        Log.d("TAG", formatoFecha.format(calendarioManana.getTime()));


    }
    private void eliminarReservasPasadas() {
        // Obtener la fecha actual
        Date fechaActual = Calendar.getInstance().getTime();

        // Obtener referencia a la colección de reservas en Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reservasRef = db.collection("reservas");

        // Consultar las reservas que tienen una fecha menor que la actual
        reservasRef.whereLessThan("Fecha", fechaActual)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Recorrer los documentos y eliminar las reservas pasadas
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Obtener la fecha de la reserva del documento
                            String fechaReservaStr = document.getString("Fecha"); // Ajustar el nombre del campo si es diferente

                            if (fechaReservaStr != null) {
                                // Convertir la fecha de Firestore (en formato de cadena) a un objeto Date
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                try {
                                    Date fechaReserva = sdf.parse(fechaReservaStr);

                                    // Comparar la fecha y eliminar la reserva si es pasada
                                    if (fechaReserva != null && fechaReserva.before(fechaActual)) {
                                        eliminarReserva(document.getId());
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        Log.e(TAG, "Error obteniendo documentos", task.getException());
                    }
                });
    }

    private void eliminarReserva(String reservaId) {
        // Obtener referencia al documento de la reserva en Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference reservaDocRef = db.collection("reservas").document(reservaId);

        // Eliminar la reserva
        reservaDocRef.delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Reserva eliminada correctamente");
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error al eliminar la reserva", e);
                });
    }
    public void comprobarDisponibilidad() {
        String claveDisponibilidad = DataPicker.obtenerIdSala() + " " +
                DataPicker.obtenerHoraSeleccionada() + " " +
                DataPicker.obtenerFechaSeleccionada();

        db.collection("disponibilidad").document(claveDisponibilidad)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            mostrarPopupReservado();
                        } else {
                            Intent intent = new Intent(ReservasActivity.this, PedirActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Log.e("FirebaseHandler", "Error al verificar disponibilidad en Firestore", task.getException());
                    }
                });
    }

    private void mostrarPopupReservado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sala Reservada");
        builder.setMessage("Lo siento, la sala ya está reservada en la fecha y hora seleccionadas.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                DataPicker.resetValues();
                textFecha.setText("Fecha Seleccionada: ");
                textHora.setText("Hora Seleccionada: ");
            }
        });
        builder.show();
    }

    private void mostrarDialogoCamposFaltantes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Campos faltantes");
        builder.setMessage("Por favor, completa la fecha y la hora antes de continuar.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);

                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    return;
                }

                textFecha.setText("Fecha Seleccionada: " + String.valueOf(year) + "." + String.valueOf(month + 1) + "." + String.valueOf(day));
                String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, day);
                dataPicker.guardarFechaSeleccionada(formattedDate);
            }
        }, currentYear, currentMonth, currentDay);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona la hora de la reserva");

        final NumberPicker hourPicker = new NumberPicker(this);
        hourPicker.setMinValue(9);
        hourPicker.setMaxValue(21);
        hourPicker.setValue(currentHour);

        builder.setView(hourPicker);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hour = hourPicker.getValue();
                textHora.setText("Hora Seleccionada: " + String.valueOf(hour) + ":00");
                String formattedTime = String.format("%02d:00", hour);
                dataPicker.guardarHoraSeleccionada(formattedTime);
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // Método para obtener el calendario correspondiente a mañana.
    private Calendar obtenerCalendarioManana() {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_YEAR, 1);
        return calendario;
    }




}
