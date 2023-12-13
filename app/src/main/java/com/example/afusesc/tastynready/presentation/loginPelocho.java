package com.example.afusesc.tastynready.presentation;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.R;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class loginPelocho extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String correo = "";
    private String contraseña = "";
    private ViewGroup contenedor;
    private EditText etCorreo, etContraseña;
    private TextInputLayout tilCorreo, tilContraseña;
    private ProgressDialog dialogo;
    private ProgressDialog dialogoo;

    private String rol = "";
    private String roli = "";
    private String rolm = "Medico";
    private String rolp = "Paciente";
    private String rola = "Admin";
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    User user;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d("PRUEBAS", "auth "+ auth);
        super.onCreate(savedInstanceState);
        /*
        setContentView(R.layout.login);
        etCorreo = (EditText) findViewById(R.id.correol);
        etContraseña = (EditText) findViewById(R.id.contraseña);
        tilCorreo = (TextInputLayout) findViewById(R.id.til_clave);
        tilContraseña = (TextInputLayout) findViewById(R.id.til_contraseña);

        dialogo = new ProgressDialog(this);
        dialogo.setTitle("Verificando usuario");
        dialogo.setMessage("Por favor espere...");
        dialogoo = new ProgressDialog(this);
        dialogoo.setTitle("Iniciando Sesion");
        dialogoo.setMessage("Por favor espere...");



        if (auth.getCurrentUser() != null && !auth.getUid().equals("dsY9BtWSuqh9pVmkLsVVfnvEKcl1")) {

            dialogoo.show();
            DocumentReference docRef = db.collection("Users").document(auth.getCurrentUser().getUid());
            Log.e("Pruebas: ", "UID del usuario: " + auth.getCurrentUser().getUid() );
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    roli = user.getRol().toString();
                    if (Objects.equals(roli, rolp)) {
                        verificaSiUsuarioValidadop();
                    }
                    else if (Objects.equals(roli, rolm)) {
                        verificaSiUsuarioValidado();
                    }
                    else if (Objects.equals(roli, rola)) {
                        verificaSiUsuarioValidadoa();
                    }
                }
            });
        }

         */
    }



    public void aceptar() {
        Toast t=Toast.makeText(this,"Bienvenido a probar el programa.", Toast.LENGTH_SHORT);
        t.show();
    }
    private void verificaSiUsuarioValidado() {
        /*Intent i = new Intent(this, VistaMedico.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

         */
    /*
    private void verificaSiUsuarioValidadop() {
        Intent i = new Intent(this, VistaPaciente.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void verificaSiUsuarioValidadoa() {
        Log.d("Pruebas","entra en verificaSiUsuarioValidadoa");
        Intent i = new Intent(this, VistaAdmin.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }



     */
/*
    public void inicioSesiónCorreo(View v) {
        if (verificaCampos()) {
            dialogo.show();
            auth.signInWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DocumentReference docRef = db.collection("Users").document(auth.getCurrentUser().getUid());
                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        user = documentSnapshot.toObject(User.class);
                                        rol=user.getRol().toString();
                                        if(Objects.equals(rol,rolp)) {
                                            verificaSiUsuarioValidadop();
                                        }
                                        else if(Objects.equals(rol,rolm)) {
                                            verificaSiUsuarioValidado();
                                        }
                                        else if(Objects.equals(rol,rola)) {
                                            verificaSiUsuarioValidadoa();
                                        }
                                    }
                                });
                            } else {
                                dialogo.dismiss();
                                Toast.makeText(getApplicationContext(),"El usuario o la contraseña no son correctas",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }



    public void registroCodigo(View v) {
        startActivity(new Intent(this, Clave.class));
    }

    private void mensaje(String mensaje) {
        Snackbar.make(findViewById(android.R.id.content), mensaje, Snackbar.LENGTH_LONG).show();
    }

    private boolean verificaCampos() {
        boolean s=true;
        correo = etCorreo.getText().toString();
        contraseña = etContraseña.getText().toString();
        tilCorreo.setError("");
        tilContraseña.setError("");
        if (correo.isEmpty()) {
            tilCorreo.setError("Introduce un correo");
            s=false;
        }
        if (contraseña.isEmpty()) {
            tilContraseña.setError("Introduce una contraseña");
            s=false;
        }
        return s;

     */}



}




