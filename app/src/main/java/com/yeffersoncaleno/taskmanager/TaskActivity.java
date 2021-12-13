package com.yeffersoncaleno.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yeffersoncaleno.taskmanager.models.State;
import com.yeffersoncaleno.taskmanager.models.Task;
import com.yeffersoncaleno.taskmanager.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private String userCreatedId;
    private Spinner spinner;
    private Button btnSaveTask;
    private List<State> states;
    private List<String> stateDescriptions;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    ArrayAdapter<String> arrayAdapterState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        title = findViewById(R.id.editTextTitleTask);
        description = findViewById(R.id.editTextDescriptionTask);
        spinner = findViewById(R.id.spinnerStateTask);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        mAuth = FirebaseAuth.getInstance();
        states = new ArrayList<>();
        stateDescriptions = new ArrayList<>();

        initFirebase();
        getAllStates();
        findUserId();

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTaskConfirm();
            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public void goToTasks(View view) {
        startActivity(new Intent(this, TasksActivity.class));
    }

    private void getAllStates() {
        reference.child(getString(R.string.state_collection)).addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                states.clear();
                stateDescriptions.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    State state = dataSnapshot.getValue(State.class);
                    states.add(state);
                }
                orderByCreated(states);
                arrayAdapterState = new ArrayAdapter<>(TaskActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, getDescriptionStates(states));
                spinner.setAdapter(arrayAdapterState);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<String> getDescriptionStates(List<State> states) {
        List<String> stateDescriptions = new ArrayList<>();
        stateDescriptions.add(getString(R.string.state_prompt));
        for(State state:states) {
            stateDescriptions.add(state.getStateDescription());
        }
        return stateDescriptions;
    }

    private void orderByCreated(List<State> states) {
        Collections.sort(states, new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                return o1.getStateCreated().compareTo(o2.getStateCreated());
            }
        });
    }

    private Boolean isEmptyFields() {
        Boolean res = false;
        if(title.getText().toString().equals("")) {
            res = true;
            title.setError(getString(R.string.empty_field));
        }
        System.out.println();
        if(spinner.getSelectedItem().toString().equals("") ||
                spinner.getSelectedItem().toString().equals(getString(R.string.state_prompt))) {
            res = true;
            ((TextView)spinner.getSelectedView()).setError(getString(R.string.empty_field));
        }

        if(description.getText().toString().equals("")) {
            res = true;
            description.setError(getString(R.string.empty_field));
        }
        return res;
    }

    private void clearFields() {
        title.setText("");
        spinner.setSelection(0);
        description.setText("");
    }

    private void saveTaskConfirm() {
        if(!isEmptyFields()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(TaskActivity.this);
            alert.setTitle("Guardar Tarea").setMessage("¿Estás seguro de guardar la Tarea?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveTask();
                            Toast.makeText(getApplicationContext(),
                                    "Tarea creada exitosamente.",
                                    Toast.LENGTH_SHORT).show();
                            clearFields();
                            startActivity(new Intent(getApplicationContext(), TasksActivity.class));
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
    }

    private void saveTask() {
        Task task = new Task();
        task.setUid(UUID.randomUUID().toString());
        task.setTaskTitle(title.getText().toString());
        task.setStateId(findStateIdByDescription(spinner.getSelectedItem().toString()));
        task.setTaskDescription(description.getText().toString());
        task.setUserCreatedId(userCreatedId);
        task.setTaskInit(new Date());
        task.setTaskEnd(new Date());
        task.setTaskCreated(new Date());
        task.setTaskUpdated(new Date());
        reference.child(getString(R.string.task_collection)).child(task.getUid()).setValue(task);
    }

    private void findUserId() {
        reference.child(getString(R.string.user_collection)).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            User user = dataSnapshot.getValue(User.class);
                            if(user.getUserEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                userCreatedId = user.getUid();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private String findStateIdByDescription(String description) {
        String uid = null;
        for(State state: states) {
            if(state.getStateDescription().equals(description)) {
                uid = state.getUid();
            }
        }
        return uid;
    }
}