package module.Core.ChainOfResponsibility;

public abstract class MessageSender {
    protected PriorityLevel priorityLevel;
    private MessageSender nextMessageSender;

    public MessageSender(PriorityLevel p){
        this.priorityLevel = p;
        this.nextMessageSender = null;
    }

    public void setNextSender(MessageSender s){
        this.nextMessageSender = s;
    }

    public void send(String m, PriorityLevel p){
        if (p.ordinal() >= priorityLevel.ordinal()){
            write(m);
        }

        if (nextMessageSender != null){
            nextMessageSender.send(m, p);
        }
    }

    public abstract void write(String message);

    public enum PriorityLevel {
        LOW, MIDDLE, HIGH
    }
}
