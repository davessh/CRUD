package com.david;

import com.david.dao.TelefonoDao;
import com.david.logica.PersonaLogica;

import java.util.List;

public class PruebaLogica {

    public static void main(String[] args) throws Exception {
        PersonaLogica service = new PersonaLogica();
        TelefonoDao telDao = new TelefonoDao();

        System.out.println("Creación persona y telefono");
        int id = service.crearPersonaConTelefonos(
                "David Escárcega",
                "Col. Rodriguez",
                List.of("686-111-2222", "686-333-4444")
        );
        System.out.println("Persona creada con id: " + id);

        System.out.println("\nTeléfonos guardados:");
        telDao.obtenerPorPersona(id).forEach(System.out::println);

        System.out.println("\nActualizar persona y cambiar telefonos");
        service.actualizarPersonaConTelefonos(
                id,
                "David Escárcega Schlemmer",
                "Col. El Refugio",
                List.of("686-999-0000")
        );

        System.out.println("\nTeléfonos después:");
        telDao.obtenerPorPersona(id).forEach(System.out::println);

        System.out.println("\nListo.");
    }
}
