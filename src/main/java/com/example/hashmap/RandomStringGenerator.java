package com.example.hashmap;

import java.util.Random;

public class RandomStringGenerator {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Random random;

    public RandomStringGenerator() {
        this.random = new Random(42);
    }

    public RandomStringGenerator(long seed) {
        this.random = new Random(seed);
    }

    public String generateString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public String[] generateStringArray(int count, int stringLength) {
        String[] strings = new String[count];
        for (int i = 0; i < count; i++) {
            strings[i] = generateString(stringLength);
        }
        return strings;
    }
}