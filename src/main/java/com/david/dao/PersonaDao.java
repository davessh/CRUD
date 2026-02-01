package com.david.dao;

import com.david.db.ConnectionFactory;
import com.david.modelo.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDao {

    public List<Persona> obtenerTodas() throws SQLException {
        String sql = "SELECT id, nombre, direccion FROM Personas ORDER BY id";
        List<Persona> lista = new ArrayList<>();

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Persona(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion")
                ));
            }
        }
        return lista;
    }

    public int insertar(String nombre, String direccion) throws SQLException {
        String sql = "INSERT INTO Personas(nombre, direccion) VALUES (?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        throw new SQLException("No se generó ID al insertar Persona.");
    }

    public void actualizar(int id, String nombre, String direccion) throws SQLException {
        String sql = "UPDATE Personas SET nombre=?, direccion=? WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Personas WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}


