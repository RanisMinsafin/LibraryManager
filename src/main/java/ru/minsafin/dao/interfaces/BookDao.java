package ru.minsafin.dao.interfaces;

import ru.minsafin.models.Book;
import ru.minsafin.models.Person;

import java.util.List;
import java.util.Optional;

public interface BookDao extends Dao<Book> {
    Optional<Book> getByTitle(String title);

    void assignPerson(int id, int ownerId);

    void release(int id);

    Optional<Person> getBookOwner(int id);

    List<Person> getAllPersons();
}
