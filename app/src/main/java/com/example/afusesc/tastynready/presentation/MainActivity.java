package com.example.afusesc.tastynready.presentation;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.afusesc.tastynready.R;
import com.example.afusesc.tastynready.model.FirebaseHandler;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private MenuItem perfilMenuItem;
    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseHandler firebaseHandler;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private ValueEventListener reservaEventListener;


    //NOTIFICACION 1
    private NotificationManager notificationManager;
    static final String CANAL_ID = "mi_canal_foreground"; //Me lo invento
    static final int NOTIFICACION_ID = 1; //Me lo invento
    NotificationCompat.Builder notificacion; //Creo el constructor
    //NOTIFICACION2
    private NotificationManager notificationManager2;
    static final String CANAL_ID2 = "mi_otro_canal_foreground"; //Me lo invento
    static final int NOTIFICACION_ID2 = 2; //Me lo invento
    NotificationCompat.Builder notificacion2; //Creo el constructor

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


        // COMENTAR Y DESCOMENTAR CUANDO HAYA QUE AÑADIR ADMINISTRADORES

        //DataPicker dataPicker = new DataPicker();
        //dataPicker.guardarAdminEnFirebase();


        // Crear una instancia de Calendar para la fecha actual
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // Añadir 1 día a la fecha actual

        // Formatear la fecha para que coincida con el formato de la fecha en Firebase
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaManana = sdf.format(calendar.getTime());


        // Consultar Firebase para las reservas del día siguiente
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();


        //String idUsuario = "FdCGourbIleLFFoZaUBPkhfbzu82";

        //FORMA 1 DE HACERLO
        db.collection("reservas")
                .whereEqualTo("IdUser", usuario.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Aquí puedes imprimir o manipular la información de cada reserva
                            String fecha = document.getString("Fecha");
                            if (fecha != null && fecha.equals(fechaManana)) {
                                crearNotificacionReserva();
                            }
                        }
                    } else {
                        Log.e(TAG, "Error obteniendo documentos", task.getException());
                    }
                });


        verificarYProcesarNotificacion();
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

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (id == R.id.nav_carta) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartaFragment()).commit();
        } else if (id == R.id.nav_acerca) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AcercaDeFragment()).commit();
        } else if (id == R.id.nav_logout) {

            AuthUI.getInstance().signOut(getApplicationContext()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            });

        } else if (id == R.id.contacta) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactaFragment()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_mi_perfil) {
            if (usuario != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentUsuario()).commit();
                return true;
            } else {
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


    private void crearNotificacionReserva() {
        // Usar un asistente de notificaciones para crear un canal (o categoría) de notificaciones.
        //Esto siempre es igual
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(CANAL_ID, "Mis Notificaciones foreground",
                            NotificationManager.IMPORTANCE_HIGH); //La importancia de la notificacion

            notificationChannel.setDescription("Descripcion del canal foreground");
            notificationManager.createNotificationChannel(notificationChannel);

        }

        // Creamos la notificación.
        notificacion =
                new NotificationCompat.Builder(this, CANAL_ID)
                        .setContentTitle("RECORDATORIO")
                        .setContentText("Usted tiene programada una reserva para mañana")
                        .setSmallIcon(android.R.drawable.ic_popup_reminder)
                        .setDefaults(Notification.DEFAULT_ALL)  // Añadir sonido y vibración
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);  // Establecer prioridad alta

        notificationManager.notify(NOTIFICACION_ID, notificacion.build());
        Log.d(TAG, "Notificación creada exitosamente");
    }

    private void crearNotificacionLlamada() {
        Log.d(TAG, "Creando notificación de llamada");
        // Usar un asistente de notificaciones para crear un canal (o categoría) de notificaciones.
        //Esto siempre es igual
        notificationManager2 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(CANAL_ID2, "Mis Notificaciones foreground",
                            NotificationManager.IMPORTANCE_DEFAULT); //La importancia de la notificacion

            notificationChannel.setDescription("Descripcion del canal foreground");
            notificationManager2.createNotificationChannel(notificationChannel);

        }

        // Creamos la notificación.
        notificacion2 =
                new NotificationCompat.Builder(this, CANAL_ID2)
                        .setContentTitle("AVISO")
                        .setContentText("¡LA mesa 3 te esta llamando!")
                        .setSmallIcon(android.R.drawable.ic_popup_reminder)
                        .setDefaults(Notification.DEFAULT_ALL)  // Añadir sonido y vibración
                        .setPriority(NotificationCompat.PRIORITY_HIGH);  // Establecer prioridad alta

        notificationManager2.notify(NOTIFICACION_ID2, notificacion2.build());
        Log.d(TAG, "Notificación2 creada exitosamente");
    }

    private void verificarYProcesarNotificacion() {
        String rutaAdmin = "administradores/XAILBTk7e8GkhOUy2HKl/notificacion";

        // Lee el valor de la base de datos
        databaseReference.child(rutaAdmin).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Obtén el valor actual
                Boolean notificacion = dataSnapshot.getValue(Boolean.class);

                // Si el valor es true, manda una notificación
                if (notificacion != null && notificacion) {
                    // Llama al método para mandar la notificación
                    crearNotificacionLlamada();

                    // Después de enviar la notificación, actualiza el valor a "false"
                    databaseReference.child(rutaAdmin).setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
