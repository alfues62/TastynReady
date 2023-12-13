package com.example.afusesc.tastynready.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHandler {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private DataPicker dataPicker;

    public FirebaseHandler() {
        // Inicializa la instancia de Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        dataPicker = new DataPicker();
    }

    public void guardarReservaEnFirebase() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario == null) {
            Log.e("FirebaseHandler", "El usuario es nulo. No se puede guardar la reserva.");
            // Aquí puedes redirigir al usuario a la pantalla de inicio de sesión si es necesario.
            return;
        }

        // Asegúrate de que el objeto DataPicker se haya inicializado correctamente.
        if (dataPicker == null) {
            Log.e("FirebaseHandler", "Error: DataPicker no inicializado correctamente.");
            return;
        }

        Map<String, Object> usuarioInfo = dataPicker.obtenerDatosUsuario();
        String fechaSeleccionada = dataPicker.obtenerFechaSeleccionada();
        String horaSeleccionada = dataPicker.obtenerHoraSeleccionada();
        int numComensales = dataPicker.obtenerNumComensales();
        String idSala = dataPicker.obtenerIdSala();
        List<Platos> platosList = dataPicker.obtenerArray();


        String IDReserva = usuarioInfo.get("displayName") +""+  fechaSeleccionada;

        Map<String, Object> reservaInfo = new HashMap<>();
        reservaInfo.put("Usuario", usuarioInfo.get("displayName"));
        reservaInfo.put("Sala", idSala);
        reservaInfo.put("Hora", horaSeleccionada);
        reservaInfo.put("Fecha", fechaSeleccionada);
        reservaInfo.put("Comensales", numComensales);

        List<Map<String, Object>> todosPlatos = new ArrayList<>();

        for (Platos platos : platosList) {
            Map<String, Object> platoInfo = new HashMap<>();
            platoInfo.put("Nombre", platos.getNombre());
            platoInfo.put("Cantidad", platos.getCantidad());
            platoInfo.put("Precio", (platos.getPrecio()) * (platos.getCantidad()));
            todosPlatos.add(platoInfo);
        }

        reservaInfo.put("zPlatos", todosPlatos);

        db.collection("reservas").document(IDReserva).set(reservaInfo)
                .addOnSuccessListener(aVoid -> {
                    // Manejar el éxito, si es necesario
                })
                .addOnFailureListener(e -> {
                    // Manejar el error, si es necesario
                    Log.e("FirebaseHandler", "Error al guardar reserva en Firestore", e);
                });

        // Crear clave única para la disponibilidad

    }
}

