package com.example.houseclean;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button buttonCliente, buttonDiarista,buttonMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCliente = (Button) findViewById(R.id.buttonCliente);
        buttonDiarista = (Button) findViewById(R.id.buttonDiarista);
        buttonMapa = (Button) findViewById(R.id.buttonMapa);

        buttonCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClienteLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }



        });

        buttonDiarista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiaristaLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }



        });
        buttonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
                return;
            }



        });
}


}









