package com.example.afusesc.tastynready.presentation;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.FirebaseHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SalaActivity extends AppCompatActivity {
    TextView temperatura;
    Button calida, tenue, fria;
    Button botonCamarero; //Llamar al camarero
    private DatabaseReference databaseReference;

    //INPUT STEPPER
    private EditText valueEditText;
    private Button incrementButton;
    private Button decrementButton;
    private int value = 10;
    private int value2 = 20;
    private static final int MAX_VALUE = 50;

    private Button enviar;

    private EditText valueEditText2;
    private Button incrementButton2;
    private Button decrementButton2;
    private Button enviar2;
    private AlertDialog alertDialog;

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje recibido");
        builder.setMessage("En breve, podrás apreciar el cambio de temperatura!");

        // Add an OK button to close the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Close the dialog
                alertDialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Temperatura");
        DatabaseReference luzSala1Ref = database.getReference("salas/sala1/led");
        DatabaseReference intSala1Ref = database.getReference("salas/sala1/intensidad");

        temperatura = findViewById(R.id.cambiTemperatura);
        calida = findViewById(R.id.luzCalida);
        tenue = findViewById(R.id.luzTenue);
        fria = findViewById(R.id.luzFria);

        valueEditText = findViewById(R.id.cantidad);
        incrementButton = findViewById(R.id.incrementButton);
        decrementButton = findViewById(R.id.decrementButton);
        valueEditText.setText(String.valueOf(value));

        valueEditText2 = findViewById(R.id.cantidad2);
        incrementButton2 = findViewById(R.id.incrementButton2);
        decrementButton2 = findViewById(R.id.decrementButton2);
        valueEditText2.setText(String.valueOf(value2));

        enviar = findViewById(R.id.enviar);

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

        incrementButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value2 < 35) {
                    value2++;
                    valueEditText2.setText(String.valueOf(value2));
                }
            }
        });

        decrementButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value2 > 15) {
                    value2--;
                    valueEditText2.setText(String.valueOf(value2));
                }
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String send = String.valueOf(valueEditText.getText());
                intSala1Ref.setValue(send);
            }
        });

        enviar2 = findViewById(R.id.enviar2);

        enviar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        //Llamar al camarero
        botonCamarero = findViewById(R.id.botonCamarero);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Valor de la temperatura
                String valorCampo = dataSnapshot.getValue(String.class);
                // Actualiza el TextView con el valor obtenido
                temperatura.setText(valorCampo);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejar errores de lectura de la base de datos, si es necesario
            }
        });

        calida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valorAEnviar = "calida";

                // Envía el valor a la base de datos
                luzSala1Ref.setValue(valorAEnviar);
            }
        });

        fria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valorAEnviar = "fria";

                // Envía el valor a la base de datos
                luzSala1Ref.setValue(valorAEnviar);
            }
        });

        tenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valorAEnviar = "tenue";

                // Envía el valor a la base de datos
                luzSala1Ref.setValue(valorAEnviar);
            }
        });


        //Llamar al camarero
        botonCamarero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idAdministrador = "XAILBTk7e8GkhOUy2HKl";

                // Crea una instancia del AdminDatabaseManager
                FirebaseHandler adminDatabaseManager = new FirebaseHandler();

                // Llama al método para actualizar la notificación
                adminDatabaseManager.actualizarNotificacion(idAdministrador);

            }
        });
    }

}
