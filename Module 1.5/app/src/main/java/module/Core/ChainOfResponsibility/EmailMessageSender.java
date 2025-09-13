package module.Core.ChainOfResponsibility;

public class EmailMessageSender extends MessageSender {
    public EmailMessageSender(PriorityLevel p) {
		super(p);
	}

    @Override
    public void write(String message) {
        System.out.format("\n[EMAIL] %s", message);
    }

}
