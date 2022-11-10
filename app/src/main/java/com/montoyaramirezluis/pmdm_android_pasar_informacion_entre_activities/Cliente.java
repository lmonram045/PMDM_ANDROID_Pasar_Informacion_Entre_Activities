package com.montoyaramirezluis.pmdm_android_pasar_informacion_entre_activities;

import java.io.Serializable;

/**
 * implementamos Serializable para poder pasar objetos de esta clase entre actividades
 */
public class Cliente implements Serializable {
    // Variables de clase
    private int codigo;
    private String nombre;

    // Constructor
    public Cliente(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
