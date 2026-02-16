package com.david.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ReemplazablePorPersonaDao {
    void reemplazar(Connection con, int personaId, List<String> valores) throws SQLException;
}