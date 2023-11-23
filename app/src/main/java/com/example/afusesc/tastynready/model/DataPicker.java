package com.example.afusesc.tastynready.model;

import static android.content.ContentValues.TAG;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPicker {
    private FirebaseAuth mAuth;
    private static int numComensales;
    private static UsuarioInfo usuarioRegistrado;
    private static String idSala;
    private FirebaseFirestore db;
    private static Map<String, Object> userData;
    private static String selectedDate;
    private static String selectedTime;
    private static List<Platos> selectedPlato;

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
    public void guardarUsuarioEnFirebase(FirebaseUser usuario) {
        // Asegúrate de que el usuario esté registrado antes de intentar guardarlo
        if (usuario != null) {
            // Crea un nuevo mapa para almacenar la información del usuario en la variable
            userData = new HashMap<>();
            userData.put("nombre", usuario.getDisplayName());
            userData.put("email", usuario.getEmail());
            userData.put("uid", usuario.getUid());

            // Guarda la información del usuario en la colección "usuarios" de Firestore
            db.collection("usuarios").document(usuario.getUid())
                    .set(userData)
                    .addOnSuccessListener(aVoid -> {
                        // Manejar el éxito, si es necesario
                        Log.d(TAG, "Usuario guardado en Firestore con éxito");
                    })
                    .addOnFailureListener(e -> {
                        // Manejar el error, si es necesario
                        Log.e(TAG, "Error al guardar usuario en Firestore", e);
                    });
        } else {
            Log.e(TAG, "Usuario no registrado. No se puede guardar en Firestore.");
        }
    }
    public static String obtenerRolUsuario() {
        if (usuarioRegistrado != null) {
            return usuarioRegistrado.getRol();
        }
        return null;
    }
    public static void resetValues() {
        numComensales = 0;
        idSala = null;
        selectedDate = null;
        selectedTime = null;
    }
}
