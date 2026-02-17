package com.david.controlador;

import com.david.dao.*;
import com.david.db.*;
import com.david.logica.DireccionLogica;
import com.david.servicio.*;
import com.david.validacion.PersonaValidator;

public class PersonaController {

    private final PersonaService personaService;
    private final PersonaRepository personaRepo;
    private final TelefonoDao telefonoDao;
    private final DireccionLogica direccionService;

    public PersonaController() {
        ConnectionProvider provider = new MariaDbConnectionProvider();
        TransactionManager tx = new TransactionManager(provider);

        this.personaRepo = new PersonaDao();   // implementación concreta de la abstracción
        this.telefonoDao = new TelefonoDao();  // implementación concreta de ContactoDao
        this.direccionService = new DireccionLogica();

        this.personaService = new PersonaService(
                personaRepo,
                telefonoDao,
                direccionService,
                new PersonaValidator(),
                tx
        );
    }

    public PersonaService service() { return personaService; }
    public PersonaRepository personaRepo() { return personaRepo; }
    public TelefonoDao telefonoDao() { return telefonoDao; }
    public DireccionLogica direccionService() { return direccionService; }
}