package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Role
import org.robogit.domain.User
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
@Slf4j
class AuthController {
  @Autowired
  private var userRepository: UserRepository? = null

  @GetMapping("/login")
  fun login() = "/login.html"

  @GetMapping("/logout")
  fun logout() = "/logout.html"

}
