package com.david.dao;

import com.david.modelo.Persona;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PersonaRepository {
    List<Persona> obtenerTodas(Connection con) throws SQLException;
    int insertar(Connection con, String nombre, String direccion) throws SQLException;
    void actualizar(Connection con, int id, String nombre, String direccion) throws SQLException;
    void eliminar(Connection con, int id) throws SQLException;
}