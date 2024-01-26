package ru.minsafin.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.minsafin.dao.interfaces.BookDao;
import ru.minsafin.models.Book;
import ru.minsafin.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Override
    @Transactional
    public void update(Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book bookToBeUpdated = session.get(Book.class, updatedBook.getId());
        bookToBeUpdated.setTitle(updatedBook.getTitle());
        bookToBeUpdated.setAuthor(updatedBook.getAuthor());
        bookToBeUpdated.setPublicationYear(updatedBook.getPublicationYear());
    }

    @Override
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person owner = book.getOwner();
        if (owner != null) {
            owner.getBooks().remove(book);
        }
        session.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        return Optional.ofNullable(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();

        List<Book> result = session.createQuery("select b from Book b where b.title = :title", Book.class)
                .setParameter("title", title)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getAllPersons() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Person p", Person.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> getBookOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Book.class, id).getOwner());
    }

    @Override
    @Transactional
    public void assignPerson(int id, int ownerId) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person owner = session.get(Person.class, ownerId);
        owner.addBook(book);
    }

    @Override
    @Transactional
    public void release(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.getOwner().getBooks().remove(book);
        book.setOwner(null);
    }
}
