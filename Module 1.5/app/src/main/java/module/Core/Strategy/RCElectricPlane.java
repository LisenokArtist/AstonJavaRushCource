package module.Core.Strategy;

public class RCElectricPlane extends Plane{
    public RCElectricPlane(String name) {
        super(name);
    }

    @Override
    public void fill(){
        System.out.println("Электросамолет заряжен!");
    }
}
