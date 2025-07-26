package module.core.threads;

public abstract class ThreadBase {
    protected final String name;

    public ThreadBase(String name){
        this.name = name;
    }

    public String getName(){ return this.name; }
}
