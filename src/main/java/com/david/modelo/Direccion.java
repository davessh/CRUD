package com.david.modelo;

public class Direccion {
    private long id;
    private String descripcion;

    public Direccion() {}

    public Direccion(long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Direccion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}