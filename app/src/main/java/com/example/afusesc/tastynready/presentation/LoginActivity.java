package com.example.afusesc.tastynready.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afusesc.tastynready.AdminActivity;
import com.example.afusesc.tastynready.model.UsuarioInfo;
import com.example.afusesc.tastynready.presentation.PaginaTrabajadorActivity;
import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.DataPicker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUser currentUser;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnGoogleSignIn;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private DataPicker dataPicker = new DataPicker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        SignInButton btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButtonClick();
            }
        });

        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Initialize GoogleSignInOptions
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Initialize GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, /* OnConnectionFailedListener */ null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(LoginActivity.this, "Google Sign In failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Use the global instance of DataPicker to save user information
                            dataPicker.guardarUsuarioEnFirebase(user, "cliente");
                            Intent intent = new Intent(LoginActivity.this, ReservasActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void onLoginButtonClick() {
        resetearErrores();

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (username.equals("admin") && password.equals("admin")) {
            // Acceso directo para el usuario Admin
            Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(adminIntent);
            finish();
            return;
        }
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
                            dataPicker.guardarUsuarioEnFirebase(user, "cliente");
                            Intent intent = new Intent(LoginActivity.this, ReservasActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            mostrarError("errorPassword", "Usuario o contraseña incorrecta");
                        }
                    }
                });
    }

    private void obtenerRolDeFirebase(String userId) {
        DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child(userId);

        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String rol = snapshot.child("rol").getValue(String.class);
                    if (rol != null) {
                        if (rol.equals("cliente")) {
                            // Usuario es cliente
                            Intent intent = new Intent(LoginActivity.this, ReservasActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        if (rol.equals("trabajador")) {
                            Log.d("DEBUG", "Redirigiendo a PaginaTrabajadorActivity"); // Agrega este log
                            Intent intent = new Intent(LoginActivity.this, PaginaTrabajadorActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Otro rol, manejar según sea necesario
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error según sea necesario
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
