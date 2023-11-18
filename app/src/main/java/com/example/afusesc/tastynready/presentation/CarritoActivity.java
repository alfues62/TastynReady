package com.example.afusesc.tastynready.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.DataPicker;
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

    // EFECTUACION DE RESERVA
    private DataPicker dataPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_carrito);

        button = findViewById(R.id.guardarReserva);
        dataPicker = new DataPicker(); // Initialize DataPicker

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                if (intent != null) {
                    ArrayList<Platos> pedidosArrayList = (ArrayList<Platos>) intent.getSerializableExtra("pedidosArrayList");

                    if (pedidosArrayList != null) {
                        subirPlatosAFirebase(pedidosArrayList);
                    }
                }
            }
        });
    }

    private void subirPlatosAFirebase(ArrayList<Platos> pedidosArrayList){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        if (usuario != null) {
            // Si el usuario está autenticado, guarda su información en la variable userData de DataPicker
            dataPicker.guardarUsuarioEnFirebase(usuario);

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

            // Add the list of plates to the platoData map
            platoData.put("Platos", platosList);

            // Puedes guardar la reserva en una colección "reservas" con un identificador único
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
