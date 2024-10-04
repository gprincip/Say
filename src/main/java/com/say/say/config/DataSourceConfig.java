package com.say.say.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class)
                .url("jdbc:postgresql://localhost:5432/saydb")
                .username("sayadmin")
                .password("say%pw")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
	
}
