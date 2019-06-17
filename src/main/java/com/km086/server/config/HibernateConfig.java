package com.km086.server.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.km086.server.repository.ExtendedJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = ExtendedJpaRepository.class, basePackages = "com.km086.server.repository")
public class HibernateConfig {

    @Bean
    public Module hibernateModule() {
        return new Hibernate5Module();
    }
}
