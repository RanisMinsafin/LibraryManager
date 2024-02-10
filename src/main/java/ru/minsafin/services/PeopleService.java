package ru.minsafin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minsafin.dao.interfaces.PersonDao;
import ru.minsafin.models.Person;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PersonDao personDao;

    @Autowired
    public PeopleService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> findAll(){
        return personDao.findAll();
    }

    public Person findById(int id) {
        return personDao.findById(id).orElse(new Person());
    }

    @Transactional
    public void save(Person person){
        personDao.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        personDao.update(id, updatedPerson);
    }

    @Transactional
    public void delete(int id){
        personDao.delete(id);
    }
}
