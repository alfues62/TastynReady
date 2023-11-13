package com.example.afusesc.tastynready;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHandler {

    private FirebaseFirestore db;
    private DataPicker dataPicker;

    public FirebaseHandler() {
        // Inicializa la instancia de Firebase
        db = FirebaseFirestore.getInstance();
        dataPicker = new DataPicker();
    }

    public void guardarReservaEnFirebase() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            // Si el usuario está autenticado, guarda su información en la variable userData de DataPicker
            dataPicker.guardarUsuarioEnVariable(usuario);

            // Obtén la información del usuario desde DataPicker
            Map<String, Object> usuarioInfo = DataPicker.obtenerDatosUsuario();

            // Obtén la fecha seleccionada desde DataPicker
            String fechaSeleccionada = DataPicker.obtenerFechaSeleccionada();

            // Guarda la reserva en Firebase
            Map<String, Object> reservaInfo = new HashMap<>();
            reservaInfo.put("Usuario", usuarioInfo.get("displayName"));
            reservaInfo.put("Sala", "ID_Sala_1");
            reservaInfo.put("Hora", "14:00:00");
            reservaInfo.put("Fecha", fechaSeleccionada);
            reservaInfo.put("Comensales", 4);

            // Puedes guardar la reserva en una colección "reservas" con un identificador único
            db.collection("reservas").document()
                    .set(reservaInfo)
                    .addOnSuccessListener(aVoid -> {
                        // Manejar el éxito, si es necesario
                    })
                    .addOnFailureListener(e -> {
                        // Manejar el error, si es necesario
                    });
        }
    }
}
