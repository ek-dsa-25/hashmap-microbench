package com.example.hashmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class MapComparisonBenchmark {

    // TODO: Add parameters for comparing HashMap vs TreeMap
    // @Param({"1000", "10000"})
    // private int insertionCount;

    // TODO: Add map type parameter
    // @Param({"HASHMAP", "TREEMAP"})
    // private String mapType;

    private String[] keys;
    private String[] values;

    @Setup(Level.Trial)
    public void setup() {
        // TODO: Initialize test data using RandomStringGenerator
        // TODO: Consider pre-populating a map for lookup benchmarks
    }

    // TODO: Implement benchmarkInsertion method
    // Compare HashMap O(1) vs TreeMap O(log n) insertion performance

    // TODO: Implement benchmarkLookup method
    // Compare HashMap O(1) vs TreeMap O(log n) lookup performance

    // TODO: Implement benchmarkSortedIteration method
    // Show TreeMap's advantage - naturally sorted key iteration
    // HashMap requires collecting and sorting keys first

    // TODO: Create createMap() method that returns Map<String, String>
    // Switch between HashMap and TreeMap based on mapType parameter
    // Note: TreeMap doesn't have initial capacity like HashMap
}