package com.david.dao;

import com.david.db.ConnectionFactory;
import com.david.modelo.Direccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DireccionDao {

    public long insertar(Direccion d) throws SQLException {
        String sql = "INSERT INTO direccion (descripcion) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, d.getDescripcion());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("No se pudo obtener el ID generado para direccion");
    }

    public Direccion buscarPorId(long id) throws SQLException {
        String sql = "SELECT id, descripcion FROM direccion WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Direccion(rs.getLong("id"), rs.getString("descripcion"));
                }
            }
        }
        return null;
    }

    public Direccion buscarPorDescripcion(String descripcion) throws SQLException {
        String sql = "SELECT id, descripcion FROM direccion WHERE descripcion = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, descripcion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Direccion(rs.getLong("id"), rs.getString("descripcion"));
                }
            }
        }
        return null;
    }

    public List<Direccion> listarTodas() throws SQLException {
        String sql = "SELECT id, descripcion FROM direccion ORDER BY descripcion";
        List<Direccion> direcciones = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                direcciones.add(new Direccion(rs.getLong("id"), rs.getString("descripcion")));
            }
        }
        return direcciones;
    }

    public long encontrarOCrear(String descripcion) throws SQLException {
        Direccion existente = buscarPorDescripcion(descripcion);
        if (existente != null) return existente.getId();

        try {
            return insertar(new Direccion(descripcion));
        } catch (SQLIntegrityConstraintViolationException e) {

            Direccion d2 = buscarPorDescripcion(descripcion);
            if (d2 != null) return d2.getId();
            throw e;
        }
    }
}
