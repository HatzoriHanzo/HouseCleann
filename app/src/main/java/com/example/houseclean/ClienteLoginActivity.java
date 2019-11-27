package com.example.houseclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


    public class ClienteLoginActivity extends AppCompatActivity {
    private Button buttonLogin,buttonRegistration;
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
                if (user!=null){
                    Intent intent = new Intent(ClienteLoginActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        etxtEmail = (EditText) findViewById(R.id.emailCliente);
        etxtPassword = (EditText) findViewById(R.id.passwordCliente);

        buttonLogin = (Button) findViewById(R.id.loginCliente);
        buttonRegistration = (Button) findViewById(R.id.registrationCliente);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etxtEmail.getText().toString();
                final String password = etxtPassword.getText().toString();
                nAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(ClienteLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(ClienteLoginActivity.this,"sign up error",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String user_id = nAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Cliente").child(user_id);
                            current_user_db.setValue(true);

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
                nAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(ClienteLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(ClienteLoginActivity.this,"sign up error",Toast.LENGTH_SHORT).show();
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
        nAuth.signOut();
    }
}