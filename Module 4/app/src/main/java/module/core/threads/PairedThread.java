package module.core.threads;

import module.core.models.WorkTaskModel;

public class PairedThread extends LivelockThread {
    private WorkTaskModel work;
    
    public PairedThread(String name, boolean isRunning, WorkTaskModel work) {
        super(name, isRunning);
        this.work = work;
    }

    public PairedThread(String name, boolean isRunning) {
        super(name, isRunning);
        work = null;
    }

    public synchronized void work(PairedThread pair) {
        while(this.running){
            if (work != null){
                //System.out.format("\n[%s]%s: A named work task has been resended to %s",this.work.getCount(), this.name, pair.name);
                System.out.format("\n[%s] %s", work.getCount(), this.name);
                this.work.increaseCount();
                pair.work = work;
                this.work = null;
            }
            else{
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e){
                    System.err.format("\n%s throw exception: $s", this.name, e.getMessage());
                    Thread.currentThread().interrupt();
                }
                continue;
            }
        }
    }
}