package com.example.hashmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 1, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class QuickHashMapBenchmark {

    @Param({"1000"})
    private int insertionCount;

    @Param({"32"})
    private int keyLength;

    @Param({"16"})
    private int valueLength;

    @Param({"DEFAULT", "EXACT"})
    private String capacityStrategy;

    private String[] keys;
    private String[] values;

    @Setup(Level.Trial)
    public void setup() {
        RandomStringGenerator generator = new RandomStringGenerator(42);
        keys = generator.generateStringArray(insertionCount, keyLength);
        values = generator.generateStringArray(insertionCount, valueLength);
    }

    @Benchmark
    public void benchmarkInsertion(Blackhole bh) {
        HashMap<String, String> map = createHashMap();

        for (int i = 0; i < insertionCount; i++) {
            map.put(keys[i], values[i]);
        }

        bh.consume(map);
    }

    private HashMap<String, String> createHashMap() {
        return switch (capacityStrategy) {
            case "DEFAULT" -> new HashMap<>();
            case "EXACT" -> new HashMap<>(insertionCount);
            default -> throw new IllegalArgumentException("Unknown capacity strategy: " + capacityStrategy);
        };
    }
}