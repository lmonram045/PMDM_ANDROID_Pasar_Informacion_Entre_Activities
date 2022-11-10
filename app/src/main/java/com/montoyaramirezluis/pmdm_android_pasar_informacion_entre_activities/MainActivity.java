package com.montoyaramirezluis.pmdm_android_pasar_informacion_entre_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Para pasar información de un activity a otra:
    //Usando el objeto Bundle. Se puede usar directamente el Intent, pero por unificar usamos únicamente el Bundle.

    //Para recoger información hay dos formas:
    //1.- Usando startActivityForResult. Esta forma está DEPRECADA. Esto significa que en X versiones dejará de funcionar.
    //2.- Utilizando ActivityResultLauncher. Más compleja pero válida actualmente.
    //3.- Utilizando el objeto Application. Se utiliza globalmente a todas las activities. Pasos:
    //  3.1.- Crear un objeto myApplication que herede de Application. Añadir las propiedades que queremos que tenga.
    //  3.2.- Añadir en el manifest.xml una propiedad al nodo application, android:name="nombreclase"
    //  <application
    //      android:name=".MyApplication"
    //  3.3.- Utilizar la clase donde sea necesario instanciando un objeto y utilizando lo necesario:
    //      MyApplication mApplication = (MyApplication)getApplicationContext();
    //      mApplication.getLista().add(new Cliente(100, "Primer cliente"));

    //Por si necesitamos saber de qué activity vienen los datos
    private int REQUESTCODE_SECOND_ACTIVITY = 1;
    private int REQUESTCODE_THIRD_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cargamos los datos que vamos a pasar por Serialización de una activity a otra
        Clientes clientes = new Clientes();
        clientes.cargar();

        //Esta primera forma es utilizando startActivityForResult
        Button b1 = findViewById(R.id.bOpcion1);
        b1OnClick(clientes, b1);

        //Segunda forma, utilizando un ActivityResultLauncher
        Button b2 = findViewById(R.id.bOpcion2);
        b2OnClick(clientes, b2);

        //Tercera forma, utilizando un objeto global
        Button b3 = findViewById(R.id.bOpcion3);
        b3OnClick(b3);

    }

    private void b3OnClick(Button b3) {
        b3.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TerceraActivity.class);
            //Lanzamos la nueva activity
            startActivityForResult(intent, REQUESTCODE_THIRD_ACTIVITY);
        });
    }

    private void b2OnClick(Clientes clientes, Button b2) {
        b2.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SegundaActivity.class);

            //Cargamos el bundle con la información a pasar:
            //Podemos pasar tipos básicos: Integer, String,...con bundle.putXXXXXX()

            Bundle bundle = new Bundle();
            bundle.putString("key_string", "Opción 2");
            bundle.putInt("key_int", 150);
            bundle.putDouble("key_double", 22.45);
            bundle.putBoolean("key_boolean", false);

            //Podemos pasar un objeto con bundle.putSerializable();
            bundle.putSerializable("clientes", clientes);

            //Cargamos el bundle en el Intent
            intent.putExtras(bundle);

            //Asociamos el Intent (ya sabe qué activity abrir) al lanzador.
            lanzador.launch(intent);
        });
    }

    private void b1OnClick(Clientes clientes, Button b1) {
        b1.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), SegundaActivity.class);

            //Cargamos el bundle con la información a pasar:
            //Podemos pasar tipos básicos: Integer, String,...con bundle.putXXXXXX()
            //Podemos pasar un objeto con bundle.putSerializable();
            //NOTA. Es necesario que el objeto y todos los objetos que utilice implementen la interfaz Serializable.
            //Mirar la clase Cliente y Clientes cómo implementan Serializable

            Bundle bundle = new Bundle();
            bundle.putString("key_string", "Opción 1");
            bundle.putInt("key_int", 120);
            bundle.putDouble("key_double", 10.45);
            bundle.putBoolean("key_boolean", true);

            //Podemos pasar un objeto con bundle.putSerializable();
            bundle.putSerializable("clientes", clientes);

            //Cargamos el bundle en el Intent
            intent.putExtras(bundle);

            //Lanzamos la nueva activity
            startActivityForResult(intent, REQUESTCODE_SECOND_ACTIVITY);

        });
    }

    //Recogemos la información usando la primera forma onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTCODE_SECOND_ACTIVITY){
            if (resultCode == RESULT_OK){

                //Recogemos la información y generamos un objeto Bundle
                //Y leemos cada clave usando el getXXXXX() que corresponda.
                Bundle bundle = data.getExtras();
                String resultado =  bundle.getString("return");
                Toast.makeText(getApplicationContext(), "Opcion 1: " + resultado, Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUESTCODE_THIRD_ACTIVITY){
            MyApplication mApplication = (MyApplication)getApplicationContext();
            Toast.makeText(getApplicationContext(), "Elementos lista: " + String.valueOf(mApplication.getLista().size()),Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Application: " + mApplication.getLista().get(0).getNombre(),Toast.LENGTH_SHORT).show();
        }

    }

    ActivityResultLauncher<Intent> lanzador = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    //Si todo ha ido OK --> RESULT_OK
                    if (result.getResultCode() == RESULT_OK) {

                        //Recogemos la información y generamos un objeto Bundle
                        //Y leemos cada clave usando el getXXXXX() que corresponda.
                        Bundle bundle = result.getData().getExtras();
                        String resultado =  bundle.getString("return");

                        //Tambien podemos recoger un objeto con bundle.getSerializable()
                        Clientes clientes = (Clientes) bundle.getSerializable("clientes");

                        //Mostramos distinta información.
                        Toast.makeText(getApplicationContext(), "Opcion 2: " + resultado, Toast.LENGTH_SHORT).show();

                        //Vemos como la lista de objetos cliente ha sido cambiada en la activity secundaria
                        Toast.makeText(getApplicationContext(), "Elementos lista: " + String.valueOf(clientes.getLista().size()),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Elemento 0: " + clientes.getLista().get(0).getNombre(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

}