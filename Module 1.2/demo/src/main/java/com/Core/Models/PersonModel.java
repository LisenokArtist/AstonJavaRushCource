package com.Core.Models;

import java.util.Arrays;

public class PersonModel {
    public String FurstName;
    public String SecondName;
    public String LastName;

    public PersonModel(String furstName, String lastName){
        this(furstName, "", lastName);
    }

    public PersonModel(String furstName, String secondName, String lastName){
        this.FurstName = furstName;
        this.SecondName = secondName;
        this.LastName = lastName;
    }

    @Override
    public String toString() {
        String[] values = { this.FurstName, this.SecondName, this.LastName };
        values = Arrays.stream(values)
        .filter((x -> !x.isEmpty()))
        .toArray(String[]::new);

        return String.join(" ", values);
    }
}
