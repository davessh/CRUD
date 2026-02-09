package com.david.logica;

import com.david.dao.DireccionDao;
import com.david.dao.PersonaDireccionDao;
import com.david.modelo.Direccion;

import java.sql.SQLException;
import java.util.List;

public class DireccionLogica {

    private final DireccionDao direccionDao;
    private final PersonaDireccionDao personaDireccionDao;

    public DireccionLogica() {
        this.direccionDao = new DireccionDao();
        this.personaDireccionDao = new PersonaDireccionDao();
    }

    public void agregarDireccionAPersona(int personaId, String descripcion) throws SQLException {
        long direccionId = direccionDao.encontrarOCrear(descripcion);
        personaDireccionDao.asociar(personaId, direccionId);
    }

    public void quitarDireccionDePersona(int personaId, long direccionId) throws SQLException {
        personaDireccionDao.desasociar(personaId, direccionId);
    }

    public List<Direccion> obtenerDireccionesDePersona(int personaId) throws SQLException {
        return personaDireccionDao.obtenerDireccionesDePersona(personaId);
    }

    public List<Direccion> listarDirecciones() throws SQLException {
        return direccionDao.listarTodas();
    }
}
