package com.david.servicio;

import com.david.db.ConnectionProvider;
import java.sql.Connection;

public class TransactionManager {

    @FunctionalInterface
    public interface TxWork<T> {
        T execute(Connection con) throws Exception;
    }

    private final ConnectionProvider provider;

    public TransactionManager(ConnectionProvider provider) {
        this.provider = provider;
    }

    public <T> T inTransaction(TxWork<T> work) throws Exception {
        try (Connection con = provider.getConnection()) {
            con.setAutoCommit(false);
            try {
                T result = work.execute(con);
                con.commit();
                return result;
            } catch (Exception e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}