package com.montoyaramirezluis.pmdm_android_pasar_informacion_entre_activities;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private List<Cliente> lista = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<Cliente> getLista() {
        return lista;
    }

    public void setLista(List<Cliente> lista) {
        this.lista = lista;
    }

    public void cargar(){
        for (int i = 0; i < 10; i++){
            lista.add(new Cliente(i, "Cliente " + String.valueOf(i)));
        }

        lista.get(0).setNombre("Sergio");

    }


}