package pethorses.load;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import pethorses.PetHorses;
import pethorses.services.HorseService;
import pethorses.services.HorseBackpackService;
import pethorses.storage.HorseData;
import pethorses.storage.HorseDataManager;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

public class StressTests {

    private PetHorses plugin;
    private HorseService horseService;
    private HorseBackpackService backpackService;
    private HorseDataManager dataManager;

    private int getIntProp(String name, int defaultValue) {
        try {
            return Integer.parseInt(System.getProperty(name, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @BeforeEach
    public void setUp() {
        try { MockBukkit.unmock(); } catch (IllegalStateException ignored) {}
        MockBukkit.mock();
        plugin = MockBukkit.load(PetHorses.class);
        horseService = plugin.getHorseService();
        backpackService = new HorseBackpackService(plugin);
        dataManager = plugin.getHorseDataManager();
    }

    @AfterEach
    public void tearDown() {
        try { MockBukkit.unmock(); } catch (IllegalStateException ignored) {}
    }

    @Test
    public void concurrentHorseDataAccessStress() throws Exception {
        int threads = getIntProp("stress.threads", 8);
        int iterations = getIntProp("stress.iterations", 200);

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        ConcurrentLinkedQueue<Long> latencies = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Throwable> errors = new ConcurrentLinkedQueue<>();
        AtomicLong operations = new AtomicLong();

        Runtime rt = Runtime.getRuntime();
        long memBefore = rt.totalMemory() - rt.freeMemory();
        long testStart = System.nanoTime();

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            futures.add(executor.submit(() -> {
                for (int j = 0; j < iterations; j++) {
                    long start = System.nanoTime();
                    try {
                        UUID id = UUID.randomUUID();
                        HorseData data = dataManager.getHorseData(id);
                        data.setLevel(ThreadLocalRandom.current().nextInt(100));
                        data.setExperience(ThreadLocalRandom.current().nextInt(1000));
                        dataManager.saveHorseData(data);
                        horseService.addJump(id);
                        horseService.addTraveledBlocks(id, ThreadLocalRandom.current().nextDouble(10));
                        operations.incrementAndGet();
                    } catch (Throwable t) {
                        errors.add(t);
                    } finally {
                        latencies.add(System.nanoTime() - start);
                    }
                }
            }));
        }

        for (Future<?> f : futures) {
            f.get(5, TimeUnit.MINUTES);
        }

        long testEnd = System.nanoTime();
        long memAfter = rt.totalMemory() - rt.freeMemory();

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        assertMetrics("concurrentHorseDataAccessStress", latencies, operations.get(), errors, testStart, testEnd, memBefore, memAfter);
    }

    @Test
    public void highFrequencySaveAndLoad() throws Exception {
        int threads = getIntProp("stress.threads", 8);
        int iterations = getIntProp("stress.iterations", 500);

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        ConcurrentLinkedQueue<Long> latencies = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Throwable> errors = new ConcurrentLinkedQueue<>();
        AtomicLong operations = new AtomicLong();

        Runtime rt = Runtime.getRuntime();
        long memBefore = rt.totalMemory() - rt.freeMemory();
        long testStart = System.nanoTime();

        List<Callable<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            tasks.add(() -> {
                for (int j = 0; j < iterations; j++) {
                    long start = System.nanoTime();
                    try {
                        UUID id = UUID.randomUUID();
                        HorseData data = dataManager.getHorseData(id);
                        data.setBackpackItems(new org.bukkit.inventory.ItemStack[9]);
                        dataManager.saveHorseData(data);
                        operations.incrementAndGet();
                    } catch (Throwable t) {
                        errors.add(t);
                    } finally {
                        latencies.add(System.nanoTime() - start);
                    }
                }
                return null;
            });
        }

        executor.invokeAll(tasks, 10, TimeUnit.MINUTES);

        long testEnd = System.nanoTime();
        long memAfter = rt.totalMemory() - rt.freeMemory();

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        assertMetrics("highFrequencySaveAndLoad", latencies, operations.get(), errors, testStart, testEnd, memBefore, memAfter);
    }

    @Test
    public void backpackServiceConcurrentArmorSaves() throws Exception {
        int threads = getIntProp("stress.threads", 12);
        int iterations = getIntProp("stress.iterations", 200);

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        ConcurrentLinkedQueue<Long> latencies = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Throwable> errors = new ConcurrentLinkedQueue<>();
        AtomicLong operations = new AtomicLong();

        Runtime rt = Runtime.getRuntime();
        long memBefore = rt.totalMemory() - rt.freeMemory();
        long testStart = System.nanoTime();

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            futures.add(executor.submit(() -> {
                for (int j = 0; j < iterations; j++) {
                    long start = System.nanoTime();
                    try {
                        backpackService.saveHorseArmor(UUID.randomUUID(), null);
                        operations.incrementAndGet();
                    } catch (Throwable t) {
                        errors.add(t);
                    } finally {
                        latencies.add(System.nanoTime() - start);
                    }
                }
            }));
        }

        for (Future<?> f : futures) {
            f.get(3, TimeUnit.MINUTES);
        }

        long testEnd = System.nanoTime();
        long memAfter = rt.totalMemory() - rt.freeMemory();

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        assertMetrics("backpackServiceConcurrentArmorSaves", latencies, operations.get(), errors, testStart, testEnd, memBefore, memAfter);
    }

    private void assertMetrics(
            String testName,
            Collection<Long> latencies,
            long operations,
            Collection<Throwable> errors,
            long start,
            long end,
            long memBefore,
            long memAfter
    ) {
        assertTrue(errors.isEmpty(), "Errors: " + errors.size());

        List<Long> sorted = new ArrayList<>(latencies);
        Collections.sort(sorted);

        long min = sorted.get(0);
        long max = sorted.get(sorted.size() - 1);
        long avg = (long) sorted.stream().mapToLong(Long::longValue).average().orElse(0);
        long p95 = sorted.get((int) (sorted.size() * 0.95));

        double seconds = (end - start) / 1_000_000_000.0;
        double throughput = operations / seconds;

        System.out.printf(
                "%n[%s]%noperations=%d, time=%.2fs, throughput=%.2f ops/s%nmin=%dns, avg=%dns, p95=%dns, max=%dns%nmemBefore=%d, memAfter=%d%n",
                testName,
                operations,
                seconds,
                throughput,
                min,
                avg,
                p95,
                max,
                memBefore,
                memAfter
        );

        assertTrue(min >= 0);
        assertTrue(max >= min);
        assertTrue(p95 >= min);
        assertTrue(throughput > 0);
        assertTrue(memAfter >= 0 && memBefore >= 0);
    }
}
