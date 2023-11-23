package com.example.afusesc.tastynready.presentation;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.DataPicker;
import com.example.afusesc.tastynready.model.UsuarioInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    DataPicker dataPicker = new DataPicker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButtonClick();
            }
        });
        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onLoginButtonClick() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {
            // Utilizar Firebase Authentication para iniciar sesión
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Inicio de sesión exitoso, obtener el usuario actual
                                FirebaseUser user = mAuth.getCurrentUser();

                                // Guardar el usuario en DataPicker
                                dataPicker.guardarUsuarioRegistrado(new UsuarioInfo(
                                        user.getDisplayName(),
                                        user.getEmail(),
                                        user.getUid()
                                ));
                                Log.d("LoginActivity", "Login successful");
                                dataPicker.guardarUsuarioEnFirebase();
                                Intent intent = new Intent(LoginActivity.this, ReservasActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Si el inicio de sesión falla, mostrar un mensaje de error
                                Log.d("LoginActivity", "Login failed: " + task.getException());
                                String errorMessage = "Inicio de sesión fallido";

                                // Check if the failure is due to an incorrect username or password
                                if (task.getException() != null &&
                                        task.getException().getMessage() != null &&
                                        (task.getException().getMessage().contains("invalid email") ||
                                                task.getException().getMessage().contains("password is invalid"))) {
                                    errorMessage = "Usuario o contraseña incorrecta";
                                }

                                Map<String, String> errores = new HashMap<>();
                                errores.put("errorLogin", errorMessage);
                                mostrarErrores(errores);
                            }
                        }
                    });
        } else {
            // Campos vacíos, mostrar mensaje de error
            Map<String, String> errores = new HashMap<>();
            errores.put("errorCampos", "Por favor, complete todos los campos");
            mostrarErrores(errores);
        }
    }

    private void mostrarErrores(Map<String, String> errores) {
        // Implementa la lógica para mostrar mensajes de error en tu interfaz de usuario
        // Puedes mostrar estos errores en TextViews, Toasts, o cualquier otro método que prefieras
        // Por ejemplo:
        for (Map.Entry<String, String> entry : errores.entrySet()) {
            String mensaje = entry.getValue();
            // Muestra el mensaje en algún lugar de tu interfaz de usuario
        }
    }

}
