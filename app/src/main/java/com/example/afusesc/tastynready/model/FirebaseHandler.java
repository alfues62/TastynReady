package com.example.afusesc.tastynready.model;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHandler {

    private FirebaseFirestore db;
    private DataPicker dataPicker;

    public FirebaseHandler() {
        // Inicializa la instancia de Firebase
        db = FirebaseFirestore.getInstance();
        dataPicker = new DataPicker();
    }

    public void guardarReservaEnFirebase() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            // Si el usuario está autenticado, guarda su información en la variable userData de DataPicker
            dataPicker.guardarUsuarioEnFirebase(usuario);

            // Obtén la información del usuario desde DataPicker
            Map<String, Object> usuarioInfo = DataPicker.obtenerDatosUsuario();

            // Obtén la fecha seleccionada desde DataPicker
            String fechaSeleccionada = DataPicker.obtenerFechaSeleccionada();

            // Obtén la hora seleccionada desde DataPicker
            String horaSeleccionada = DataPicker.obtenerHoraSeleccionada();

            // Obtén el numero de comersales desde DataPicker
            int numComersales = DataPicker.obtenerNumComensales();

            // Obtén el numero de sala desde DataPicker
            String idSala = DataPicker.obtenerIdSala();

            // Obtén el array de platos desde DataPicker
            List<Platos> platosList = DataPicker.obtenerArray();

            // Guarda la reserva en Firebase
            Map<String, Object> reservaInfo = new HashMap<>();
            reservaInfo.put("Usuario", usuarioInfo.get("displayName"));
            reservaInfo.put("Sala", idSala);
            reservaInfo.put("Hora", horaSeleccionada);
            reservaInfo.put("Fecha", fechaSeleccionada);
            reservaInfo.put("Comensales", numComersales);

            List<Map<String, Object>> todosPlatos = new ArrayList<>();

           for (Platos platos : platosList){
               Map<String, Object> platoInfo = new HashMap<>();
               platoInfo.put("Nombre", platos.getNombre());
               platoInfo.put("Cantidad", platos.getCantidad());
               platoInfo.put("Precio", (platos.getPrecio()) * (platos.getCantidad()));

               // Add the plate information to the list
               todosPlatos.add(platoInfo);
           }

            reservaInfo.put("zPlatos", platosList);


            // Puedes guardar la reserva en una colección "reservas" con un identificador único
            db.collection("reservas").document()
                    .set(reservaInfo)
                    .addOnSuccessListener(aVoid -> {
                        // Manejar el éxito, si es necesario
                    })
                    .addOnFailureListener(e -> {
                        // Manejar el error, si es necesario
                    });
        }
    }

    // ESTE METODO SOLO DEBE UTILIZARSE SI HAY UN CAMBIO EN LA BBDD DE FIREBASE
    public void ponerPlatosFirebase(){

        // Platos Entrantes______________________________________________
        Map<String, Object> FileteConSalsa = new HashMap<>();
        FileteConSalsa.put("Nombre", "Filete de Ternera");
        FileteConSalsa.put("Descripcion", "Descripción del Plato1");
        FileteConSalsa.put("Precio", 10.99);
        FileteConSalsa.put("Categoria", "Entrantes");
        FileteConSalsa.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_bistec.jpg?alt=media&token=9ca17999-646c-436c-b675-7a3d46017f0f");


        Map<String, Object> PastaAlfredo = new HashMap<>();
        PastaAlfredo.put("Nombre", "Spaghetti Alfredo");
        PastaAlfredo.put("Descripcion", "Descripción del Plato2");
        PastaAlfredo.put("Precio", 8.99);
        PastaAlfredo.put("Categoria", "Entrantes");
        PastaAlfredo.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_spaghet.jpg?alt=media&token=909a807b-f0b2-49b8-bd45-047d6a5d7727");

        Map<String, Object> EnsaladaSalmon = new HashMap<>();
        EnsaladaSalmon.put("Nombre", "Ensalada de Salmon");
        EnsaladaSalmon.put("Descripcion", "Descripción del Plato3");
        EnsaladaSalmon.put("Precio", 12.99);
        EnsaladaSalmon.put("Categoria", "Entrantes");
        EnsaladaSalmon.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_salad.jpg?alt=media&token=90395f5e-0b28-4f98-aa10-6295d9aec214");

        Map<String, Object> TacosChipotle = new HashMap<>();
        TacosChipotle.put("Nombre", "Tacos con Chipotle");
        TacosChipotle.put("Descripcion", "Descripción del Plato4");
        TacosChipotle.put("Precio", 15.99);
        TacosChipotle.put("Categoria", "Entrantes");
        TacosChipotle.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_taco.jpg?alt=media&token=1f4dc12f-cbe6-4656-9252-d0202de72a7c");

        // Platos Complementarios______________________________________________

        Map<String, Object> AlitasPollo = new HashMap<>();
        AlitasPollo.put("Nombre", "Alitas de Pollo");
        AlitasPollo.put("Descripcion", "Descripción del Plato1");
        AlitasPollo.put("Precio", 8.99);
        AlitasPollo.put("Categoria", "Complementos");
        AlitasPollo.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_pollo.jpg?alt=media&token=fa21a8b1-f6eb-43d0-a489-4e9b4ac179c6");

        Map<String, Object> Nachos = new HashMap<>();
        Nachos.put("Nombre", "Nachos Supremos");
        Nachos.put("Descripcion", "Descripción del Plato2");
        Nachos.put("Precio", 9.99);
        Nachos.put("Categoria", "Complementos");
        Nachos.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_nacho.jpg?alt=media&token=249af042-41b7-43f5-81d5-3c26ba615962");

        Map<String, Object> Brochetas = new HashMap<>();
        Brochetas.put("Nombre", "Brochetas de Camarones");
        Brochetas.put("Descripcion", "Descripción del Plato3");
        Brochetas.put("Precio", 12.99);
        Brochetas.put("Categoria", "Complementos");
        Brochetas.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_brocheta.jpg?alt=media&token=58fb6756-ac49-4236-aa28-7aa77ab6019e");

        Map<String, Object> TacosBarbacoa = new HashMap<>();
        TacosBarbacoa.put("Nombre", "Tacos de Barbacoa");
        TacosBarbacoa.put("Descripcion", "Descripción del Plato4");
        TacosBarbacoa.put("Precio", 15.99);
        TacosBarbacoa.put("Categoria", "Complementos");
        TacosBarbacoa.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_taco.jpg?alt=media&token=1f4dc12f-cbe6-4656-9252-d0202de72a7c");

        // Platos de Postre______________________________________________________

        Map<String, Object> ChoclolateDecadente = new HashMap<>();
        ChoclolateDecadente.put("Nombre", "Tarta de Choclolate Decadente");
        ChoclolateDecadente.put("Descripcion", "Descripción del Plato1");
        ChoclolateDecadente.put("Precio", 18.99);
        ChoclolateDecadente.put("Categoria", "Postres");
        ChoclolateDecadente.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_cake.jpg?alt=media&token=b3f555f0-1cf6-4152-b0d4-fe42c6034abf");

        Map<String, Object> Tiramisu = new HashMap<>();
        Tiramisu.put("Nombre", "Tiramisú");
        Tiramisu.put("Descripcion", "Descripción del Plato2");
        Tiramisu.put("Precio", 11.99);
        Tiramisu.put("Categoria", "Postres");
        Tiramisu.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_tiramisu.jpg?alt=media&token=36b19766-4ee5-4211-bd92-bbed78b7e7ec");

        Map<String, Object> CremaCatalana = new HashMap<>();
        CremaCatalana.put("Nombre", "Crema Catalana");
        CremaCatalana.put("Descripcion", "Descripción del Plato3");
        CremaCatalana.put("Precio", 12.99);
        CremaCatalana.put("Categoria", "Postres");
        CremaCatalana.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_crema.jpg?alt=media&token=f9af97c3-ea73-491b-bf35-4e67170a5454");

        Map<String, Object> Cafe = new HashMap<>();
        Cafe.put("Nombre", "Cafe Espresso");
        Cafe.put("Descripcion", "Descripción del Plato4");
        Cafe.put("Precio", 15.99);
        Cafe.put("Categoria", "Postres");
        Cafe.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_cafe.jpg?alt=media&token=9d98c45b-76ca-4159-ba85-fbbbb1d64364");

        // Bebidas_______________________________________________________________

        Map<String, Object> Awa = new HashMap<>();
        Awa.put("Nombre", "Agua Mineral Natural");
        Awa.put("Descripcion", "Descripción del Plato1");
        Awa.put("Precio", 18.99);
        Awa.put("Categoria", "Bebidas");
        Awa.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_awa.jpg?alt=media&token=b8706e19-832c-4812-9ec1-c6f2a4a3fa7d");

        Map<String, Object> Vino = new HashMap<>();
        Vino.put("Nombre", "Vino Tinto Malbec");
        Vino.put("Descripcion", "Descripción del Plato2");
        Vino.put("Precio", 11.99);
        Vino.put("Categoria", "Bebidas");
        Vino.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_wine.jpg?alt=media&token=ee0511be-801d-46c0-90f7-12de9cb07374");

        Map<String, Object> Coca = new HashMap<>();
        Coca.put("Nombre", "CocaCola");
        Coca.put("Descripcion", "Descripción del Plato3");
        Coca.put("Precio", 12.99);
        Coca.put("Categoria", "Bebidas");
        Coca.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_coca.jpg?alt=media&token=3795b3e6-7227-43e5-8ec3-01bb8e4cf9f2");

        Map<String, Object> Refresco = new HashMap<>();
        Refresco.put("Nombre", "Limonada Casera");
        Refresco.put("Descripcion", "Descripción del Plato4");
        Refresco.put("Precio", 15.99);
        Refresco.put("Categoria", "Bebidas");
        Refresco.put("img", "https://firebasestorage.googleapis.com/v0/b/tastynready.appspot.com/o/img_limon.jpg?alt=media&token=2f7a9508-a122-4ea5-b02a-663f71debb69");

        // Guardar Cosas_____________________________________________________________________

        db.collection("Platos").document("FileteConSalsa").set(FileteConSalsa);
        db.collection("Platos").document("PastaAlfredo").set(PastaAlfredo);
        db.collection("Platos").document("EnsaladaSalmon").set(EnsaladaSalmon);
        db.collection("Platos").document("TacosChipotle").set(TacosChipotle);

        db.collection("Platos").document("AlitasPollo").set(AlitasPollo);
        db.collection("Platos").document("Nachos").set(Nachos);
        db.collection("Platos").document("Brochetas").set(Brochetas);
        db.collection("Platos").document("TacosBarbacoa").set(TacosBarbacoa);

        db.collection("Platos").document("ChoclolateDecadente").set(ChoclolateDecadente);
        db.collection("Platos").document("Tiramisu").set(Tiramisu);
        db.collection("Platos").document("CremaCatalana").set(CremaCatalana);
        db.collection("Platos").document("Cafe").set(Cafe);

        db.collection("Platos").document("Agua").set(Awa);
        db.collection("Platos").document("Vino").set(Vino);
        db.collection("Platos").document("CocaCola").set(Coca);
        db.collection("Platos").document("Refresco").set(Refresco);
    }
}
