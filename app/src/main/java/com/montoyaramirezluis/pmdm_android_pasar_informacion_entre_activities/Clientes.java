package com.montoyaramirezluis.pmdm_android_pasar_informacion_entre_activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * implementamos Serializable para poder pasar objetos de esta clase entre actividades
 */
public class Clientes implements Serializable {

    private List<Cliente> lista;

    // Constructor por defecto
    public Clientes() {
        lista = new ArrayList<>();
    }

    // Constructor con parámetros
    public Clientes(List<Cliente> lista) {
        this.lista = lista;
    }

    public List<Cliente> getLista() {
        return lista;
    }

    public void setLista(List<Cliente> lista) {
        this.lista = lista;
    }

    // método para cargar una lista de clientes
    public void cargar() {
        // creamos 10 clientes genéricos para el ejemplo
        for (int i = 0; i < 10; i++) {
            lista.add(new Cliente(i, "Cliente " + i));
        }

        // pongo mi nombre en el primero para el ejemplo
        lista.get(0).setNombre("Luis");
    }

}
