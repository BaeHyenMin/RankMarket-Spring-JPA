package com.market.rank.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataBaseConfig {

    private final EntityManager em;

    @Bean
    public JPAQueryFactory queryFactoryFactory() {
        return new JPAQueryFactory(em);
    }
}
