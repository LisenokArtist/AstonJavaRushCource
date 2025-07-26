package module.Core.Strategy;

public class PaperPlane extends Plane {
    public PaperPlane(String name) {
        super(name);
    }

    @Override
    public void fill(){
        System.out.println("Бумажный самолет не нуждается в заправке");
    }
}
