package com.example.afusesc.tastynready.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.afusesc.tastynready.R;

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
        imgAperitivos = view.findViewById(R.id.img_aperitivos);
        imgBebidas = view.findViewById(R.id.img_bebidas);
        imgPostres = view.findViewById(R.id.img_postres);

        imgEntrantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorComidas.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorComidas.MyItem("Filete de Ternera con Salsa de Champiñones", R.drawable.img_bistec,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Pasta Alfredo con Pollo", R.drawable.img_spaghet,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Ensalada de Salmón Ahumado", R.drawable.img_salad,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Tacos de Camarones al Chipotle", R.drawable.headerbkg,"20 €"));
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
                itemList.add(new AdaptadorComidas.MyItem("Alitas de Pollo Buffalo", R.drawable.img_pollo,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Nachos Supremos", R.drawable.img_nacho,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Brochetas de Camarones y Piña", R.drawable.img_brocheta,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Tacos de Barbacoa", R.drawable.img_taco,"20 €"));
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
                itemList.add(new AdaptadorComidas.MyItem("Agua Mineral Natural", R.drawable.img_awa,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Vino Tinto Malbec", R.drawable.img_wine,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Refresco de Limón Casero", R.drawable.img_limon,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Coca-Cola", R.drawable.headerbkg,"20 €"));
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
                itemList.add(new AdaptadorComidas.MyItem("Tarta de Chocolate Decadente", R.drawable.img_cake,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Tiramisú", R.drawable.img_tiramisu,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Crema Catalana", R.drawable.img_crema,"20 €"));
                itemList.add(new AdaptadorComidas.MyItem("Café Espresso", R.drawable.img_cafe,"20 €"));
                // Agrega más elementos...

                AdaptadorComidas adapter = new AdaptadorComidas(requireContext(), itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapter);
            }
        });

        List<AdaptadorComidas.MyItem> itemList = new ArrayList<>();
        itemList.add(new AdaptadorComidas.MyItem("Filete de Ternera con Salsa de Champiñones", R.drawable.img_bistec,"20 €"));
        itemList.add(new AdaptadorComidas.MyItem("Pasta Alfredo con Pollo", R.drawable.img_spaghet,"20 €"));
        itemList.add(new AdaptadorComidas.MyItem("Ensalada de Salmón Ahumado", R.drawable.img_salad,"20 €"));
        itemList.add(new AdaptadorComidas.MyItem("Tacos de Camarones al Chipotle", R.drawable.headerbkg,"20 €"));
        // Agrega más elementos...

        AdaptadorComidas adapter = new AdaptadorComidas(requireContext(), itemList);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}