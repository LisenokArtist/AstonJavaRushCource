package module.Core.Decorator.Decorator.Options;

import module.Core.Decorator.Decorator.OptionDecorator;
import module.Core.Decorator.Service.Service;

public class HullMaintanceOption extends OptionDecorator {
    public HullMaintanceOption(Service serivce) {
        super(serivce, "Hull repairing\n", 500);
    }
}
