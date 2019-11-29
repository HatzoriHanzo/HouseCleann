package com.example.houseclean;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class ClienteCadastro extends AppCompatActivity {
    public Uri imguri;
    Button buttonCadastrar, buttonChooseImg;
    ImageView img;
    StorageReference mStorageRef;
    User user;
    DatabaseReference mDbRef;
    EditText userName, userIdade, userCpf;
    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_cadastro);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mStorageRef = FirebaseStorage.getInstance().getReference("ClientesAvatars");
        mDbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);

        buttonCadastrar = findViewById(R.id.cadastrarUser);
        buttonChooseImg = findViewById(R.id.fotoGaleriaUser);
        img = findViewById(R.id.imagemUser);
        userName = findViewById(R.id.txtnomeUser);
        userIdade = findViewById(R.id.txtidadeUser);
        userCpf = findViewById(R.id.txtcpfUser);

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
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(ClienteCadastro.this, "File Being Uplodaded!", Toast.LENGTH_LONG).show();

                } else {
                    Fileuploader();
                }
            }
        });
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader() {
        String imageId;
        imageId = System.currentTimeMillis() + "." + getExtension(imguri);
        user.setNome(userName.getText().toString());
        user.setImageId(imageId);
        user.setIdade(Integer.parseInt(userIdade.getText().toString().trim()));
        user.setCpf(userCpf.getText().toString().trim());
        user.setDiarista(false);
        mDbRef.setValue(user);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference reference = mStorageRef.child(imageId);

        reference.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(ClienteCadastro.this, "Cadastro Realizado com Sucesso!", Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(ClienteCadastro.this, MapsActivity.class);
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
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            img.setImageURI(imguri);
        }
    }
}
