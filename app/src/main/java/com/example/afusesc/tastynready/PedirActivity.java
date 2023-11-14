package com.example.afusesc.tastynready;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PedirActivity extends AppCompatActivity {

    ImageView imgEntrantes;
    ImageView imgAperitivos;
    ImageView imgBebidas;
    ImageView imgPostres;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_cont);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        imgEntrantes = findViewById(R.id.img_entrante);
        imgAperitivos = findViewById(R.id.img_aperitivos);
        imgBebidas = findViewById(R.id.img_bebidas);
        imgPostres = findViewById(R.id.img_postres);
        button = findViewById(R.id.button2);

        imgEntrantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorPedidos.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorPedidos.MyItem("Filete de Ternera con Salsa de Champiñones", R.drawable.img_bistec,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Pasta Alfredo con Pollo", R.drawable.img_spaghet,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Ensalada de Salmón Ahumado", R.drawable.img_salad,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Tacos de Camarones al Chipotle", R.drawable.headerbkg,"20 €"));
                // Agrega más elementos...

                AdaptadorPedidos adapter = new AdaptadorPedidos(PedirActivity.this, itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(PedirActivity.this));
                recyclerView.setAdapter(adapter);
            }
        });

        imgAperitivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorPedidos.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorPedidos.MyItem("Alitas de Pollo Buffalo", R.drawable.img_pollo,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Nachos Supremos", R.drawable.img_nacho,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Brochetas de Camarones y Piña", R.drawable.img_brocheta,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Tacos de Barbacoa", R.drawable.img_taco,"20 €"));
                // Agrega más elementos...

                AdaptadorPedidos adapter = new AdaptadorPedidos(PedirActivity.this, itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(PedirActivity.this));
                recyclerView.setAdapter(adapter);
            }
        });

        imgBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorPedidos.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorPedidos.MyItem("Agua Mineral Natural", R.drawable.img_awa,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Vino Tinto Malbec", R.drawable.img_wine,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Refresco de Limón Casero", R.drawable.img_limon,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Coca-Cola", R.drawable.headerbkg,"20 €"));
                // Agrega más elementos...

                AdaptadorPedidos adapter = new AdaptadorPedidos(PedirActivity.this, itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(PedirActivity.this));
                recyclerView.setAdapter(adapter);
            }
        });

        imgPostres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdaptadorPedidos.MyItem> itemList = new ArrayList<>();
                itemList.add(new AdaptadorPedidos.MyItem("Tarta de Chocolate Decadente", R.drawable.img_cake,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Tiramisú", R.drawable.img_tiramisu,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Crema Catalana", R.drawable.img_crema,"20 €"));
                itemList.add(new AdaptadorPedidos.MyItem("Café Espresso", R.drawable.img_cafe,"20 €"));
                // Agrega más elementos...

                AdaptadorPedidos adapter = new AdaptadorPedidos(PedirActivity.this, itemList);

                recyclerView.setLayoutManager(new LinearLayoutManager(PedirActivity.this));
                recyclerView.setAdapter(adapter);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PedirActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        List<AdaptadorPedidos.MyItem> itemList = new ArrayList<>();
        itemList.add(new AdaptadorPedidos.MyItem("Filete de Ternera con Salsa de Champiñones", R.drawable.img_bistec,"20 €"));
        itemList.add(new AdaptadorPedidos.MyItem("Pasta Alfredo con Pollo", R.drawable.img_spaghet,"20 €"));
        itemList.add(new AdaptadorPedidos.MyItem("Ensalada de Salmón Ahumado", R.drawable.img_salad,"20 €"));
        itemList.add(new AdaptadorPedidos.MyItem("Tacos de Camarones al Chipotle", R.drawable.headerbkg,"20 €"));
        // Agrega más elementos...

        AdaptadorPedidos adapter = new AdaptadorPedidos(PedirActivity.this, itemList);

        recyclerView.setLayoutManager(new LinearLayoutManager(PedirActivity.this));
        recyclerView.setAdapter(adapter);
    }
}
