package com.example.afusesc.tastynready.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.DataPicker;
import com.example.afusesc.tastynready.model.Platos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedirActivity extends AppCompatActivity {

    //DECLARACION DE VARIABLES

    //RECYCLER
    RecyclerView recyclerView;
    ArrayList<Platos> pedidosArrayList;
    AdaptadorPedidosFirestore adaptadorPedidosFirestore;
    FirebaseFirestore db;

<<<<<<< Updated upstream


=======
<<<<<<< Updated upstream


=======
>>>>>>> Stashed changes
>>>>>>> Stashed changes
    // CODIGO _________________________________________________________________________________________________
    private void guardarPlatosSeleccionados() {
        List<Platos> platosSeleccionados = adaptadorPedidosFirestore.obtenerPlatosConCantidadMayorACero();
        Log.d("PlatosSeleccionados", "Número de platos seleccionados: " + platosSeleccionados.size());
        for (Platos plato : platosSeleccionados) {
            Log.d("PlatoSeleccionado", "Nombre: " + plato.getNombre() +
                    ", Cantidad: " + plato.getCantidad() +
                    ", Precio: " + (plato.getPrecio()) * (plato.getCantidad()));
        }

<<<<<<< Updated upstream
        Intent intent = new Intent(this, CarritoActivity.class);
        intent.putExtra("pedidosArrayList", new ArrayList<>(platosSeleccionados));
=======
<<<<<<< Updated upstream
        Intent intent = new Intent(this, CarritoActivity.class);
        intent.putExtra("pedidosArrayList", new ArrayList<>(platosSeleccionados));
=======
        DataPicker.guardarArray(platosSeleccionados);

        Intent intent = new Intent(this, CarritoActivity.class);
        //intent.putExtra("pedidosArrayList", new ArrayList<>(platosSeleccionados));
>>>>>>> Stashed changes
>>>>>>> Stashed changes
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_cont);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        pedidosArrayList = new ArrayList<Platos>();
        adaptadorPedidosFirestore = new AdaptadorPedidosFirestore(PedirActivity.this, pedidosArrayList);

        recyclerView.setAdapter(adaptadorPedidosFirestore);

        Button btnGuardarPlatos = findViewById(R.id.guardar);
        btnGuardarPlatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPlatosSeleccionados();
            }
        });

        EventChangeListener();

    }

    private void EventChangeListener(){
        db.collection("Platos").orderBy("Categoria", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                Platos plato = dc.getDocument().toObject(Platos.class);
                                plato.setImg(dc.getDocument().getString("img"));
                                pedidosArrayList.add(plato);
                            }
                            adaptadorPedidosFirestore.notifyDataSetChanged();
                        }

                    }
                });

    }

    private List<Platos> filterPlatosByCategoria(String categoria) {
        List<Platos> filteredList = new ArrayList<>();
        for (Platos plato : pedidosArrayList) {
            if (plato.getCategoria().equals(categoria)) {
                filteredList.add(plato);
            }
        }
        return filteredList;
    }
}