package com.example.afusesc.tastynready.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHandler {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private DataPicker dataPicker;
    private DatabaseReference databaseReference;


    public FirebaseHandler() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        dataPicker = new DataPicker();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    public void actualizarNotificacion(String idAdministrador) {
        // Construye la ruta completa en la base de datos
        String rutaAdmin = "administradores/" + idAdministrador + "/notificacion";

        // Actualiza el valor a "true"
        databaseReference.child(rutaAdmin).setValue(true);

    }

    public void guardarReservaEnFirebase() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario == null) {
            Log.e("FirebaseHandler", "El usuario es nulo. No se puede guardar la reserva.");
            return;
        }

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

        String claveDisponibilidad = idSala + " " +
                dataPicker.obtenerHoraSeleccionada() + " " +
                dataPicker.obtenerFechaSeleccionada();

        agregarNuevaReservacion(claveDisponibilidad);
        String IDReserva = usuarioInfo.get("displayName") + " - " + fechaSeleccionada;

        Map<String, Object> reservaInfo = new HashMap<>();
        reservaInfo.put("Usuario", usuarioInfo.get("displayName"));
        reservaInfo.put("Sala", idSala);
        reservaInfo.put("Hora", horaSeleccionada);
        reservaInfo.put("Fecha", fechaSeleccionada);
        reservaInfo.put("Comensales", numComensales);
        reservaInfo.put("IdUser", usuarioInfo.get("uid"));

        List<Map<String, Object>> todosPlatos = new ArrayList<>();

        for (Platos platos : platosList) {
            Map<String, Object> platoInfo = new HashMap<>();
            platoInfo.put("Nombre", platos.getNombre());
            platoInfo.put("Cantidad", platos.getCantidad());
            platoInfo.put("Precio", (platos.getPrecio()) * (platos.getCantidad()));
            todosPlatos.add(platoInfo);
        }

        reservaInfo.put("zPlatos", todosPlatos);
        reservaInfo.put("IdUser", usuario.getUid());

        db.collection("reservas").document(IDReserva).set(reservaInfo)
                .addOnSuccessListener(aVoid -> {
                    // Manejar el éxito, si es necesario
                })
                .addOnFailureListener(e -> {
                    // Manejar el error, si es necesario
                    Log.e("FirebaseHandler", "Error al guardar reserva en Firestore", e);
                });
    }

    private void agregarNuevaReservacion(String claveDisponibilidad) {
        Map<String, Object> disponibilidad = new HashMap<>();
        disponibilidad.put("Reserva", "Sala: " + dataPicker.obtenerIdSala() + " -Fecha: " +
                dataPicker.obtenerHoraSeleccionada() + " -Hora: " + dataPicker.obtenerFechaSeleccionada());

        db.collection("disponibilidad").document(claveDisponibilidad).set(disponibilidad)
                .addOnSuccessListener(aVoid -> {
                    // Reserva agregada con éxito, manejar el éxito si es necesario
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseHandler", "Error al guardar disponibilidad en Firestore", e);
                });
    }

}
