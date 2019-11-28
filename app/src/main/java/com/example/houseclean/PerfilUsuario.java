package com.example.houseclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilUsuario extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String UserId;
    User user;


    EditText txtNome,txtCpf,txtIdade;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_perfil_usuario);

        txtNome = (EditText) findViewById(R.id.txtnomeUser);
        txtCpf = (EditText) findViewById(R.id.txtcpfUser);
        txtIdade = (EditText) findViewById(R.id.txtidadeUser);


        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Users");
        UserId = mFirebaseDatabase.push().getKey();


    }

    public void updateUser(User user){

        mFirebaseDatabase.child("Users").child(UserId).child("nome").setValue(user.getNome());
        mFirebaseDatabase.child("Users").child(UserId).child("cpf").setValue(user.getCpf());
        mFirebaseDatabase.child("Users").child(UserId).child("idade").setValue(user.getIdade());

    }

    public void updateData(View view){
        updateUser(user);
    }

    public void readData(View view){
        mFirebaseDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String dbuser = ds.child("nome").getValue(String.class);
                        String dbcpf = ds.child("cpf").getValue(String.class);
                        Integer dbidade = ds.child("idade").getValue(Integer.class);
                        Log.d("Tag",dbuser+"/"+dbcpf+"/"+dbidade);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }







}

