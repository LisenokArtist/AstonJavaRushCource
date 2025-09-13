package module;

import org.junit.jupiter.api.Test;

import module.core.threads.DeadlockThread;
import module.core.threads.LivelockThread;
import module.core.threads.PairedThread;
import module.core.models.WorkTaskModel;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void deadlockTest(){
        assertTimeoutPreemptively(java.time.Duration.ofSeconds(5), () -> {
            final DeadlockThread threadA = new DeadlockThread("Thread A");
            final DeadlockThread threadB = new DeadlockThread("Thread B");

            Thread runA = new Thread(new Runnable() {
                @Override
                public void run(){
                    threadA.lock(threadB);
                }
            });

            Thread runB = new Thread(new Runnable() {
                @Override
                public void run(){
                    threadB.lock(threadA);
                }
            });

            runA.start();
            runB.start();

            runA.join(); // Выдаст ошибку после 5 секунд работы, оба потока будут заблокированы
            runB.join();
        });
    }

    @Test void livelockTest(){
        assertTimeout(java.time.Duration.ofSeconds(1), () -> {
            final LivelockThread threadA = new LivelockThread("Thread A", true);
            final LivelockThread threadB = new LivelockThread("Thread B", true);

            Thread runA = new Thread(() -> threadA.work(threadB));
            Thread runB = new Thread(() -> threadB.work(threadA));

            runA.start();
            runB.start();

            runA.join(); // Оба потока не взаимоблокируются и продолжают работать
            runB.join();
        });
    }

    @Test void queuePrintlnTest(){
        assertTimeoutPreemptively(java.time.Duration.ofSeconds(10), () -> {
            final WorkTaskModel work = new WorkTaskModel("Most important task name");
            final PairedThread threadA = new PairedThread("1", true, work);
            final PairedThread threadB = new PairedThread("2", true);

            Thread runA = new Thread(() -> threadA.work(threadB));
            Thread runB = new Thread(() -> threadB.work(threadA));

            runA.start();
            runB.start();

            runA.join();
            runB.join();
        });
    }
}
