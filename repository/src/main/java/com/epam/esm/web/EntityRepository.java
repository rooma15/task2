package com.epam.esm.web;

import com.epam.esm.model.Certificate;

import java.util.List;
import java.util.Map;

public interface EntityRepository<T> {
    List<T> retrieveAll();
    T retrieveOne(int id);
    List<T> retrieveByQuery(String sql, Class<T> elemType, Object...params);
    int create(T Entity);
    int delete(int id);
}
