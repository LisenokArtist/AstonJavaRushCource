package module.Core.ChainOfResponsibility;

public class LogReportMessageSender extends MessageSender {
    public LogReportMessageSender(PriorityLevel p) {
        super(p);
    }

    @Override
    public void write(String message) {
        System.out.format("\n[LOG] %s", message);
    }
}
