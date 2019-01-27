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

  // FIXME do not use the method, use [OAuth2AuthenticationSuccessHandler] instead
  @GetMapping("/oauth-login-success")
  fun findOrCreateLocalUser(principal: Principal?): String {
    if (principal == null) return "redirect:/login"
    return if (principal is OAuth2Authentication && principal.userAuthentication.details is Map<*, *>) {
      // username is a real name
      val username = (principal.userAuthentication.details as Map<String, String>)["name"]
      // login is a unique key that identifies the user
      val login = principal.name
      if (!userRepository?.existsByLogin(login)!!) {
        val user = User().apply {
          this.login = login
          this.name = username
          this.role = Role.ROLE_USER
        }
        userRepository?.save(user)
      }
      "/"
    } else "redirect:/login"
  }
}
