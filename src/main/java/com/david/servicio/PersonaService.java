package com.david.servicio;

import com.david.dao.PersonaDao;
import com.david.dao.TelefonoDao;
import com.david.logica.DireccionLogica;
import com.david.validacion.PersonaValidator;

import java.sql.Connection;
import java.util.List;

public class PersonaService {

    private final PersonaDao personaDao;
    private final TelefonoDao telefonoDao;
    private final DireccionLogica direccionService;
    private final PersonaValidator validator;
    private final TransactionManager tx;

    public PersonaService(PersonaDao personaDao,
                          TelefonoDao telefonoDao,
                          DireccionLogica direccionService,
                          PersonaValidator validator,
                          TransactionManager tx) {
        this.personaDao = personaDao;
        this.telefonoDao = telefonoDao;
        this.direccionService = direccionService;
        this.validator = validator;
        this.tx = tx;
    }

    public int crear(String nombre, String direccion, List<String> telefonos) throws Exception {
        var errores = validator.validar(nombre, direccion, telefonos);
        if (!errores.isEmpty()) throw new IllegalArgumentException(String.join("\n", errores));

        return tx.inTransaction(con -> {
            int personaId = personaDao.insertar(con, nombre.trim(), direccion);

            if (telefonos != null && !telefonos.isEmpty()) {
                telefonoDao.reemplazarTelefonos(con, personaId, telefonos);
            }

            if (direccion != null && !direccion.trim().isEmpty()) {
                direccionService.agregarDireccionAPersona(personaId, direccion.trim());
            }

            return personaId;
        });
    }

    public void actualizar(int personaId, String nombre, String direccion, List<String> telefonos) throws Exception {
        var errores = validator.validar(nombre, direccion, telefonos);
        if (!errores.isEmpty()) throw new IllegalArgumentException(String.join("\n", errores));

        tx.inTransaction(con -> {
            personaDao.actualizar(con, personaId, nombre.trim(), direccion);

            telefonoDao.reemplazarTelefonos(con, personaId, telefonos);

            if (direccion != null && !direccion.trim().isEmpty()) {
                direccionService.agregarDireccionAPersona(personaId, direccion.trim());
            }

            return null;
        });
    }

    public void eliminar(int personaId) throws Exception {
        tx.inTransaction(con -> {
            personaDao.eliminar(con, personaId);
            return null;
        });
    }
}