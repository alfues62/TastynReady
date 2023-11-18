package com.example.afusesc.tastynready;

import android.provider.ContactsContract;

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
            dataPicker.guardarUsuarioEnFirebase(usuario);

            // Obtén la información del usuario desde DataPicker
            Map<String, Object> usuarioInfo = DataPicker.obtenerDatosUsuario();

            // Obtén la fecha seleccionada desde DataPicker
            String fechaSeleccionada = DataPicker.obtenerFechaSeleccionada();

            // Obtén la hora seleccionada desde DataPicker
            String horaSeleccionada = DataPicker.obtenerHoraSeleccionada();

            // Guarda la reserva en Firebase
            Map<String, Object> reservaInfo = new HashMap<>();
            reservaInfo.put("Usuario", usuarioInfo.get("displayName"));
            reservaInfo.put("Sala", "ID_Sala_1");
            reservaInfo.put("Hora", horaSeleccionada);
            reservaInfo.put("Fecha", fechaSeleccionada);
            reservaInfo.put("Comensales", 4);

            Map<String, Object> plato1 = new HashMap<>();
            plato1.put("Nombre", "Plato1");
            plato1.put("Descripcion", "Descripción del Plato1");
            plato1.put("Precio", 10.99);

            Map<String, Object> plato2 = new HashMap<>();
            plato2.put("Nombre", "Plato2");
            plato2.put("Descripcion", "Descripción del Plato2");
            plato2.put("Precio", 8.99);

            Map<String, Object> plato3 = new HashMap<>();
            plato3.put("Nombre", "Plato3");
            plato3.put("Descripcion", "Descripción del Plato3");
            plato3.put("Precio", 12.99);

            Map<String, Object> plato4 = new HashMap<>();
            plato4.put("Nombre", "Plato4");
            plato4.put("Descripcion", "Descripción del Plato4");
            plato4.put("Precio", 15.99);

            db.collection("Platos").document("Plato1").set(plato1);
            db.collection("Platos").document("Plato2").set(plato2);
            db.collection("Platos").document("Plato3").set(plato3);
            db.collection("Platos").document("Plato4").set(plato4);

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
