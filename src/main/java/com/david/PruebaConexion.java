package com.david;

import com.david.db.ConnectionFactory;

public class PruebaConexion {

    public static void main(String[] args) throws Exception {
        try (var con = ConnectionFactory.getConnection()) {
            System.out.println("Conexión Exitosa -" + con.getMetaData().getURL());
        }
    }
}

