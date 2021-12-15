package com.yeffersoncaleno.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yeffersoncaleno.taskmanager.models.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText name, email, password, passwordVerify;
    private Button btnSignUp;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.editTextNameSignUp);
        email = findViewById(R.id.editTextEmailAddressSignUp);
        password = findViewById(R.id.editTextPassSignUp);
        passwordVerify = findViewById(R.id.editTextPassVerifySignUp);
        btnSignUp = findViewById(R.id.btnSignUpSignUp);

        initFirebase();

    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public void goToLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }

    public void signUpUser(View view) {
        if(!isEmptyFields()) {
            if(password.getText().toString().equals(passwordVerify.getText().toString())) {
                btnSignUp.setText("Registrando");
                btnSignUp.setEnabled(false);
                firebaseAuth();
            } else {
                Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth() {
        mAuth.createUserWithEmailAndPassword(
                email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            userRegister();
                            clearFields();
                            Toast.makeText(getApplicationContext(),
                                    "Usuario creado exitosamente.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),
                                    TasksActivity.class));
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                password.setError(getString(R.string.error_weak_password));
                                password.requestFocus();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                email.setError(getString(R.string.error_invalid_email));
                                email.requestFocus();
                            } catch(FirebaseAuthUserCollisionException e) {
                                email.setError(getString(R.string.error_user_exists));
                                email.requestFocus();
                            } catch(Exception e) {
                                System.out.println(e.getMessage());
                            }
                            Toast.makeText(getApplicationContext(),
                                    "Registro de usuario fallido.",
                                    Toast.LENGTH_SHORT).show();
                            btnSignUp.setText(getString(R.string.btn_signUp));
                            btnSignUp.setEnabled(true);
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private Boolean isEmptyFields() {
        Boolean res = false;
        if(name.getText().toString().equals("")) {
            res = true;
            name.setError(getString(R.string.empty_field));
            name.requestFocus();
        }
        if(email.getText().toString().equals("")) {
            res = true;
            email.setError(getString(R.string.empty_field));
            email.requestFocus();
        }
        if(password.getText().toString().equals("")) {
            res = true;
            password.setError(getString(R.string.empty_field));
            password.requestFocus();
        }
        if(passwordVerify.getText().toString().equals("")) {
            res = true;
            passwordVerify.setError(getString(R.string.empty_field));
            passwordVerify.requestFocus();
        }
        return res;
    }

    private void clearFields() {
        name.setText("");
        email.setText("");
        password.setText("");
        passwordVerify.setText("");
    }

    private void userRegister() {
        User user = new User();
        user.setUid(mAuth.getCurrentUser().getUid());
        user.setUserName(name.getText().toString());
        user.setUserEmail(email.getText().toString());
        user.setUserCreated(new Date());
        user.setUserUpdated(new Date());
        reference.child(getString(R.string.user_collection)).child(user.getUid())
                .setValue(user);
    }
}