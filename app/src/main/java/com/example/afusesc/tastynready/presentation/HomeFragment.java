package com.example.afusesc.tastynready.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.afusesc.tastynready.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {

    Button reserButton;
    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        reserButton = view.findViewById(R.id.reservaButton);
        reserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usuario != null){
                    Intent intent = new Intent(getActivity(), ReservasActivity.class);
                    startActivity(intent);
                }else{
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginIntent);
                }

            }
        });

        return view;
    }
}