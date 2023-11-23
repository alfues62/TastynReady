package com.example.afusesc.tastynready.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.FirebaseHandler;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private MenuItem perfilMenuItem;
    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseHandler firebaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu navMenu = navigationView.getMenu();
        perfilMenuItem = navMenu.findItem(R.id.nav_logout);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // COMENTAR Y DESCOMENTAR CUANDO HAYA QUE AÑADIR PLATOS
        //firebaseHandler = new FirebaseHandler();
        //firebaseHandler.ponerPlatosFirebase();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    protected void onStart() {
        super.onStart();
        usuario = FirebaseAuth.getInstance().getCurrentUser();

        if (usuario != null) {
            perfilMenuItem.setVisible(true); // Mostrar el elemento del menú si el usuario está autenticado
        } else {
            perfilMenuItem.setVisible(false); // Ocultar el elemento del menú si el usuario no está autenticado
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_home){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (id == R.id.nav_carta) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartaFragment()).commit();
        } else if(id == R.id.nav_acerca){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AcercaDeFragment()).commit();
        }
        else if (id == R.id.nav_logout) {
            AuthUI.getInstance().signOut(getApplicationContext()) .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override public void onComplete(@NonNull Task<Void> task) {
                    Intent i = new Intent( getApplicationContext (), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(i); finish(); } });
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_mi_perfil) {
            if(usuario != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentUsuario()).commit();
                return true;
            }else{
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
            }

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}