package com.example.afusesc.tastynready.presentation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.afusesc.tastynready.R;

public class ContactaFragment extends Fragment {

    private static final int REQUEST_CALL_PERMISSION = 1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacta, container, false);

        TextView llamarTxt = view.findViewById(R.id.llamarTxt);
        TextView correoTxt = view.findViewById(R.id.correoTxt);

        llamarTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                switch (event.getAction()) {
                    case android.view.MotionEvent.ACTION_DOWN:
                        llamarTxt.setTextColor(getResources().getColor(R.color.colorAzul));
                        break;
                    case android.view.MotionEvent.ACTION_UP:
                        if (checkCallPermission()) {
                            realizarLlamada();
                        } else {
                            requestCallPermission();
                        }
                        // Restaurar el color original al soltar el toque
                        llamarTxt.setTextColor(getResources().getColor(R.color.colorNegro));
                        break;
                }
                return true;
            }
        });

        correoTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                switch (event.getAction()) {
                    case android.view.MotionEvent.ACTION_DOWN:
                        correoTxt.setTextColor(getResources().getColor(R.color.colorAzul));
                        break;
                    case android.view.MotionEvent.ACTION_UP:
                        enviarCorreo();
                        // Restaurar el color original al soltar el toque
                        correoTxt.setTextColor(getResources().getColor(R.color.colorNegro));
                        break;
                }
                return true;
            }
        });


        return view;
    }

    private void realizarLlamada() {
        Intent llamar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:656678654"));
        llamar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(llamar);
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.e("ERROR", "Error al iniciar la actividad de llamada.");
        }
    }

    private void enviarCorreo() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:tastyAndReady@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto del correo");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Cuerpo del correo");
        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar correo electrónico"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR", "Error al iniciar la actividad de enviar correo.");
        }
    }

    private boolean checkCallPermission() {
        int result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCallPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
    }
}
