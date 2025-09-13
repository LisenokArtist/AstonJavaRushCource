package module.core.threads;

import java.util.Random;

public class LivelockThread extends ThreadBase {
    protected boolean running;

    public LivelockThread(String name, boolean isRunning) {
        super(name);
        this.running = isRunning;
    }
    
    public boolean isRunning(){ return this.running; }

    public synchronized void work(LivelockThread thread){
        Random randomizer = new Random();

        while (this.running){
            if (thread.isRunning()){
                System.out.format("\n%s: %s is running, %s await.", this.name, thread.getName(), this.name);
                try{
                    Thread.sleep(randomizer.nextInt(200, 3000));
                } catch (InterruptedException e){
                    System.err.format("\n%s throw exception: $s", this.name, e.getMessage());
                    Thread.currentThread().interrupt();
                }
                continue;
            }

            System.out.format("\n%s: can be running for now.", this.name);
            this.running = false;
        }
    }
}
