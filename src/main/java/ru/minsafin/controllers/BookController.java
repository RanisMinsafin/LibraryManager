package ru.minsafin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.minsafin.dao.interfaces.BookDao;
import ru.minsafin.models.Book;
import ru.minsafin.models.Person;
import ru.minsafin.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("books")
public class BookController {
    private final BookDao bookDao;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDao bookDao, BookValidator bookValidator) {
        this.bookDao = bookDao;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String getBooks(Model model) {
        model.addAttribute("books", bookDao.getAll());
        return "books/index";
    }

    @GetMapping("/new")
    public String createBook(@ModelAttribute Book book) {
        return "books/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDao.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.getById(id).orElse(new Book()));

        Optional<Person> person = bookDao.getBookOwner(id);
        if (person.isPresent()) {
            model.addAttribute("owner", person.get());
        } else {
            model.addAttribute("people", bookDao.getAllPersons());
        }
        return "books/show";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDao.getById(id).orElse(new Book()));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute @Valid Book book, @PathVariable("id") int id,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        book.setId(id);
        bookDao.update(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("ownerId") int ownerId) {
        bookDao.assignPerson(id, ownerId);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDao.release(id);
        return "redirect:/books";
    }
}
