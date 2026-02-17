package com.david.dao;

import com.david.db.ConnectionFactory;
import com.david.modelo.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDao implements PersonaRepository {

    // ---------- WRAPPER (opcional) ----------
    // Útil para llamadas simples (pero en Services transaccionales usa el overload con Connection)
    public List<Persona> obtenerTodas() throws SQLException {
        try (Connection con = ConnectionFactory.getConnection()) {
            return obtenerTodas(con);
        }
    }

    // ✅ ESTE es el que exige PersonaRepository
    @Override
    public List<Persona> obtenerTodas(Connection con) throws SQLException {
        String sql = "SELECT id, nombre, direccion FROM Personas ORDER BY id";
        List<Persona> lista = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql);
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

    // ---------- WRAPPER (opcional) ----------
    public int insertar(String nombre, String direccion) throws SQLException {
        try (Connection con = ConnectionFactory.getConnection()) {
            return insertar(con, nombre, direccion);
        }
    }

    @Override
    public int insertar(Connection con, String nombre, String direccion) throws SQLException {
        String sql = "INSERT INTO Personas(nombre, direccion) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        throw new SQLException("No se generó ID al insertar Persona.");
    }

    // ---------- WRAPPER (opcional) ----------
    public void actualizar(int id, String nombre, String direccion) throws SQLException {
        try (Connection con = ConnectionFactory.getConnection()) {
            actualizar(con, id, nombre, direccion);
        }
    }

    @Override
    public void actualizar(Connection con, int id, String nombre, String direccion) throws SQLException {
        String sql = "UPDATE Personas SET nombre=?, direccion=? WHERE id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    // ---------- WRAPPER (opcional) ----------
    public void eliminar(int id) throws SQLException {
        try (Connection con = ConnectionFactory.getConnection()) {
            eliminar(con, id);
        }
    }

    @Override
    public void eliminar(Connection con, int id) throws SQLException {
        String sql = "DELETE FROM Personas WHERE id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}