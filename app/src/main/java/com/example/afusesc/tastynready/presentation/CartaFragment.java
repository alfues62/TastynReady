package com.example.afusesc.tastynready.presentation;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class CartaFragment extends Fragment {

    //IMAGENES
    ImageView img_entrante;
    ImageView img_bebidas;
    ImageView img_aperitivos;
    ImageView img_postres;
    //RECYCLER
    RecyclerView recyclerView;
    ArrayList<Platos> platosArrayList;
    AdaptadorComidasFirestore adaptadorComidasFirestore;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carta, container, false);

        img_entrante = view.findViewById(R.id.img_entrante);
        img_bebidas = view.findViewById(R.id.img_bebidas);
        img_aperitivos = view.findViewById(R.id.img_aperitivos);
        img_postres = view.findViewById(R.id.img_postres);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();
        platosArrayList = new ArrayList<Platos>();
        adaptadorComidasFirestore = new AdaptadorComidasFirestore(getActivity(), platosArrayList);

        recyclerView.setAdapter(adaptadorComidasFirestore);


        img_entrante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Platos> entrantesList = filterPlatosByCategoria("Entrantes");
                adaptadorComidasFirestore.setPlatosList(entrantesList);
            }
        });

        img_bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Platos> bebidasList = filterPlatosByCategoria("Bebidas");
                adaptadorComidasFirestore.setPlatosList(bebidasList);
            }
        });

        img_aperitivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Platos> aperitivosList = filterPlatosByCategoria("Complementos");
                adaptadorComidasFirestore.setPlatosList(aperitivosList);
            }
        });

        img_postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Platos> postresList = filterPlatosByCategoria("Postres");
                adaptadorComidasFirestore.setPlatosList(postresList);
            }
        });

        EventChangeListener();

        return view;
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
                                platosArrayList.add(plato);
                                List<Platos> entrantesList = filterPlatosByCategoria("Entrantes");
                                adaptadorComidasFirestore.setPlatosList(entrantesList);

                            }
                            adaptadorComidasFirestore.notifyDataSetChanged();
                        }

                    }
                });

    }

    private List<Platos> filterPlatosByCategoria(String categoria) {
        List<Platos> filteredList = new ArrayList<>();
        for (Platos plato : platosArrayList) {
            if (plato.getCategoria().equals(categoria)) {
                filteredList.add(plato);
            }
        }
        return filteredList;
    }
}