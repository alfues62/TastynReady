package com.example.afusesc.tastynready;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHandler {

    private FirebaseFirestore db;

    public FirebaseHandler() {
        // Inicializa la instancia de Firebase
        db = FirebaseFirestore.getInstance();
    }

    public void guardarFechaEnFirebase(String fecha) {
        // Realiza la lógica para guardar la fecha en Firebase
        // Por ejemplo, podrías guardarla en un documento llamado "fechas" en una colección
        Map<String, Object> fechaMap = new HashMap<>();
        fechaMap.put("fecha", fecha);

        db.collection("fechas").add(fechaMap)
                .addOnSuccessListener(documentReference -> {
                    // Éxito al guardar la fecha
                    // Aquí puedes agregar más lógica si es necesario
                })
                .addOnFailureListener(e -> {
                    // Error al guardar la fecha
                    // Aquí puedes manejar el error según tus necesidades
                });
    }
}
