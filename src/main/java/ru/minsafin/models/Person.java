package ru.minsafin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Data
@ToString(exclude = "books")
@AllArgsConstructor
@Entity
@Table(name = "person", schema = "library")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Column(name = "birth_year")
    @Min(value = 1900, message = "Birth year should be equal or greater than 1900")
    @Max(value = 2024, message = "Birth year should be equal or less than 2024")
    private int birthYear;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Cascade(SAVE_UPDATE)
    private List<Book> books;

    public Person() {
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>(Collections.singletonList(book));
        } else {
            books.add(book);
        }
        book.setOwner(this);
    }
}
