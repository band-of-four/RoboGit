package org.robogit.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableJpaRepositories(basePackages = ["org.robogit.repository"])
@Configuration
@EnableTransactionManagement
open class DatabaseConfig {
  companion object {
    const val SCHEMA_NAME = "public"
  }
}
