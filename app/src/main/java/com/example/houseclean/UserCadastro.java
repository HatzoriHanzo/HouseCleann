package com.example.houseclean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class UserCadastro extends AppCompatActivity {
    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button buttonCadastrar,buttonChooseImg;
    ImageView img;
    StorageReference mStorageRef;
    public Uri imguri;
    private StorageTask uploadTask;
    User user;
    DatabaseReference mDbRef;
    EditText userName,userIdade,userCpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_cadastro);

        mStorageRef = FirebaseStorage.getInstance().getReference("ClientesAvatars");
        mDbRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Cliente").child("CadastrosClientes");
        buttonCadastrar = (Button) findViewById(R.id.cadastrarUser);
        buttonChooseImg = (Button) findViewById(R.id.fotoGaleriaUser);
        img = (ImageView) findViewById(R.id.imagemUser);
        userName = (EditText) findViewById(R.id.txtnomeUser);
        userIdade = (EditText) findViewById(R.id.txtidadeUser);
        userCpf = (EditText) findViewById(R.id.txtcpfUser);

        user = new User();


        buttonChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }
        });
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(UserCadastro.this,"File Being Uplodaded!",Toast.LENGTH_LONG).show();

                }else{
                    Fileuploader();
                }
            }
        });
    }
    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void Fileuploader() {
        String imageId;
        imageId = System.currentTimeMillis()+"."+getExtension(imguri);
        user.setNome(userName.getText().toString().trim());
        user.setImageId(imageId);
        int p = Integer.parseInt(userIdade.getText().toString().trim());
        user.setIdade(p);
        user.setCpf(userCpf.getText().toString().trim());
        mDbRef.push().setValue(user);

        StorageReference reference = mStorageRef.child(imageId);

        reference.putFile(imguri)


                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(UserCadastro.this,"Cadastro Realizado com Sucesso!",Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(UserCadastro.this, MapsActivity.class);
                        startActivity(intent);
                        finish();
                        return;

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void Filechooser() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(intent, 1);
        } catch (Exception ex) {

            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData() != null){
             imguri = data.getData();
             img.setImageURI(imguri);
        }
    }
}
