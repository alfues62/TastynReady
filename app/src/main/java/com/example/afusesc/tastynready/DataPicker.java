package com.example.afusesc.tastynready;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DataPicker {
    private FirebaseFirestore db;
    private static Map<String, Object> userData;
    private static String selectedDate; // Agregado

    public DataPicker() {
        // Inicializa la instancia de Firebase
        db = FirebaseFirestore.getInstance();
    }

    public static Map<String, Object> obtenerDatosUsuario() {
        return userData;
    }

    public static String obtenerFechaSeleccionada() {
        return selectedDate;
    }

    public void guardarFechaSeleccionada(String fecha) {
        selectedDate = fecha;
    }

    public void guardarUsuarioEnVariable(FirebaseUser usuario) {
        // Crea un nuevo mapa para almacenar la información del usuario en la variable
        userData = new HashMap<>();
        userData.put("displayName", usuario.getDisplayName());
        userData.put("email", usuario.getEmail());
        userData.put("uid", usuario.getUid());

        // Guarda la información del usuario en la colección "usuarios" de Firestore
        db.collection("usuarios").document(usuario.getUid())
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    // Manejar el éxito, si es necesario
                })
                .addOnFailureListener(e -> {
                    // Manejar el error, si es necesario
                });
    }
}
