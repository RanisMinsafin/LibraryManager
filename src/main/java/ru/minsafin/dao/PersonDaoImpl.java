package ru.minsafin.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.minsafin.dao.interfaces.PersonDao;
import ru.minsafin.models.Book;
import ru.minsafin.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDaoImpl implements PersonDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Override
    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, id);
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setBirthYear(updatedPerson.getBirthYear());
    }

    @Override
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeDeleted = session.get(Person.class, id);

        List<Book> books = personToBeDeleted.getBooks();
        for(Book book : books){
            book.setReader(null);
        }
        session.delete(personToBeDeleted);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        return Optional.ofNullable(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();

        List<Person> result = session.createQuery("select p from Person p where p.name = :name", Person.class)
                .setParameter("name", name)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    @Transactional(readOnly = true)
        public List<Person> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p", Person.class)
                .getResultList();
    }
}
