package module.core.threads;

public class DeadlockThread extends ThreadBase {
    private boolean lock;

    public DeadlockThread(String name) {
        super(name);
        this.lock = false;
    }

    public boolean isLocked(){ return this.lock; }

    public synchronized void lock(DeadlockThread thread){
        this.lock = true;
        System.out.format("\n%s: %s has locked", this.name, thread.getName());
        thread.lockBack(this);
    }

    public synchronized void lockBack(DeadlockThread thread){
        this.lock = false;
        System.out.format("\n%s: %s has unlocked", this.name, thread.getName());
    }
}
