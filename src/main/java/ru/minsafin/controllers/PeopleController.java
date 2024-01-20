package ru.minsafin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.minsafin.dao.PersonDao;
import ru.minsafin.models.Book;
import ru.minsafin.models.Person;
import ru.minsafin.util.PersonValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDao personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", personDao.getAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String createPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDao.create(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.getById(id).orElse(new Person()));
        model.addAttribute("books", personDao.getBooksByPerson(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.getById(id).orElse(new Person()));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute @Valid Person person, @PathVariable("id") int id,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        person.setId(id);
        personDao.update(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }
}
