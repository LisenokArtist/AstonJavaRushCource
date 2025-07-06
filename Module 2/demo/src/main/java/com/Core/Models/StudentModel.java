package com.Core.Models;

import java.util.List;

public class StudentModel extends PersonModel {

    public List<BookModel> Books;

    public StudentModel(String furstName, String lastName){
        super(furstName, "", lastName);
    }
}
