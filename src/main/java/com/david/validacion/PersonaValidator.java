package com.david.validacion;

import java.util.ArrayList;
import java.util.List;

public class PersonaValidator {

    public List<String> validar(String nombre, String direccion, List<String> telefonos) {
        List<String> errores = new ArrayList<>();

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.add("El nombre es obligatorio.");
        }


        if (telefonos != null) {
            for (String t : telefonos) {
                if (t == null || t.trim().isEmpty()) continue;
                if (t.trim().length() < 7) {
                    errores.add("Teléfono inválido: " + t);
                }
            }
        }

        return errores;
    }
}