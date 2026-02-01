package com.david;

import com.david.dao.PersonaDao;
import com.david.modelo.Persona;

public class PruebaPersona {
    public static void main(String[] args) throws Exception {
        PersonaDao dao = new PersonaDao();

        System.out.println("Lista de personas");
        for (Persona p : dao.obtenerTodas()) {
            System.out.println(p);
        }

        System.out.println("\nInsertar persona");
        int newId = dao.insertar("David Escárcega", "Muy muy lejano");
        System.out.println("Insertada con ID: " + newId);

        System.out.println("\nLstado despues de INSERT");
        for (Persona p : dao.obtenerTodas()) {
            System.out.println(p);
        }

        System.out.println("\nUPDATE (ID " + newId + ")");
        dao.actualizar(newId, "David Escárcega New", "Muy muy cercano");

        System.out.println("\nLista despues de UPDATE");
        for (Persona p : dao.obtenerTodas()) {
            System.out.println(p);
        }

        System.out.println("\nDELETE (ID " + newId + ")");
        dao.eliminar(newId);

        System.out.println("\nLISTADO FINAL");
        for (Persona p : dao.obtenerTodas()) {
            System.out.println(p);
        }
    }
}
