package com.example.afusesc.tastynready.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.R;

public class PagandoActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

        // Mostrar el diálogo de carga simulada
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Efectuando el Pago...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Duración del splash screen en milisegundos (en este caso, 15 segundos)
        int splashDuration = 2000;

        // Crear un Handler para retrasar la ejecución de la tarea
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ocultar el diálogo de carga simulada
                progressDialog.dismiss();

                // Crear un Intent para iniciar la MainActivity
                Intent intent = new Intent(PagandoActivity.this, MainActivity.class);
                startActivity(intent);

                // Cerrar la SplashActivity para que no vuelva atrás
                finish();
            }
        }, splashDuration);
    }
}
