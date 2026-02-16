package com.david.modelo;

public interface Contacto {
    String getEtiqueta();
    String getValor();
    default String formato() {
        return getEtiqueta() + ": " + getValor();
    }
}