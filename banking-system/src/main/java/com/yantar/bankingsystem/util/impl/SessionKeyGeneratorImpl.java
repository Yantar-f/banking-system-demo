package com.yantar.bankingsystem.util.impl;

import com.yantar.bankingsystem.util.SessionKeyGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SessionKeyGeneratorImpl implements SessionKeyGenerator {
    private final long rangeMin;
    private final long rangeMax;
    private final int keyLength;
    private final Random random = new Random();

    public SessionKeyGeneratorImpl(@Value("${app.security.session.session-key-length}")
                                   @Min(6) @Max(15) int keyLength) {

        this.keyLength = keyLength;

        rangeMin = Math.round(Math.pow(10, 6));
        rangeMax = rangeMin * 10;
    }

    @Override
    public String generate() {
        return String.valueOf(random.nextLong(rangeMax - rangeMin) + rangeMin);
    }

    @Override
    public long getKeyLength() {
        return keyLength;
    }
}
