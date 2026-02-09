package com.david.dao;

import com.david.db.ConnectionFactory;
import com.david.modelo.Direccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDireccionDao {

    public void asociar(int personaId, long direccionId) throws SQLException {
        String sql = "INSERT IGNORE INTO persona_direccion (persona_id, direccion_id) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);
            ps.setLong(2, direccionId);
            ps.executeUpdate();
        }
    }

    public void desasociar(int personaId, long direccionId) throws SQLException {
        String sql = "DELETE FROM persona_direccion WHERE persona_id = ? AND direccion_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);
            ps.setLong(2, direccionId);
            ps.executeUpdate();
        }
    }

    public List<Direccion> obtenerDireccionesDePersona(int personaId) throws SQLException {
        String sql = """
                SELECT d.id, d.descripcion
                FROM direccion d
                INNER JOIN persona_direccion pd ON pd.direccion_id = d.id
                WHERE pd.persona_id = ?
                ORDER BY d.descripcion
                """;

        List<Direccion> direcciones = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    direcciones.add(
                            new Direccion(
                                    rs.getLong("id"),
                                    rs.getString("descripcion")
                            )
                    );
                }
            }
        }
        return direcciones;
    }
}