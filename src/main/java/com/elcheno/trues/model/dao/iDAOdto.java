package com.elcheno.trues.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface iDAOdto<T, E> extends AutoCloseable {
    public List<T> findAll(E entity) throws SQLException;
    public boolean save(T entity, E entity2) throws SQLException;
}
