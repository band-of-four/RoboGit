package org.robogit.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableJpaRepositories(basePackages = arrayOf("org.robogit.repository"))
@Configuration
@EnableTransactionManagement
open class DatabaseConfig {
  companion object {
    // const val SCHEMA_NAME = "s244707" // helios config
    const val SCHEMA_NAME = "public" // docker config
  }

}
