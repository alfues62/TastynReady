package com.example.afusesc.tastynready.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.afusesc.tastynready.model.UsuarioInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

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

        // Verificar si el correo ya está registrado
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            if (result != null && result.getSignInMethods() != null && !result.getSignInMethods().isEmpty()) {
                                // El correo ya está registrado
                                mostrarError("errorRegistroEmail", "Correo electrónico ya registrado");
                            } else {
                                // El correo no está registrado, proceder con el registro
                                registrarUsuario(email, password, username);
                            }
                        } else {
                            // Error al verificar el correo
                            Toast.makeText(RegisterActivity.this, "Error al verificar el correo electrónico", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registrarUsuario(String email, String password, String username) {
        // Registrar al usuario en Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registro exitoso
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Añadir el valor del rol "cliente"
                            if (user != null) {
                                // Envía un correo de verificación al usuario
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> emailTask) {
                                                if (emailTask.isSuccessful()) {
                                                    // Correo de verificación enviado con éxito
                                                    mostrarMensajeRegistroExitoso();
                                                } else {
                                                    // Error al enviar el correo de verificación
                                                    Toast.makeText(RegisterActivity.this, "Error al enviar el correo de verificación", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> profileUpdateTask) {
                                                if (profileUpdateTask.isSuccessful()) {
                                                    // Usuario actualizado con el nombre de usuario y el rol
                                                    dataPicker.guardarUsuarioRegistrado(new UsuarioInfo(
                                                            user.getDisplayName(),
                                                            user.getEmail(),
                                                            user.getUid()
                                                    ));

                                                    // Llama al método para guardar el usuario en Firebase Firestore
                                                    dataPicker.guardarUsuarioEnFirebase();

                                                    mostrarMensajeRegistroExitoso();
                                                } else {
                                                    // Error al actualizar el perfil del usuario
                                                    Toast.makeText(RegisterActivity.this, "Error al actualizar el perfil del usuario", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            // Error en el registro
                            // Mostrar mensaje de error
                            if (task.getException().getMessage().contains("email address is already in use")) {
                                // El correo ya está en uso
                                mostrarError("errorRegistroEmail", "Correo electrónico ya registrado");
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error en el registro: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
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
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual para que el usuario no pueda volver atrás
            }
        });

        builder.show();
    }
}
