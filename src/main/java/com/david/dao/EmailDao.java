package com.david.dao;

import com.david.db.ConnectionFactory;
import com.david.modelo.Email;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDao implements ContactoDao<Email> {

    public EmailDao() { }
    public List<Email> obtenerPorPersona(int personaId) throws SQLException {
        try (Connection con = ConnectionFactory.getConnection()) {
            return obtenerPorPersona(con, personaId);
        }
    }

    @Override
    public List<Email> obtenerPorPersona(Connection con, int personaId) throws SQLException {

        String sql = "SELECT id, personaId, email FROM Emails WHERE personaId=? ORDER BY id";
        List<Email> lista = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, personaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Email(
                            rs.getInt("id"),
                            rs.getInt("personaId"),
                            rs.getString("email")
                    ));
                }
            }
        }
        return lista;
    }

    @Override
    public void reemplazar(Connection con, int personaId, List<String> emails) throws SQLException {
        try (PreparedStatement del = con.prepareStatement(
                "DELETE FROM Emails WHERE personaId=?")) {
            del.setInt(1, personaId);
            del.executeUpdate();
        }

        if (emails == null || emails.isEmpty()) return;

        try (PreparedStatement ins = con.prepareStatement(
                "INSERT INTO Emails(personaId, email) VALUES (?, ?)")) {

            for (String e : emails) {
                if (e == null || e.trim().isEmpty()) continue;
                ins.setInt(1, personaId);
                ins.setString(2, e.trim());
                ins.addBatch();
            }

            ins.executeBatch();
        }
    }
}