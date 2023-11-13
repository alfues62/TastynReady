package com.example.afusesc.tastynready;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;
import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;import com.firebase.ui.auth.AuthUI;
import com.google.firebase.firestore.FirebaseFirestore;


public class ReservasActivity extends AppCompatActivity {

    DataPicker dataPicker = new DataPicker();

    //BBDD
    private FirebaseFirestore db; // Variable de clase
    FirebaseHandler firebaseHandler = new FirebaseHandler();

    //INPUT STEPPER
    private EditText valueEditText;
    private Button incrementButton;
    private Button decrementButton;
    private int value = 0;
    private static final int MAX_VALUE = 20;

    //HORA Y FECHA
    private TextView textHora;
    private Button btnHora;
    private TextView textFecha;
    private Button btnFecha;

    //BACK
    private ImageView back;
    private Button next;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_main);

        //DECLARACIONES
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
                if (value > 0) {
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
                firebaseHandler.guardarReservaEnFirebase();
                Intent intent = new Intent(ReservasActivity.this, PedirActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openDatePicker() {
        // Obtén la fecha actual
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Establece la fecha mínima como mañana
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        int minYear = calendar.get(Calendar.YEAR);
        int minMonth = calendar.get(Calendar.MONTH);
        int minDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Actualiza la instancia de Calendar con la fecha seleccionada
                calendar.set(year, month, day);

                // Verifica si la fecha seleccionada es en el pasado
                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    // No permitir selección de fechas pasadas
                    return;
                }

                // Muestra la fecha seleccionada
                textFecha.setText("Fecha Seleccionada: " + String.valueOf(year) + "." + String.valueOf(month + 1) + "." + String.valueOf(day));

                // Formatea la fecha como "yyyy-MM-dd"
                String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, day);

                // Llama al método para guardar la fecha en DataPicker
                dataPicker.guardarFechaSeleccionada(formattedDate);

            }
        }, currentYear, currentMonth, currentDay);

        // Establece la fecha mínima como mañana
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

    private void openTimePicker(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                textHora.setText("Hora Seleccionada: " + String.valueOf(hour)+ ":"+String.valueOf(minute));

            }
        }, 15, 30, false);

        timePickerDialog.show();
    }

}
