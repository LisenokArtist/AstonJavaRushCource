package module.Core.ChainOfResponsibility;

public class SMSMessageSender extends MessageSender {
    public SMSMessageSender(PriorityLevel p) {
        super(p);
    }

    @Override
    public void write(String message) {
        System.out.format("\n[SMS] %s", message);
    }
}
