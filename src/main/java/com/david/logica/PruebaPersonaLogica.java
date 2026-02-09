package com.david.logica;

import com.david.logica.DireccionLogica;
import com.david.modelo.Direccion;

import java.sql.SQLException;
import java.util.List;

public class PruebaPersonaLogica {

    public static void main(String[] args) throws SQLException {
        DireccionLogica service = new DireccionLogica();

        int personaId = 3;

        service.agregarDireccionAPersona(personaId, "Av. Independencia #100, Mexicali");

        List<Direccion> direcciones = service.obtenerDireccionesDePersona(personaId);
        for (Direccion d : direcciones) {
            System.out.println(d.getDescripcion());
        }
    }
}