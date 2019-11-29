package com.example.houseclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PerfilUsuario extends AppCompatActivity {
    EditText txtNome, txtEmail;
    ImageView mUserimg;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_perfil_usuario);
        txtNome = findViewById(R.id.txtnomeUser);
        txtEmail = findViewById(R.id.txtcpfUser);
        mUserimg = findViewById(R.id.imagemUserFirebase);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Cliente").child(currentuser);

    }

    public List<User> users = new ArrayList<>();
    public void readDataFirebase(View view) {
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        mFirebaseDatabase.child("Cliente").addValueEventListener(new ValueEventListener() {
            List<User> post = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.child(currentuser).getValue(User.class);
                    post.add(user);
                }
                    users = post;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PerfilUsuario.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        String a = "";
    }

    public void isLoggingOut(View view) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(PerfilUsuario.this, "Logging out...", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(PerfilUsuario.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(PerfilUsuario.this, "Welcome " + mAuth.getCurrentUser().getDisplayName() + "!", Toast.LENGTH_LONG).show();
        }
    }
}





