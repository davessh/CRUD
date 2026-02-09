package com.david;

import com.david.dao.DireccionDao;
import com.david.modelo.Direccion;

import java.sql.SQLException;
import java.util.List;

public class PruebaDireccion {

    public static void main(String[] args) {

        DireccionDao direccionDao = new DireccionDao();

        try {
            System.out.println("prueba insertar direccion");
            long id1 = direccionDao.encontrarOCrear(
                    "Blvd. Benito Juárez S/N, Parcela 44, 21280 Mexicali, B.C."
            );
            System.out.println("Direccion ID: " + id1);

            long id2 = direccionDao.encontrarOCrear(
                    "Blvd. Benito Juárez s/n, Residencias, 21285 Mexicali, B.C."
            );
            System.out.println("Direccion ID: " + id2);

            // Intento de direccion duplicada
            long id3 = direccionDao.encontrarOCrear(
                    "Blvd. Benito Juárez S/N, Parcela 44, 21280 Mexicali, B.C."
            );
            System.out.println("Direccion reutilizada ID: " + id3);

            System.out.println("\nLista de direcciones");
            List<Direccion> direcciones = direccionDao.listarTodas();
            for (Direccion d : direcciones) {
                System.out.println(d.getId() + " | " + d.getDescripcion());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}