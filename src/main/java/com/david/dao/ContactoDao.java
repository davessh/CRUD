package com.david.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ContactoDao<T> {
    List<T> obtenerPorPersona(Connection con, int personaId) throws SQLException;
    void reemplazar(Connection con, int personaId, List<String> valores) throws SQLException;
}