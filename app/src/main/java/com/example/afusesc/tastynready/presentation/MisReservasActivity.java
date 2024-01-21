package com.example.afusesc.tastynready.presentation;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.DataPicker;
import com.example.afusesc.tastynready.model.Reserva;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MisReservasActivity extends AppCompatActivity {

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAMainActivity();
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("reservas")
                    .whereEqualTo("IdUser", userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<Reserva> reservasList = new ArrayList<>();

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Reserva reserva = document.toObject(Reserva.class);
                                    // Obtener la fecha de la reserva como String
                                    String fechaReservaStr = reserva.getFecha();

                                    // Convertir el String a LocalDate (ajusta el formato según el de tu aplicación)
                                    LocalDate fechaReserva = parseFecha(fechaReservaStr);

                                    // Obtener la fecha actual
                                    LocalDate fechaActual = LocalDate.now();

                                    // Comparar las fechas
                                    if (fechaReserva != null && !fechaReserva.isBefore(fechaActual)) {
                                        reservasList.add(reserva);
                                    }
                                }

                                // Configura el RecyclerView con las reservas del usuario activo
                                setupRecyclerView(reservasList);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    private LocalDate parseFecha(String fechaStr) {
        return LocalDate.parse(fechaStr);
    }

    public void onItemClick(Reserva reserva) {
        Intent intent = new Intent(this, SalaActivity.class);
        startActivity(intent);
    }

    private void setupRecyclerView(List<Reserva> reservasList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewReservas);

        // Pasa 'this' como OnItemClickListener
        ReservaAdapter adapter = new ReservaAdapter(reservasList, this::onItemClick);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void irAMainActivity() {
        Intent intent = new Intent(MisReservasActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}