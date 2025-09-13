package module.Models;

import java.util.Arrays;

public class PersonModel {
    private String _furstName;
    private String _secondName;
    private String _lastName;

    public PersonModel() {}
    
    public PersonModel(String furstName, String lastName){
        this(furstName, "", lastName);
    }

    public PersonModel(String furstName, String secondName, String lastName){
        this._furstName = furstName;
        this._secondName = secondName;
        this._lastName = lastName;
    }

    public String getFurstName(){
        return this._furstName;
    }

    public String getSecondName(){
        return this._secondName;
    }

    public String getLastName(){
        return this._lastName;
    }

    @Override
    public String toString() {
        String[] values = { this._furstName, this._secondName, this._lastName };
        values = Arrays.stream(values)
            .filter((x -> !x.isEmpty()))
            .toArray(String[]::new);

        return String.join(" ", values);
    }
}

