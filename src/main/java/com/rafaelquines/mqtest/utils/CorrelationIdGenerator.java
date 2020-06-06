package com.rafaelquines.mqtest.utils;

import java.util.UUID;

public class CorrelationIdGenerator {
    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
