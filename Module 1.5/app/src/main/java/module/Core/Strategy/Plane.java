package module.Core.Strategy;

public class Plane {
    private String name;

    public Plane(String name){
        this.name = name;
    }

    public void fill() {
        System.out.println("Самолет заправлен!");
    }

    public void fly(){
        System.out.println("Летим");
    }

    public String getName(){
        return this.name;
    }
}
