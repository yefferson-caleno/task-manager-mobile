package com.yeffersoncaleno.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEmailAddressLogin);
        password = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }

    public void goToForgotPassword(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    public void goToSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void signIn(View view) {
        if(!isEmptyFields()) {
            btnLogin.setText("Validando");
            btnLogin.setEnabled(false);
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(),
                                        "Usuario validado exitosamente.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),
                                        TasksActivity.class));// updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                try {
                                    throw task.getException();
                                } catch(FirebaseAuthInvalidCredentialsException e) {
                                    Toast.makeText(getApplicationContext(),
                                            getString(R.string.error_invalid_email_password),
                                            Toast.LENGTH_SHORT).show();
                                } catch(Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                Toast.makeText(getApplicationContext(),
                                        "Inicio de sesión fallido.",
                                        Toast.LENGTH_SHORT).show();
                                btnLogin.setText(getString(R.string.btn_login));
                                btnLogin.setEnabled(true);
                                // updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }

    private Boolean isEmptyFields() {
        Boolean res = false;
        if(email.getText().toString().equals("")) {
            res = true;
            email.setError(getString(R.string.empty_field));
            email.requestFocus();
        }
        if(password.getText().toString().equals("")) {
            res = true;
            password.setError(getString(R.string.empty_field));
            email.requestFocus();
        }
        return res;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            alert.setTitle("Cerrar Aplicación").setMessage("¿Deseas cerrar la aplicaci\u00f3n?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
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
        return super.onKeyDown(keyCode, event);
    }
}