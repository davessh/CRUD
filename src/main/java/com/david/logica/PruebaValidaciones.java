package com.david.logica;

public final class PruebaValidaciones {
    private PruebaValidaciones() {}

    public static void nombreObligatorio(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
    }

    public static void telefonoValido(String tel) {
        if (tel == null || tel.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }
        if (!tel.matches("[0-9+\\-\\s]{6,20}")) {
            throw new IllegalArgumentException("Teléfono inválido: " + tel);
        }
    }
}
