package module.Core.Decorator.Service;

public class ServiceBase implements Service {
    protected String label;
    protected double price;

    public ServiceBase(String label, double price){
        this.label = label;
        this.price = price;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public double getPrice() {
        return this.price;
    }
}
