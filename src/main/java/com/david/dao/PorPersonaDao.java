package com.david.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PorPersonaDao<T> {
    List<T> obtenerPorPersona(Connection con, int personaId) throws SQLException;
}