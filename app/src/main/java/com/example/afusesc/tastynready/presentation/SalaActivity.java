package com.example.afusesc.tastynready.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SalaActivity extends AppCompatActivity {

    TextView temperatura;
    Button calida, tenue, fria;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Temperatura");
        DatabaseReference luzSala1Ref = database.getReference("salas/sala1/led");

        temperatura = findViewById(R.id.cambiTemperatura);
        calida = findViewById(R.id.luzCalida);
        tenue = findViewById(R.id.luzTenue);
        fria = findViewById(R.id.luzFria);

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
    }
}
