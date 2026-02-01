package com.david.dao;
import com.david.db.ConnectionFactory;
import com.david.modelo.Telefono;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefonoDao {

    public List<Telefono> obtenerPorPersona(int personaId) throws SQLException {
        String sql = "SELECT id, personaId, telefono FROM Telefonos WHERE personaId=? ORDER BY id";
        List<Telefono> lista = new ArrayList<>();

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, personaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Telefono(
                            rs.getInt("id"),
                            rs.getInt("personaId"),
                            rs.getString("telefono")
                    ));
                }
            }
        }
        return lista;
    }

    public int insertar(int personaId, String telefono) throws SQLException {
        String sql = "INSERT INTO Telefonos(personaId, telefono) VALUES (?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, personaId);
            ps.setString(2, telefono);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        throw new SQLException("No se generó ID al insertar Teléfono.");
    }

    public void actualizar(int telefonoId, String telefonoNuevo) throws SQLException {
        String sql = "UPDATE Telefonos SET telefono=? WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, telefonoNuevo);
            ps.setInt(2, telefonoId);
            ps.executeUpdate();
        }
    }

    public void eliminar(int telefonoId) throws SQLException {
        String sql = "DELETE FROM Telefonos WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, telefonoId);
            ps.executeUpdate();
        }
    }

    public void reemplazarTelefonos(int personaId, List<String> telefonos) throws SQLException {
        try (Connection con = ConnectionFactory.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement del = con.prepareStatement("DELETE FROM Telefonos WHERE personaId=?")) {
                    del.setInt(1, personaId);
                    del.executeUpdate();
                }

                try (PreparedStatement ins = con.prepareStatement(
                        "INSERT INTO Telefonos(personaId, telefono) VALUES (?, ?)")) {
                    for (String t : telefonos) {
                        ins.setInt(1, personaId);
                        ins.setString(2, t);
                        ins.addBatch();
                    }
                    ins.executeBatch();
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

