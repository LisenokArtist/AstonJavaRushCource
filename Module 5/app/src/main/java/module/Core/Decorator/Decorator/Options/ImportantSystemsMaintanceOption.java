package module.Core.Decorator.Decorator.Options;

import module.Core.Decorator.Decorator.OptionDecorator;
import module.Core.Decorator.Service.Service;

public class ImportantSystemsMaintanceOption extends OptionDecorator {
    public ImportantSystemsMaintanceOption(Service serivce) {
        super(serivce, "Important systems maintence\n", 1200);
    }
}
