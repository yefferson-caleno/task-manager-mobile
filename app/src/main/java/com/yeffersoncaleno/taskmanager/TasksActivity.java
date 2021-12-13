package com.yeffersoncaleno.taskmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yeffersoncaleno.taskmanager.models.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TasksActivity extends AppCompatActivity {

    List<TaskCardActivity> cardActivities;
    private FirebaseAuth mAuth;
    private Button btnLogoutAlert;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        mAuth = FirebaseAuth.getInstance();
        btnLogoutAlert = findViewById(R.id.btnLogout);

        initFirebase();
        init();

        btnLogoutAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutConfirm();
            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public void init() {
        cardActivities = new ArrayList<>();
        cardActivities.add(new TaskCardActivity("Diseñar DB.", "Nueva"));
        cardActivities.add(new TaskCardActivity("Mockup Frontend.", "Activa"));
        cardActivities.add(new TaskCardActivity("Maquetar Frontend", "Impedimento"));
        cardActivities.add(new TaskCardActivity("Diseñar Backend.", "Cerrada"));
        cardActivities.add(new TaskCardActivity("Crear Backend.", "Nueva"));
        cardActivities.add(new TaskCardActivity("Conectar Front-Back.", "Impedimento"));
        cardActivities.add(new TaskCardActivity("Realizar pruebas.", "Cerrada"));
        cardActivities.add(new TaskCardActivity("Ajustes finales", "Activa"));

        TaskCardAdapter taskCardAdapter = new TaskCardAdapter(cardActivities, this);
        RecyclerView recyclerView = findViewById(R.id.reciclerViewTask);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskCardAdapter);
    }

    public void goToTask(View view) {
        startActivity(new Intent(this, TaskActivity.class));
    }

    private void logoutConfirm() {
        AlertDialog.Builder alert = new AlertDialog.Builder(TasksActivity.this);
        alert.setTitle("Cerrar Sesión").setMessage("¿Deseas salir de la aplicaci\u00f3n?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alert.create();
        alert.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK) {logoutConfirm();}
        return super.onKeyDown(keyCode, event);
    }
}