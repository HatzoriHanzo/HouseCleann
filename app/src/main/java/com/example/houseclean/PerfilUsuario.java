package com.example.houseclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerfilUsuario extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    EditText txtNome, txtEmail;
    ImageView mUserimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        txtNome = (EditText) findViewById(R.id.txtnomeUser);
        txtEmail = (EditText) findViewById(R.id.txtcpfUser);
        mUserimg = (ImageView) findViewById(R.id.imagemUserFirebase);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(PerfilUsuario.this, "Welcome " +user.getDisplayName()+"!", Toast.LENGTH_LONG).show();


                }

            }


        };
    }

    public void readDataFirebase(View view){

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Glide.with(PerfilUsuario.this).load(mUser.getPhotoUrl().toString()).into(mUserimg);

                    String displayName = user.getDisplayName();
                    String displayEmail = user.getEmail();

                    txtNome.setText(displayName);
                    txtEmail.setText(displayEmail);}

                }

            };




        }
    public void isLoggingOut(View view){
        try {

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(PerfilUsuario.this, "Logging out...", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(PerfilUsuario.this,MainActivity.class);
        startActivity(intent);
        finish();
        return;

    }

}




