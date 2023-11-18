package com.example.afusesc.tastynready.presentation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD:app/src/main/java/com/example/afusesc/tastynready/presentation/ReservasActivity.java
=======
import android.provider.ContactsContract;
import android.util.Log;
>>>>>>> HaoXu:app/src/main/java/com/example/afusesc/tastynready/ReservasActivity.java
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

<<<<<<< HEAD:app/src/main/java/com/example/afusesc/tastynready/presentation/ReservasActivity.java
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
>>>>>>> HaoXu:app/src/main/java/com/example/afusesc/tastynready/ReservasActivity.java
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.model.DataPicker;
import com.example.afusesc.tastynready.model.FirebaseHandler;
import com.example.afusesc.tastynready.R;

import java.util.Calendar;

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
    private static final int MAX_VALUE = 8;

    private String salaReservada;

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
                // Verifica si se ha seleccionado la fecha y la hora
                if (dataPicker.obtenerFechaSeleccionada() == null || dataPicker.obtenerHoraSeleccionada() == null) {
                    // Muestra un cuadro de diálogo para informar al usuario
                    mostrarDialogoCamposFaltantes();
                    return;
                }

                // Convierte el valor de valueEditText a un entero
                int numComensales = Integer.parseInt(valueEditText.getText().toString());

                if (numComensales > 4){
                    salaReservada = "ID_Sala_2";
                }else {
                    salaReservada = "ID_Sala_1";
                }
                //Llama al DataPicker para guardar el número de comensales
                DataPicker.guardarNumComensales(numComensales);
                DataPicker.guardarIdSala(salaReservada);

                // Procede con la reserva ya que ambos campos están completos
                firebaseHandler.guardarReservaEnFirebase();

                // Llama al resetValues para restablecer los valores cuando retrocedes
                DataPicker.resetValues();

                Intent intent = new Intent(ReservasActivity.this, PedirActivity.class);
                startActivity(intent);
            }
        });
    }
    private void mostrarDialogoCamposFaltantes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Campos faltantes");
        builder.setMessage("Por favor, completa la fecha y la hora antes de continuar.");

        // Agrega un botón de OK para cerrar el cuadro de diálogo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cierra el cuadro de diálogo
                dialogInterface.dismiss();
            }
        });

        // Muestra el cuadro de diálogo
        builder.show();
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

    private void openTimePicker() {
        // Obtén la hora actual
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        // Crear un cuadro de diálogo personalizado para mostrar solo la hora
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona la hora de la reserva");

        // Crear un NumberPicker personalizado para seleccionar las horas
        final NumberPicker hourPicker = new NumberPicker(this);
        hourPicker.setMinValue(9);
        hourPicker.setMaxValue(21);
        hourPicker.setValue(currentHour);

        builder.setView(hourPicker);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hour = hourPicker.getValue();

                // Muestra la hora seleccionada
                textHora.setText("Hora Seleccionada: " + String.valueOf(hour) + ":00");

                // Formatea la hora como "HH:00"
                String formattedTime = String.format("%02d:00", hour);

                // Llama al método para guardar la hora en DataPicker
                dataPicker.guardarHoraSeleccionada(formattedTime);
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
