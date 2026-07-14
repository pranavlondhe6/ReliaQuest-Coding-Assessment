package com.challenge.api.config;

import com.challenge.api.model.EmployeeEntity;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeStoreConfig {

    @Bean
    public ConcurrentMap<UUID, EmployeeEntity> employeeStore() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ConcurrentMap<String, EmployeeEntity> emailStore() {
        return new ConcurrentHashMap<>();
    }
}
