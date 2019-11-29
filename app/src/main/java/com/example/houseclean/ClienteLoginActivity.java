package com.example.houseclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClienteLoginActivity extends AppCompatActivity {
    private Button buttonLogin, buttonRegistration;
    private EditText etxtEmail, etxtPassword;
    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_login);

        nAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(ClienteLoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                }
            }
        };

        etxtEmail = findViewById(R.id.emailCliente);
        etxtPassword = findViewById(R.id.passwordCliente);

        buttonLogin = findViewById(R.id.loginCliente);
        buttonRegistration = findViewById(R.id.registrationCliente);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etxtEmail.getText().toString();
                final String password = etxtPassword.getText().toString();
                nAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(ClienteLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(ClienteLoginActivity.this, "Make sure your password is 6 digits long!", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(ClienteLoginActivity.this, ClienteCadastro.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etxtEmail.getText().toString();
                final String password = etxtPassword.getText().toString();
                nAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(ClienteLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(ClienteLoginActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(ClienteLoginActivity.this, MapsActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        nAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        nAuth.removeAuthStateListener(firebaseAuthListener);

    }
}