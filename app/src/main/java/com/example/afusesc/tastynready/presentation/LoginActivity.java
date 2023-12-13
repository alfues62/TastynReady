package com.example.afusesc.tastynready.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.DataPicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        resetearErrores();

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        // Validaciones de campos
        if (username.isEmpty() && password.isEmpty()) {
            mostrarError("errorUsername", "Correo electrónico requerido ");
            mostrarError("errorPassword", "Contraseña requerida ");
            return;  // Evitar la autenticación si los campos están vacíos
        }
        if (username.isEmpty()) {
            mostrarError("errorUsername", "Correo electrónico requerido ");
            return;  // Evitar la autenticación si el campo de correo electrónico está vacío
        }
        if (password.isEmpty()) {
            mostrarError("errorPassword", "Contraseña requerida ");
            return;  // Evitar la autenticación si el campo de contraseña está vacío
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            mostrarError("errorUsername", "Formato de correo electrónico incorrecto");
            return;  // Evitar la autenticación si el formato del correo electrónico es incorrecto
        }

        // Utilizar Firebase Authentication para iniciar sesión
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Utiliza la instancia global de DataPicker para guardar la información del usuario
                            dataPicker.guardarUsuarioEnFirebase(user);
                            Intent intent = new Intent(LoginActivity.this, ReservasActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            mostrarError("errorPassword", "Usuario o contraseña incorrecta");
                        }
                    }
                });
    }

    private void mostrarError(String errorTextViewId, String mensajeError) {
        TextView errorTextView = findViewById(getResources().getIdentifier(errorTextViewId, "id", getPackageName()));
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(mensajeError);
    }

    private void resetearErrores() {
        TextView errorUsername = findViewById(R.id.errorUsername);
        TextView errorPassword = findViewById(R.id.errorPassword);

        errorUsername.setVisibility(View.GONE);
        errorPassword.setVisibility(View.GONE);
    }
}
