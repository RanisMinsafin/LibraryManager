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
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Override
    @Transactional
    public void update(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book bookToBeUpdated = session.get(Book.class, id);
        bookToBeUpdated.setTitle(updatedBook.getTitle());
        bookToBeUpdated.setAuthor(updatedBook.getAuthor());
        bookToBeUpdated.setPublicationYear(updatedBook.getPublicationYear());
    }

    @Override
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person owner = book.getReader();
        if (owner != null) {
            owner.getBooks().remove(book);
        }
        session.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        return Optional.ofNullable(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();

        List<Book> result = session.createQuery("select b from Book b where b.title = :title", Book.class)
                .setParameter("title", title)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    @Transactional
    public void assignReader(int id, int readerId) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person owner = session.get(Person.class, readerId);
        owner.addBook(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAllReaders() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Person p", Person.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void free(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.getReader().getBooks().remove(book);
        book.setReader(null);
    }
}
