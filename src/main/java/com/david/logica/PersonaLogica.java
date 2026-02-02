package com.david.logica;

import com.david.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class PersonaLogica {

    public int crearPersonaConTelefonos(String nombre, String direccion, List<String> telefonos) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        try (Connection con = ConnectionFactory.getConnection()) {
            con.setAutoCommit(false);

            try {
                int personaId;
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO Personas(nombre, direccion) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, nombre.trim());
                    ps.setString(2, direccion);
                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) throw new IllegalStateException("No se generó ID de persona.");
                        personaId = keys.getInt(1);
                    }
                }

                if (telefonos != null && !telefonos.isEmpty()) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO Telefonos(personaId, telefono) VALUES (?, ?)")) {

                        for (String t : telefonos) {
                            if (t == null || t.trim().isEmpty()) continue;
                            ps.setInt(1, personaId);
                            ps.setString(2, t.trim());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                }

                con.commit();
                return personaId;

            } catch (Exception e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public void actualizarPersonaConTelefonos(int personaId, String nombre, String direccion, List<String> telefonos) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        try (Connection con = ConnectionFactory.getConnection()) {
            con.setAutoCommit(false);

            try {
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE Personas SET nombre=?, direccion=? WHERE id=?")) {

                    ps.setString(1, nombre.trim());
                    ps.setString(2, direccion);
                    ps.setInt(3, personaId);
                    ps.executeUpdate();
                }

                try (PreparedStatement del = con.prepareStatement(
                        "DELETE FROM Telefonos WHERE personaId=?")) {
                    del.setInt(1, personaId);
                    del.executeUpdate();
                }

                if (telefonos != null && !telefonos.isEmpty()) {
                    try (PreparedStatement ins = con.prepareStatement(
                            "INSERT INTO Telefonos(personaId, telefono) VALUES (?, ?)")) {

                        for (String t : telefonos) {
                            if (t == null || t.trim().isEmpty()) continue;
                            ins.setInt(1, personaId);
                            ins.setString(2, t.trim());
                            ins.addBatch();
                        }
                        ins.executeBatch();
                    }
                }

                con.commit();

            } catch (Exception e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}
