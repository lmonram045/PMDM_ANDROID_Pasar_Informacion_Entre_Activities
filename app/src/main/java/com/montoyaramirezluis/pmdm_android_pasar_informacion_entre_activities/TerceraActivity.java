package com.montoyaramirezluis.pmdm_android_pasar_informacion_entre_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TerceraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercera);

        MyApplication mApplication = (MyApplication)getApplicationContext();
        mApplication.getLista().add(new Cliente(100, "Primer cliente"));
        mApplication.getLista().get(0).setNombre("Mario Martinez Pérez");

        Button bOK = findViewById(R.id.bAceptar);
        bOK.setOnClickListener(view -> finish());



    }
}