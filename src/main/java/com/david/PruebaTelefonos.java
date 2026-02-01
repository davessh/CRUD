package com.david;

import com.david.dao.PersonaDao;
import com.david.dao.TelefonoDao;
import com.david.modelo.Persona;
import com.david.modelo.Telefono;

public class PruebaTelefonos {

    public static void main(String[] args) throws Exception {
        PersonaDao personaDao = new PersonaDao();
        TelefonoDao telDao = new TelefonoDao();

        Persona p = personaDao.obtenerTodas().get(0);
        int personaId = p.getId();

        System.out.println("Persona elegida: " + p);

        System.out.println("\nLista de teléfonos: ");
        for (Telefono t : telDao.obtenerPorPersona(personaId)) {
            System.out.println(t);
        }

        System.out.println("\nInsertar nuevo telefono: ");
        int telId = telDao.insertar(personaId, "686-142-6639");
        System.out.println("Tel insertado con id: " + telId);

        System.out.println("\nTeléfonos despues de insertar: ");
        for (Telefono t : telDao.obtenerPorPersona(personaId)) {
            System.out.println(t);
        }

        System.out.println("\n Actualizar teléfono (id " + telId + ")");
        telDao.actualizar(telId, "686-578-4842");

        System.out.println("\n Teléfonos despues de actualizar: ");
        for (Telefono t : telDao.obtenerPorPersona(personaId)) {
            System.out.println(t);
        }

        //System.out.println("\nEliminar teléfono(id " + telId + ")");
        //telDao.eliminar(telId);

        System.out.println("\nLista final de telefonos: ");
        for (Telefono t : telDao.obtenerPorPersona(personaId)) {
            System.out.println(t);
        }
    }
}

