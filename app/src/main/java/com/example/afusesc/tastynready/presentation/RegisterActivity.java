package com.example.afusesc.tastynready.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegistroEmail;
    private EditText editTextRegistroUsername;
    private EditText editTextRegistroPassword;
    private EditText editTextRegistroConfirmPassword;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        editTextRegistroEmail = findViewById(R.id.editTextRegistroEmail);
        editTextRegistroUsername = findViewById(R.id.editTextRegistroUsername);
        editTextRegistroPassword = findViewById(R.id.editTextRegistroPassword);
        editTextRegistroConfirmPassword = findViewById(R.id.editTextRegistroConfirmPassword);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegistroButtonClick();
            }
        });
    }

    private void onRegistroButtonClick() {
        // Restablecer mensajes de error
        resetearErrores();

        // Validar que los campos no estén vacíos
        boolean hayErrores = false;

        String email = editTextRegistroEmail.getText().toString();
        String username = editTextRegistroUsername.getText().toString();
        String password = editTextRegistroPassword.getText().toString();
        String confirmPassword = editTextRegistroConfirmPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrarError("errorRegistroEmail", "Correo electrónico requerido o inválido");
            hayErrores = true;
        }

        if (username.isEmpty()) {
            mostrarError("errorRegistroUsername", "Usuario requerido");
            hayErrores = true;
        }

        if (password.isEmpty() || !esContrasenaValida(password)) {
            mostrarError("errorRegistroPassword", "La contraseña debe tener entre 6 y 16 caracteres y contener letras y números");
            hayErrores = true;
        }

        if (!password.equals(confirmPassword)) {
            mostrarError("errorRegistroConfirmPassword", "Las contraseñas no coinciden");
            hayErrores = true;
        }

        if (hayErrores) {
            // Si hay errores, no continúes con el registro
            return;
        }

        // Aquí puedes agregar la lógica para registrar al usuario en Firebase o en tu sistema de autenticación
        // Puedes usar FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password) para Firebase

        // Mostrar un mensaje de registro exitoso
        mostrarMensajeRegistroExitoso();

        // Puedes redirigir a la pantalla de inicio de sesión o a cualquier otra actividad según tus necesidades
    }

    private void mostrarError(String errorTextViewId, String mensajeError) {
        TextView errorTextView = findViewById(getResources().getIdentifier(errorTextViewId, "id", getPackageName()));
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(mensajeError);
    }

    private void resetearErrores() {
        TextView errorRegistroEmail = findViewById(R.id.errorRegistroEmail);
        TextView errorRegistroUsername = findViewById(R.id.errorRegistroUsername);
        TextView errorRegistroPassword = findViewById(R.id.errorRegistroPassword);
        TextView errorRegistroConfirmPassword = findViewById(R.id.errorRegistroConfirmPassword);

        errorRegistroEmail.setVisibility(View.GONE);
        errorRegistroUsername.setVisibility(View.GONE);
        errorRegistroPassword.setVisibility(View.GONE);
        errorRegistroConfirmPassword.setVisibility(View.GONE);
    }

    private boolean esContrasenaValida(String contrasena) {
        // Validar que la contraseña tenga entre 6 y 16 caracteres y contenga letras y números
        return contrasena.length() >= 6 && contrasena.length() <= 16 && contrasena.matches(".*[a-zA-Z].*") && contrasena.matches(".*\\d.*");
    }

    private void mostrarMensajeRegistroExitoso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registro Exitoso");
        builder.setMessage("Se ha registrado correctamente. Verifica tu correo electrónico para completar el proceso.");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Redirigir a MainActivity
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual para que el usuario no pueda volver atrás
            }
        });

        builder.show();
    }
}
