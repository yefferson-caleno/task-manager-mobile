package com.yeffersoncaleno.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        goToApp();
    }

    private void goToApp() {
        if(mAuth.getCurrentUser()!=null) {
            startActivity(new Intent(this, TaskActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}