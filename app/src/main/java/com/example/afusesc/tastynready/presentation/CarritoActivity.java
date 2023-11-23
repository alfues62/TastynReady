package com.example.afusesc.tastynready.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_carrito);

        button = findViewById(R.id.guardarReserva);
        sala = findViewById(R.id.cambSala);
        comensales = findViewById(R.id.cambComens);
        hora = findViewById(R.id.cambHora);
        fecha = findViewById(R.id.cambFecha);
        dataPicker = new DataPicker(); // Initialize DataPicker
        FirebaseHandler firebaseHandler = new FirebaseHandler();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Procede con la reserva ya que ambos campos están completos
                firebaseHandler.guardarReservaEnFirebase();
                // Llama al resetValues para restablecer los valores cuando retrocedes
                DataPicker.resetValues();
            }
        });

        sala.setText(DataPicker.obtenerIdSala());
        comensales.setText(String.valueOf(DataPicker.obtenerNumComensales()));
        fecha.setText(DataPicker.obtenerFechaSeleccionada());
        hora.setText(DataPicker.obtenerHoraSeleccionada());
    }

    private void subirPlatosAFirebase(ArrayList<Platos> pedidosArrayList){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        if (usuario != null) {
            // Si el usuario está autenticado, guarda su información en la variable userData de DataPicker
            dataPicker.guardarUsuarioEnFirebase();

            // Obtén la información del usuario desde DataPicker
            Map<String, Object> usuarioInfo = DataPicker.obtenerDatosUsuario();

            // Guarda la reserva en Firebase
            Map<String, Object> platoData = new HashMap<>();
            platoData.put("Usuario", usuarioInfo.get("displayName"));

            // Create a list to store information about each plate
            List<Map<String, Object>> platosList = new ArrayList<>();

            for (Platos plato : pedidosArrayList) {
                Map<String, Object> platoInfo = new HashMap<>();
                platoInfo.put("Nombre", plato.getNombre());
                platoInfo.put("Cantidad", plato.getCantidad());
                platoInfo.put("Precio", (plato.getPrecio()) * (plato.getCantidad()));

                // Add the plate information to the list
                platosList.add(platoInfo);
            }

            platoData.put("Platos", platosList);

            db.collection("reservaComida").document()
                    .set(platoData)
                    .addOnSuccessListener(aVoid -> {
                        // Manejar el éxito, si es necesario
                    })
                    .addOnFailureListener(e -> {
                        // Manejar el error, si es necesario
                    });
        }
    }
}
