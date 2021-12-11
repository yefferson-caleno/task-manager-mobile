package com.yeffersoncaleno.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    List<TaskCardActivity> cardActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        init();
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
}