package com.example.hashmap;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(HashMapBenchmark.class.getSimpleName())
                // TODO: Add MapComparisonBenchmark to runner
                // .include(MapComparisonBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}