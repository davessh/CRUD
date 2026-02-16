package com.david.servicio;

import com.david.db.ConnectionFactory;

import java.sql.Connection;

public class TransactionManager {

    @FunctionalInterface
    public interface TxWork<T> {
        T execute(Connection con) throws Exception;
    }

    public <T> T inTransaction(TxWork<T> work) throws Exception {
        try (Connection con = ConnectionFactory.getConnection()) {
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