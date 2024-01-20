package ru.minsafin.dao;

import ru.minsafin.models.Person;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void create(T entity);

    void update(T entity);

    void delete(int id);

    Optional<T> getById(int id);

    List<T> getAll();

    Optional<T> getByName(String name);

}
