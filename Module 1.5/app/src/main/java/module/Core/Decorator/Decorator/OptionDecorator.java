package module.Core.Decorator.Decorator;

import module.Core.Decorator.Service.Service;
import module.Core.Decorator.Service.ServiceBase;

public class OptionDecorator extends ServiceBase {
    private Service service;

    public OptionDecorator(Service serivce, String label, double price) {
        super(label, price);
        this.service = serivce;
    }

    @Override
    public String getLabel(){
        return this.label + service.getLabel();
    }

    @Override
    public double getPrice(){
        return this.price + service.getPrice();
    }
}
