package com.yantar.bankingsystem.util.impl;

import com.yantar.bankingsystem.util.TokenIdGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TokenIdGeneratorImpl implements TokenIdGenerator {
    private final long rangeMin;
    private final long rangeMax;
    private final Random activationCodeRandom = new Random();

    public TokenIdGeneratorImpl(@Value("${app.security.session.token-id-length}")
                                @Min(6) @Max(15) int keyLength) {

        rangeMin = Math.round(Math.pow(10, 6));
        rangeMax = rangeMin * 10;
    }

    @Override
    public String generate() {
        return String.valueOf(activationCodeRandom.nextLong(rangeMax - rangeMin) + rangeMin);
    }
}
