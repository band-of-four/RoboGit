package org.robogit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "org.robogit.repository")
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
}
