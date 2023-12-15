package com.example.afusesc.tastynready.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.afusesc.tastynready.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FragmentUsuario extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id;
    private EditText correo;
    private EditText nombre;
    private Button botonGuardar;
    private ImageButton editarNombre;
    private ImageButton editarCorreo;
    private String nombreOriginal;
    private String correoOriginal;

    Button btn;

    ImageView imageView6;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        id = usuario.getUid();
        DocumentReference docRef = db.collection("usuarios").document(id);
        correo = view.findViewById(R.id.correo);
        nombre = view.findViewById(R.id.nombre);
        botonGuardar = view.findViewById(R.id.guardarDatos);
        editarNombre = view.findViewById(R.id.editarNombre);
        editarCorreo = view.findViewById(R.id.editarCorreo);
        imageView6 = view.findViewById(R.id.imageView6);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                correoOriginal = (documentSnapshot.getString("email"));
                nombreOriginal = documentSnapshot.getString("displayName");
                nombre.setText(nombreOriginal);
                correo.setText(correoOriginal);
            }
        });

        botones();

        btn = view.findViewById(R.id.guardarReserva2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MisReservasActivity.class);
                startActivity(i);
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requireActivity().getSupportFragmentManager().popBackStack();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("BackStackDebug", "Back stack count: " + requireActivity().getSupportFragmentManager().getBackStackEntryCount());

            }
        });


        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);  // Add this line if you want to add the current fragment to the back stack
        transaction.commit();
    }

    private void navigateBack() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();  // Use popBackStackImmediate to pop the back stack immediately
    }
    private void botones() {
        editarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre.isEnabled()) {
                    nombre.setEnabled(false);
                } else {
                    nombre.setEnabled(true);
                    nombre.requestFocus();
                    nombre.selectAll();
                }
            }
        });


        editarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre.isEnabled()) {
                    nombre.setEnabled(false);
                } else {
                    nombre.setEnabled(true);
                    nombre.requestFocus();
                    nombre.selectAll();
                }
            }
        });

        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                botonGuardar.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                botonGuardar.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoConfirmacion();
            }
        });
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Guardar Cambios");
        builder.setMessage("¿Quieres guardar los cambios?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                guardarCambios();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nombre.setText(nombreOriginal);
                botonGuardar.setEnabled(false);
                nombre.setEnabled(false);
            }
        });
        builder.show();
    }

    private void guardarCambios() {
        nombre.setEnabled(false);
        correo.setEnabled(false);
        botonGuardar.setEnabled(false);
        actualizarDatos();
    }

    private void actualizarDatos() {
        String nombreTexto = nombre.getText().toString();
        String correoTexto = correo.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("displayName", nombreTexto);
        map.put("email", correoTexto);

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        fStore.collection("usuarios").document(id).update(map);
    }
}
