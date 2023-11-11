package com.example.afusesc.tastynready;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservasActivity extends AppCompatActivity {

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

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Verifica si la fecha seleccionada es el mismo día
                if (year == currentYear && month == currentMonth && day == currentDay) {
                    // No permitir selección del mismo día
                    return;
                }

                // Verifica si el día seleccionado es domingo (domingo tiene valor 1 en Calendar.DAY_OF_WEEK)
                calendar.set(year, month, day);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == Calendar.SUNDAY) {
                    // No permitir selección de domingo
                    return;
                }

                // Verifica si la fecha seleccionada es en el pasado
                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    // No permitir selección de fechas pasadas
                    return;
                }

                // Muestra la fecha seleccionada
                textFecha.setText("Fecha Seleccionada: " + String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(day));
            }
        }, currentYear, currentMonth, currentDay);

        // Desactiva la selección de fechas pasadas
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        // Personaliza el color de los domingos
        datePickerDialog.getDatePicker().setCalendarViewShown(false);

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
