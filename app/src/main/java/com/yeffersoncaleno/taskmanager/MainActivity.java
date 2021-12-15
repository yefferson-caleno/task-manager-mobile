package com.yeffersoncaleno.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

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

    @Override
    public void onResume(){
        super.onResume();
    }

    private Boolean onlineVerify() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }

    private void goToApp() {
        if(onlineVerify().booleanValue()) {
            if(mAuth.getCurrentUser()!=null) {
                startActivity(new Intent(this, TasksActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        } else {
            Toast.makeText(getApplicationContext(), "No tienes conexión a internet.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}