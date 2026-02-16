package com.david.servicio;

import com.david.dao.ContactoDao;

import java.sql.Connection;
import java.util.List;

public class ContactoService {

    private final List<ContactoDao<?>> repos;

    public ContactoService(List<ContactoDao<?>> repos) {
        this.repos = repos;
    }
    public void guardarTelefonos(Connection con, int personaId, ContactoDao<?> dao, List<String> valores) throws Exception {
        dao.reemplazar(con, personaId, valores);
    }
}