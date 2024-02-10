package ru.minsafin.dao.interfaces;

import ru.minsafin.models.Book;
import ru.minsafin.models.Person;

import java.util.List;
import java.util.Optional;

public interface BookDao extends Dao<Book> {
    Optional<Book> findByTitle(String title);

    void assignReader(int id, int readerId);

    void free(int id);

    List<Person> findAllReaders();
}
