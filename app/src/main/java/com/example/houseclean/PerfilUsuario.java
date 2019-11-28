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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_perfil_usuario);
        txtNome = (EditText) findViewById(R.id.txtnomeUser);
        txtEmail = (EditText) findViewById(R.id.txtcpfUser);
        mUserimg = (ImageView) findViewById(R.id.imagemUserFirebase);


        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child(mUser.getUid());

    }



       /* mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("nome").getValue().toString();
                String cpf = dataSnapshot.child("cpf").getValue().toString();
                Integer idade = (Integer) dataSnapshot.child("idade").getValue();

                Toast.makeText(getApplicationContext(), username + "\n" + cpf + "\n" + idade, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }*/;


    public void readDataFirebase(View view) {

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    if (user.getPhotoUrl() != null) {
                        Glide.with(PerfilUsuario.this).load(user.getPhotoUrl().toString()).into(mUserimg);
                    }
                    if (user.getDisplayName() != null) {
                        String displayName = user.getDisplayName();
                        txtNome.setText(displayName);
                    }
                }

            }


        };
    }

        public void isLoggingOut (View view){
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
        protected void onStart () {
            super.onStart();
            if (mAuth.getCurrentUser() != null) {
                Toast.makeText(PerfilUsuario.this, "Welcome " + mAuth.getCurrentUser().getDisplayName() + "!", Toast.LENGTH_LONG).show();
            }

        }


}





