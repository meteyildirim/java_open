
import com.mete.roadmap.order.AtomicCounter;
import com.mete.roadmap.order.Counter;
import com.mete.roadmap.order.SynchronizedCounter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CounterTest {

    private static final int THREAD_COUNT = 10;
    private static final int INCREMENTS_PER_THREAD = 10_000;
    private static final int EXPECTED_TOTAL = THREAD_COUNT * INCREMENTS_PER_THREAD; // 100,000

    @Test
    void shouldDemonstrateRaceConditionWithUnsafeCounter() throws InterruptedException {
        Counter counter = new Counter();

        runConcurrently(() -> {
            for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
                counter.increment();
            }
        });

        System.out.println("Unsafe Counter Result: " + counter.getValue() + " (Expected: " + EXPECTED_TOTAL + ")");

        // Due to race conditions, lost updates cause the final count to be less than EXPECTED_TOTAL
        assertNotEquals(EXPECTED_TOTAL, counter.getValue());
    }

    @Test
    void shouldBeThreadSafeWithSynchronizedCounter() throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();

        runConcurrently(() -> {
            for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
                counter.increment();
            }
        });

        System.out.println("Synchronized Counter Result: " + counter.getValue());
        assertEquals(EXPECTED_TOTAL, counter.getValue());
    }

    @Test
    void shouldBeThreadSafeWithAtomicCounter() throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();

        runConcurrently(() -> {
            for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
                counter.increment();
            }
        });

        System.out.println("Atomic Counter Result: " + counter.getValue());
        assertEquals(EXPECTED_TOTAL, counter.getValue());
    }

    // Helper method to coordinate multi-threaded execution
    private void runConcurrently(Runnable task) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // Wait for all threads to finish execution
        executor.shutdown();
    }
}