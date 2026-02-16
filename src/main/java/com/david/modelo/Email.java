package com.david.modelo;

public class Email implements Contacto {

    private int id;
    private int personaId;
    private String email;

    public Email(int id, int personaId, String email) {
        this.id = id;
        this.personaId = personaId;
        this.email = email;
    }

    public int getId() { return id; }
    public int getPersonaId() { return personaId; }
    public String getEmail() { return email; }

    @Override
    public String getEtiqueta() { return "Email"; }

    @Override
    public String getValor() { return email; }

    @Override
    public String toString() { return formato(); }
}