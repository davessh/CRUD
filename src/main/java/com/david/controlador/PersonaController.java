package com.david.controlador;

import com.david.dao.PersonaDao;
import com.david.dao.TelefonoDao;
import com.david.logica.DireccionLogica;
import com.david.servicio.PersonaService;
import com.david.servicio.TransactionManager;
import com.david.validacion.PersonaValidator;

public class PersonaController {

    private final PersonaDao personaDao = new PersonaDao();
    private final TelefonoDao telefonoDao = new TelefonoDao();
    private final DireccionLogica direccionService = new DireccionLogica();

    private final PersonaService personaService =
            new PersonaService(personaDao, telefonoDao, direccionService,
                    new PersonaValidator(), new TransactionManager());

    public PersonaService service() {
        return personaService;
    }

    public PersonaDao personaDao() { return personaDao; }
    public TelefonoDao telefonoDao() { return telefonoDao; }
    public DireccionLogica direccionService() { return direccionService; }
}