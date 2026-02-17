package com.david.servicio;

import com.david.dao.PersonaRepository;
import com.david.dao.ContactoDao;
import com.david.validacion.PersonaValidator;
import com.david.logica.DireccionLogica;

import java.util.List;

public class PersonaService {

    private final PersonaRepository personaRepo;
    private final ContactoDao<?> telefonoRepo;
    private final DireccionLogica direccionService;
    private final PersonaValidator validator;
    private final TransactionManager tx;

    public PersonaService(PersonaRepository personaRepo,
                          ContactoDao<?> telefonoRepo,
                          DireccionLogica direccionService,
                          PersonaValidator validator,
                          TransactionManager tx) {
        this.personaRepo = personaRepo;
        this.telefonoRepo = telefonoRepo;
        this.direccionService = direccionService;
        this.validator = validator;
        this.tx = tx;
    }

    public int crear(String nombre, String direccion, List<String> telefonos) throws Exception {
        var errores = validator.validar(nombre, direccion, telefonos);
        if (!errores.isEmpty()) throw new IllegalArgumentException(String.join("\n", errores));

        return tx.inTransaction(con -> {
            int personaId = personaRepo.insertar(con, nombre.trim(), direccion);
            telefonoRepo.reemplazar(con, personaId, telefonos);

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
            personaRepo.actualizar(con, personaId, nombre.trim(), direccion);
            telefonoRepo.reemplazar(con, personaId, telefonos);

            if (direccion != null && !direccion.trim().isEmpty()) {
                direccionService.agregarDireccionAPersona(personaId, direccion.trim());
            }

            return null;
        });
    }

    public void eliminar(int personaId) throws Exception {
        tx.inTransaction(con -> {
            personaRepo.eliminar(con, personaId);
            return null;
        });
    }
}