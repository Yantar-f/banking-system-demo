package com.yantar.bankingsystem.util;

public interface SessionKeyGenerator {
    String generate();
    long getKeyLength();
}
