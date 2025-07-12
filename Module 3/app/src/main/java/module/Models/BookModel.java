package module.Models;

import java.util.Arrays;

public class BookModel {
    private String name;
    private PersonModel[] authors;
    private int pages;
    private int year;

    public BookModel() {}
    
    public BookModel(String name, PersonModel[] authors, int pages, int year){
        this.name = name;
        this.authors = authors;
        this.pages = pages;
        this.year = year;
    }

    public int getYear(){
        return this.year;
    }

    public int getPages(){
        return this.pages;
    }

    public String getName(){
        return this.name;
    }

    public PersonModel[] getAuthors(){
        return this.authors;
    }

    public String toString() {
        return String.format(
            "%s (%s)", 
            this.name, 
            String.join(
                ", ", 
                Arrays.stream(this.authors)
                    .map(x -> x.toString())
                    .toArray(String[]::new)));
    }
}