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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class UserCadastro extends AppCompatActivity {

    Button buttonCadastrar,buttonChooseImg;
    ImageView img;
    StorageReference mStorageRef;
    public Uri imguri;
    private StorageTask uploadTask;
    User user;
    DatabaseReference mDbRef;
    EditText userName,userIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_cadastro);
        mStorageRef = FirebaseStorage.getInstance().getReference("Avatar");
        mDbRef = FirebaseDatabase.getInstance().getReference().child("Cadastros");
        buttonCadastrar = (Button) findViewById(R.id.cadastrarUser);
        buttonChooseImg = (Button) findViewById(R.id.fotoGaleriaUser);
        img = (ImageView) findViewById(R.id.imagemUser);
        userName = (EditText) findViewById(R.id.txtnomeUser);
        userIdade = (EditText) findViewById(R.id.txtidadeUser);
        user = new User();


        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }

        });
        buttonChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(UserCadastro.this,"Imagem sendo carregada , espere um momento!",Toast.LENGTH_LONG).show();
                }
                else{
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
        int p = Integer.parseInt(userIdade.getText().toString().trim());
        user.setIdade(p);
        user.setImageId(imageId);
        mDbRef.push().setValue(user);

        StorageReference reference = mStorageRef.child(imageId);
        uploadTask = reference.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                       // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(UserCadastro.this,"Cadastro Realizado com Sucesso!",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
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