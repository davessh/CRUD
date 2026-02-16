package com.david.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ContactoDao<T> extends PorPersonaDao<T>, ReemplazablePorPersonaDao{
}