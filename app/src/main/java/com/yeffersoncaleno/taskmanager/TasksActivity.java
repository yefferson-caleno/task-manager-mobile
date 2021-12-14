package com.yeffersoncaleno.taskmanager;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yeffersoncaleno.taskmanager.models.State;
import com.yeffersoncaleno.taskmanager.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    List<TaskCardActivity> cardActivities;
    private FirebaseAuth mAuth;
    private Button btnLogoutAlert;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<State> states;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        mAuth = FirebaseAuth.getInstance();
        btnLogoutAlert = findViewById(R.id.btnLogout);

        states = new ArrayList<>();
        cardActivities = new ArrayList<>();

        initFirebase();
        getAllStates();

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

    private void getAllStates() {
        reference.child(getString(R.string.state_collection)).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        states.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            State state = dataSnapshot.getValue(State.class);
                            states.add(state);
                        }
                        getAllTask();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void getAllTask() {
        reference.child(getString(R.string.task_collection)).addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cardActivities.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Task task = dataSnapshot.getValue(Task.class);
                    if(task.getUserCreatedId().equals(mAuth.getCurrentUser().getUid())) {
                        cardActivities.add(new TaskCardActivity(task.getUid(), task.getTaskTitle(),
                                findStateDescriptionById(task.getStateId())));
                    }
                }
                TaskCardAdapter taskCardAdapter = new TaskCardAdapter(cardActivities,
                        TasksActivity.this);
                RecyclerView recyclerView = findViewById(R.id.reciclerViewTask);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(TasksActivity.this));
                recyclerView.setAdapter(taskCardAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    private String findStateDescriptionById(String uid) {
        String res = "";
        for(State state: states) {
            if(state.getUid().equals(uid)) {
                res = state.getStateDescription();
            }
        }
        return res;
    }
}