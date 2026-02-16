package com.david.validacion;

import com.david.modelo.Contacto;

import java.util.ArrayList;
import java.util.List;

public class ContactoValidator {

    public List<String> validar(Contacto c) {
        List<String> errores = new ArrayList<>();
        if (c == null) {
            errores.add("Contacto no puede ser null.");
            return errores;
        }
        if (c.getEtiqueta() == null || c.getEtiqueta().trim().isEmpty()) {
            errores.add("Etiqueta de contacto obligatoria.");
        }
        if (c.getValor() == null || c.getValor().trim().isEmpty()) {
            errores.add("Valor de contacto obligatorio.");
        }
        return errores;
    }
}