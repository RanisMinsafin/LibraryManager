package ru.minsafin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Data
@ToString(exclude = "person")
@AllArgsConstructor
@Entity
@Table(name = "book", schema = "library")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Book's name should not be empty")
    @Size(min = 2, max = 50, message = "Book's name should be between 2 and 50 characters")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Author's name should not be empty")
    @Size(min = 2, max = 30, message = "Author's name should be between 2 and 30 characters")
    private String author;

    @Column(name = "publication_year")
    @Min(value = 1900, message = "Publication year should not be less than 1900")
    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @Cascade(SAVE_UPDATE)
    private Person owner;

    public Book() {
    }
}
