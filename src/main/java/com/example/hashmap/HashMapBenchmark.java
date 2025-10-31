package com.example.hashmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class HashMapBenchmark {

    @Param({"100", "1000", "10000", "100000"})
    private int insertionCount;

    @Param({"8", "32", "128"})
    private int keyLength;

    @Param({"16"})
    private int valueLength;

    @Param({"DEFAULT", "EXACT", "UNDERSIZED", "OVERSIZED"})
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

    @Benchmark
    public void benchmarkInsertionAndLookup(Blackhole bh) {
        HashMap<String, String> map = createHashMap();

        for (int i = 0; i < insertionCount; i++) {
            map.put(keys[i], values[i]);
        }

        String result = null;
        for (int i = 0; i < insertionCount; i++) {
            result = map.get(keys[i]);
        }

        bh.consume(map);
        bh.consume(result);
    }

    private HashMap<String, String> createHashMap() {
        return switch (capacityStrategy) {
            case "DEFAULT" -> new HashMap<>();
            case "EXACT" -> new HashMap<>(insertionCount);
            case "UNDERSIZED" -> new HashMap<>(Math.max(1, insertionCount / 4));
            case "OVERSIZED" -> new HashMap<>(insertionCount * 2);
            default -> throw new IllegalArgumentException("Unknown capacity strategy: " + capacityStrategy);
        };
    }
}