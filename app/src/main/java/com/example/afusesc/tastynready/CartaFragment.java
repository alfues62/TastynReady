package com.example.afusesc.tastynready;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CartaFragment extends Fragment {

    ImageView imgEntrantes;
    ImageView imgComidas;
    ImageView imgAperitivos;
    ImageView imgBebidas;
    ImageView imgPostres;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carta, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        imgEntrantes = view.findViewById(R.id.img_entrante);
        imgComidas = view.findViewById(R.id.img_comidas);
        imgAperitivos = view.findViewById(R.id.img_aperitivos);
        imgBebidas = view.findViewById(R.id.img_bebidas);
        imgPostres = view.findViewById(R.id.img_postres);

        imgEntrantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorComidas.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorComidas.MyItem("Elemento 1", R.drawable.headerbkg));
                itemList.add(new AdaptadorComidas.MyItem("Elemento 2", R.drawable.headerbkg));
                // Agrega más elementos...

                AdaptadorComidas adapter = new AdaptadorComidas(requireContext(), itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapter);
            }
        });

        imgComidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorComidas.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorComidas.MyItem("Elemento 1", R.drawable.headerbkg));
                itemList.add(new AdaptadorComidas.MyItem("Elemento 2", R.drawable.headerbkg));
                // Agrega más elementos...

                AdaptadorComidas adapter = new AdaptadorComidas(requireContext(), itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapter);
            }
        });

        imgAperitivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorComidas.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorComidas.MyItem("Elemento 1", R.drawable.headerbkg));
                itemList.add(new AdaptadorComidas.MyItem("Elemento 2", R.drawable.headerbkg));
                // Agrega más elementos...

                AdaptadorComidas adapter = new AdaptadorComidas(requireContext(), itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapter);
            }
        });

        imgBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorComidas.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorComidas.MyItem("Elemento 1", R.drawable.headerbkg));
                itemList.add(new AdaptadorComidas.MyItem("Elemento 2", R.drawable.headerbkg));
                // Agrega más elementos...

                AdaptadorComidas adapter = new AdaptadorComidas(requireContext(), itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapter);
            }
        });

        imgPostres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorComidas.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorComidas.MyItem("Postre 1", R.drawable.fondo_app));
                itemList.add(new AdaptadorComidas.MyItem("Postre 2", R.drawable.headerbkg));
                // Agrega más elementos...

                AdaptadorComidas adapter = new AdaptadorComidas(requireContext(), itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapter);
            }
        });

        List<AdaptadorComidas.MyItem> itemList = new ArrayList<>();
        itemList.add(new AdaptadorComidas.MyItem("Postre 1", R.drawable.fondo_app));
        itemList.add(new AdaptadorComidas.MyItem("Postre 2", R.drawable.headerbkg));
        // Agrega más elementos...

        AdaptadorComidas adapter = new AdaptadorComidas(requireContext(), itemList);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}