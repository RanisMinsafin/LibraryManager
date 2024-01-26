package ru.minsafin.dao.interfaces;

import ru.minsafin.models.Book;
import ru.minsafin.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao extends Dao<Person> {
    Optional<Person> getByName(String name);

    List<Book> getBooksByPerson(int id);
}
