package ru.minsafin.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void save(T entity);

    void update(int id, T entity);

    void delete(int id);

    Optional<T> findById(int id);

    List<T> findAll();
}
