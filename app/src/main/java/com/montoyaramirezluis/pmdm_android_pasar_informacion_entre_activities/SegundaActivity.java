package com.montoyaramirezluis.pmdm_android_pasar_informacion_entre_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SegundaActivity extends AppCompatActivity {

    // Creamos un objeto de la clase Clientes
    Clientes clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        // Cargamos un objeto Intent con getIntent()
        Intent intent = getIntent();

        // Leemos el objeto que nos ha pasado la actividad anterior y generamos el Bundle
        Bundle bundle = intent.getExtras();
        String message1 = bundle.getString("key_string");
        int valorInt = bundle.getInt("key_int");
        double valorDouble = bundle.getDouble("key_double");
        boolean valorBoolean = bundle.getBoolean("key_boolean");

        // Podemos serializar el objeto que hemos mandado desde la actividad principal.
        clientes = (Clientes) bundle.getSerializable("clientes");

        // Mostramos el resultado
        Toast.makeText(this, message1
                + " " + valorInt
                + " " + valorDouble
                + " " + valorBoolean, Toast.LENGTH_SHORT).show();

        // Vemos como la lista de objetos cliente ha sido modificada en la segunda activity
        Toast.makeText(this, clientes.getLista().get(0).getNombre(), Toast.LENGTH_SHORT).show();

        Button bOk = findViewById(R.id.bOK);
        Button bCancel = findViewById(R.id.bCancel);

        bOk.setOnClickListener(bOkOnClick());

        bCancel.setOnClickListener(v -> finish());

    }

    /**
     * Método onClick para el botón bOk
     */
    private View.OnClickListener bOkOnClick() { return v -> {
        Intent intent = new Intent();

        // Mandamos información de la segunda activity a la primera
        Bundle bundle = new Bundle();
        bundle.putString("return", "Pulsamos OK");

        // Modificamos el objeto recibido para comprobar que se aplican los cambios
        clientes.getLista().get(0).setNombre("Luis Montoya");
        clientes.getLista().add(new Cliente(100, "Otro cliente"));

        // Serializamos el objeto para mandarlo de vuelta a la actividad principal
        bundle.putSerializable("clientes", clientes);

        // Añadimos el bundle al intent
        intent.putExtras(bundle);

        // Y devolvemos los valores.
        // Podemos usar RESULT_OK o RESULT_CANCELED
        setResult(RESULT_OK, intent);
        finish();
    };}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}