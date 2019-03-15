package com.blah.crud.crudtest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.blah.crud.crudtest.persistence.repository")
@PropertySource("classpath:/application.properties")
@EntityScan(basePackages={ "com.blah.crud.crudtest.persistence.entity" })
public class JPAPersistenceConfig {
}
