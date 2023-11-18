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
import com.example.afusesc.tastynready.model.Platos;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PedirActivity extends AppCompatActivity {

    //IMAGENES
    ImageView img_entrante;
    ImageView img_bebidas;
    ImageView img_aperitivos;
    ImageView img_postres;
    //RECYCLER
    RecyclerView recyclerView;
    ArrayList<Platos> pedidosArrayList;
    AdaptadorPedidosFirestore adaptadorPedidosFirestore;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_cont);

        img_entrante = findViewById(R.id.img_entrante);
        img_bebidas = findViewById(R.id.img_bebidas);
        img_aperitivos = findViewById(R.id.img_aperitivos);
        img_postres = findViewById(R.id.img_postres);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        pedidosArrayList = new ArrayList<Platos>();
        adaptadorPedidosFirestore = new AdaptadorPedidosFirestore(PedirActivity.this, pedidosArrayList);

        recyclerView.setAdapter(adaptadorPedidosFirestore);

        img_entrante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Platos> entrantesList = filterPlatosByCategoria("Entrantes");
                adaptadorPedidosFirestore.setPlatosList(entrantesList);
            }
        });

        img_bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Platos> bebidasList = filterPlatosByCategoria("Bebidas");
                adaptadorPedidosFirestore.setPlatosList(bebidasList);
            }
        });

        img_aperitivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Platos> aperitivosList = filterPlatosByCategoria("Complementos");
                adaptadorPedidosFirestore.setPlatosList(aperitivosList);
            }
        });

        img_postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Platos> postresList = filterPlatosByCategoria("Postres");
                adaptadorPedidosFirestore.setPlatosList(postresList);
            }
        });

        EventChangeListener();

    }

    private void EventChangeListener(){
        db.collection("Platos").orderBy("Categoria", Query.Direction.DESCENDING)
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
                                List<Platos> entrantesList = filterPlatosByCategoria("Entrantes");
                                adaptadorPedidosFirestore.setPlatosList(entrantesList);
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