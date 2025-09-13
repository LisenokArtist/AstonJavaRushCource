package com.Core.Models;

public class BookModel {
    public String Name;
    public PersonModel[] Authors;
    public int Pages;
    public int Year;

    public BookModel(String name, PersonModel[] authors, int pages, int year){
        this.Name = name;
        this.Authors = authors;
        this.Pages = pages;
        this.Year = year;
    }

    public int getYear(){
        return this.Year;
    }

    public int getPages(){
        return this.Pages;
    }
}
