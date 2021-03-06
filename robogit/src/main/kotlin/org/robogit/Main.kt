package org.robogit

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso

@SpringBootApplication
@Slf4j
open class Main {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(Main::class.java, *args)
    }
  }
}
