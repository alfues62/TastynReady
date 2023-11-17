package com.example.afusesc.tastynready.presentation;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.afusesc.tastynready.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class FragmentUsuario extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_usuario, container, false);

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        TextView nombre = view.findViewById(R.id.nombre);
        nombre.setText(usuario.getDisplayName());
        TextView coreo = view.findViewById(R.id.correo);
        coreo.setText(usuario.getEmail());
        TextView prove = view.findViewById(R.id.proveedor);
        prove.setText(usuario.getProviderId());
        TextView telf = view.findViewById(R.id.telefono);
        telf.setText(usuario.getPhoneNumber());
        TextView Uid = view.findViewById(R.id.uid);
        Uid.setText(usuario.getUid());

        return view;
    }


}