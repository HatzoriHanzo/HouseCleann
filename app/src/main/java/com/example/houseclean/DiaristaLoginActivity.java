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

public class DiaristaLoginActivity extends AppCompatActivity {

    private Button buttonLogin,buttonRegistration;
    private EditText etxtEmail, etxtPassword;
    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaristas_login);

        nAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null){
                    Toast.makeText(DiaristaLoginActivity.this,"Welcome",Toast.LENGTH_LONG).show();
                }
            }
        };
        etxtEmail = (EditText) findViewById(R.id.emailDiarista);
        etxtPassword = (EditText) findViewById(R.id.passwordDiarista);

        buttonLogin = (Button) findViewById(R.id.loginDiaristaa);
        buttonRegistration = (Button) findViewById(R.id.registrationDiarista);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etxtEmail.getText().toString();
                final String password = etxtPassword.getText().toString();
                nAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(DiaristaLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(DiaristaLoginActivity.this,"Make sure your password is 6 digits long!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(DiaristaLoginActivity.this, DiaristaCadastro.class);
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
                nAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(DiaristaLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(DiaristaLoginActivity.this,"sign up error",Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(DiaristaLoginActivity.this, MapsActivity.class);
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


