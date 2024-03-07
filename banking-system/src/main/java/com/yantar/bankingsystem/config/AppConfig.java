package com.yantar.bankingsystem.config;

import com.yantar.bankingsystem.entity.RoleEntity;
import com.yantar.bankingsystem.repository.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Arrays;

@Configuration
public class AppConfig {
    public AppConfig(RoleRepository repository) {
        Arrays.stream(Role.values()).forEach(role -> {
            if (! repository.existsByDesignation(role))
                repository.save(new RoleEntity(role));
        });
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
