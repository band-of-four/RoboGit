package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
@Slf4j
class AuthController {
  @GetMapping("/login")
  fun login() = "/login.html"

  @GetMapping("/logout")
  fun logout() = "/logout.html"
}
