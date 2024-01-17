package com.example.afusesc.tastynready.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.afusesc.tastynready.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends Fragment {
    double latitud = 38.996651;
    double longitud = -0.166009;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_mapa, container, false);


        mostrarMapa();
        return inflatedView;
    }


    private void mostrarMapa() {
        // Crear un Uri para mostrar un mapa en una ubicación específica
        Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud);

        // Crear un Intent con la acción ACTION_VIEW y el Uri creado
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        // Establecer el paquete para asegurar que se use la aplicación de Google Maps
        mapIntent.setPackage("com.google.android.apps.maps");

        // Verificar si hay una aplicación que pueda manejar el intent antes de iniciarlo
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
