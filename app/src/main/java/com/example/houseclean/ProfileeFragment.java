package com.example.houseclean;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileeFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView avatar;
    TextView txtNome,txtCpf,txtIdade;

    public ProfileeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profilee, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        avatar = view.findViewById(R.id.avatar);
        txtNome = view.findViewById(R.id.txtnomeUser);
        txtCpf = view.findViewById(R.id.txtcpfUser);
        txtIdade = view.findViewById(R.id.txtidadeUser);

        Query query = databaseReference.orderByChild("uid").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    String name = ""+ ds.child("name").getValue();
                    String cpf = ""+ ds.child("cpf").getValue();
                    String idade = ""+ ds.child("idade").getValue();
                    String image = ""+ ds.child("image").getValue();

                    txtNome.setText(name);
                    txtCpf.setText(cpf);
                    txtIdade.setText(idade);
                    try {
                        Picasso.get().load(image).into(avatar);
                        }catch (Exception e){
                        Picasso.get().load(R.drawable.addphoto).into(avatar);
                        }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

}
