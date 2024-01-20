package ru.minsafin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@ToString
@AllArgsConstructor
public class Book {
    private int id;
    @NotEmpty(message = "Book's name should not be empty")
    @Size(min = 2, max = 50, message = "Book's name should be between 2 and 50 characters")
    private String title;
    @NotEmpty(message = "Author's name should not be empty")
    @Size(min = 2, max = 30, message = "Author's name should be between 2 and 30 characters")
    private String author;
    @Min(value = 1900, message = "Publication year should not be less than 1900")
    private int publicationYear;

    public Book() {
    }
}
