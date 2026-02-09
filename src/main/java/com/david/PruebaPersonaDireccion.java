package com.david;

import com.david.dao.DireccionDao;
import com.david.dao.PersonaDireccionDao;
import com.david.modelo.Direccion;

import java.sql.SQLException;
import java.util.List;

public class PruebaPersonaDireccion {

    public static void main(String[] args) {

        DireccionDao direccionDao = new DireccionDao();
        PersonaDireccionDao pdDao = new PersonaDireccionDao();

        try {
            int personaId = 3;

            long dirId1 = direccionDao.encontrarOCrear("Calle Zacatecas  #10, Mexicali");
            long dirId2 = direccionDao.encontrarOCrear("Calle Saturno #20, Mexicali");

            pdDao.asociar(personaId, dirId1);
            pdDao.asociar(personaId, dirId2);

            System.out.println("Direcciones de la persona " + personaId + ":");
            List<Direccion> direcciones = pdDao.obtenerDireccionesDePersona(personaId);
            for (Direccion d : direcciones) {
                System.out.println(d.getId() + " | " + d.getDescripcion());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}