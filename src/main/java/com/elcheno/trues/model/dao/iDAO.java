package com.elcheno.trues.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface iDAO<T> extends AutoCloseable {
    List<T> findAll() throws SQLException;
    T findById(int id) throws SQLException;
    boolean save(T entity) throws SQLException;
    boolean delete(T entity) throws SQLException;
}
