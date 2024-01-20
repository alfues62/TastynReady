package com.example.afusesc.tastynready.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPicker {
    private FirebaseAuth mAuth;
    private static int numComensales;
    private static String idSala;
    private FirebaseFirestore db;
    private static Map<String, Object> userData;
    private static Map<String, Object> AdminData;

    private static String selectedDate;
    private static String selectedTime;
    private static List<Platos> selectedPlato;

    public DataPicker() {
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

    public static String obtenerHoraSeleccionada() {
        return selectedTime;
    }

    public void guardarHoraSeleccionada(String hora) {
        selectedTime = hora;
    }

    public static int obtenerNumComensales() {
        return numComensales;
    }

    public static void guardarNumComensales(int num) {
        numComensales = num;
    }

    public static String obtenerIdSala() {
        return idSala;
    }

    public static void guardarIdSala(String id) {
        idSala = id;
    }

    public static void guardarArray(List<Platos> platosSeleccionados) {
        selectedPlato = platosSeleccionados;
    }

    public static List<Platos> obtenerArray() {
        return selectedPlato;
    }

    // Actualizado para aceptar el rol del usuario
    public void guardarUsuarioEnFirebase(FirebaseUser usuario, String rol) {
        if (usuario != null) {
            userData = new HashMap<>();
            userData.put("displayName", usuario.getDisplayName());
            userData.put("email", usuario.getEmail());
            userData.put("uid", usuario.getUid());
            userData.put("rol", rol);  // Agrega la asignación del rol

            db.collection("usuarios").document(usuario.getUid())
                    .set(userData)
                    .addOnSuccessListener(aVoid -> {
                        // Manejar el éxito, si es necesario
                    })
                    .addOnFailureListener(e -> {
                        // Manejar el error, si es necesario
                        Log.e("DataPicker", "Error al guardar usuario en Firestore", e);
                    });
        }
    }


    public static void resetValues() {
        numComensales = 0;
        idSala = null;
        selectedDate = null;
        selectedTime = null;
    }
}
