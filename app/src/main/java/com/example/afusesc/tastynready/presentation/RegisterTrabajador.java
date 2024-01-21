package com.example.afusesc.tastynready.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.DataPicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterTrabajador extends AppCompatActivity {

    private EditText editTextRegistroEmail;
    private EditText editTextRegistroUsername;
    private EditText editTextRegistroPassword;
    private EditText editTextRegistroConfirmPassword;
    private FirebaseAuth mAuth;
    private Button btnRegistro;
    DataPicker dataPicker = new DataPicker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mAuth = FirebaseAuth.getInstance();  // Inicializar mAuth

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
        resetearErrores();

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
            return;
        }

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            if (result != null && result.getSignInMethods() != null && !result.getSignInMethods().isEmpty()) {
                                mostrarError("errorRegistroEmail", "Correo electrónico ya registrado");
                            } else {
                                agregarTrabajador(email, password, username, "trabajador");
                            }
                        } else {
                            Toast.makeText(RegisterTrabajador.this, "Error al verificar el correo electrónico", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void agregarTrabajador(String email, String password, String username, String rol) {
        String Email = email;
        String Password = password;  // Cambia la contraseña a algo con al menos 6 caracteres
        String Usurname = 

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Creación de usuario administrador exitosa
                            FirebaseUser adminUser = mAuth.getCurrentUser();
                            // Utiliza la instancia global de DataPicker para guardar la información del usuario
                            dataPicker.guardarUsuarioEnFirebase(adminUser, "admin");

                            // También puedes almacenar información adicional si es necesario
                            DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child("admin00");
                            adminRef.child("username").setValue(Email);
                            adminRef.child("password").setValue(Password);

                            Toast.makeText(RegisterTrabajador.this, "Usuario administrador creado exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            // Manejar errores en la creación del usuario administrador
                            Toast.makeText(RegisterTrabajador.this, "Error al crear el usuario administrador", Toast.LENGTH_SHORT).show();
                            Log.e("LoginActivity", "Error al crear el usuario administrador", task.getException());
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
        return contrasena.length() >= 6 && contrasena.length() <= 16 && contrasena.matches(".*[a-zA-Z].*") && contrasena.matches(".*\\d.*");
    }

    private void mostrarMensajeRegistroExitoso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registro Exitoso");
        builder.setMessage("Se ha registrado correctamente. Verifica tu correo electrónico para completar el proceso.");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(RegisterTrabajador.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.show();
    }
}