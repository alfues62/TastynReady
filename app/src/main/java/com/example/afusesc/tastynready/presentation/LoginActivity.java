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
import com.example.afusesc.tastynready.model.FirebaseHandler;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

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

        // Intentar iniciar sesión como Admin
        if (intentarLoginComoAdmin(username, password)) {
            return;  // Evitar mostrar el mensaje de credenciales incorrectas para trabajador
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

    private boolean intentarLoginComoAdmin(String username, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuariosRef = db.collection("usuarios");

        usuariosRef.whereEqualTo("email", username)
                .whereEqualTo("password", password)
                .whereEqualTo("rol", "admin")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Usuario con credenciales de administrador encontrado
                                Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(adminIntent);
                                finish();
                                return;
                            }
                            // Si no se encuentra un usuario con las credenciales proporcionadas
                            mostrarError("errorPassword", "Credenciales de administrador incorrectas");
                        } else {
                            // Manejar errores de Firestore
                            Toast.makeText(LoginActivity.this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return false;  // Retorna false por defecto, actualiza según tus necesidades
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
