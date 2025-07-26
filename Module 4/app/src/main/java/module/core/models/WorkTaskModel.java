package module.core.models;

public class WorkTaskModel {
    private String message;
    
    private int count = 0;

    public WorkTaskModel(String message) { this.message = message; }

    public String getMessage() { return this.message; }

    public int getCount() { return this.count;}

    public void increaseCount() { this.count ++;}
}