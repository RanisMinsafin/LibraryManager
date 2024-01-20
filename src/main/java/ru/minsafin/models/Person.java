package ru.minsafin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@ToString
@AllArgsConstructor
public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    @Min(value = 1900, message = "Birth year should be equal or greater than 1900")
    @Max(value = 2024, message = "Birth year should be equal or less than 2024")
    private int birthYear;

    public Person() {
    }
}
