package ru.minsafin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.minsafin.dao.BookDao;
import ru.minsafin.models.Book;

@Component
public class BookValidator implements Validator {
    private final BookDao bookDao;

    @Autowired
    public BookValidator(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if(bookDao.getByName(book.getTitle()).isPresent()){
            errors.rejectValue("name", "", "This book is already exit");
        }
    }
}
