package ru.minsafin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minsafin.dao.interfaces.BookDao;
import ru.minsafin.models.Book;
import ru.minsafin.models.Person;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookDao bookDao;

    @Autowired
    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Book findById(int id) {
        return bookDao.findById(id).orElse(new Book());
    }

    public Optional<Book> findByTitle(String title) {
        return bookDao.findByTitle(title);
    }

    public List<Book> findAll() {
        return bookDao.findAll();
    }

    public List<Person> findAllReaders() {
        return bookDao.findAllReaders();
    }

    @Transactional
    public void save(Book book) {
        bookDao.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        bookDao.update(id, updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookDao.delete(id);
    }
    @Transactional
    public void assignReader(int bookId, int readerId) {
        bookDao.assignReader(bookId, readerId);
    }

    @Transactional
    public void free(int id) {
        bookDao.free(id);
    }
}
