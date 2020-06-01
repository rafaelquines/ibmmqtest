package com.rafaelquines.mqtest;

import java.util.UUID;

public class CorrelationIdGenerator {
    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
