package com.example.afusesc.tastynready.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.DataPicker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Button reserButton;
    DataPicker dataPicker = new DataPicker();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(getActivity());

        dataPicker.guardarUsuarioEnFirebase(user, "cliente");
        reserButton = view.findViewById(R.id.reservaButton);
        reserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null | googleAccount!= null) {
                    Intent intent = new Intent(getActivity(), ReservasActivity.class);
                    startActivity(intent);
                } else {
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        });

        return view;
    }
}
