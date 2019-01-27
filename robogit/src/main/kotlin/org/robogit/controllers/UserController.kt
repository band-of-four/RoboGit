package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.User
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api")
@Slf4j
class UserController {
  @Autowired
  private val userRepository: UserRepository? = null

  @GetMapping("/user/{id}")
  fun getAll(@PathVariable("id") id: Int?): User {
    println("Controller!")
    return userRepository?.findById(id!!)!!.get()
  }

  @GetMapping("/login/{login}")
  fun getUser(@PathVariable("login") login: String?): User? {
    println("Controller!")
    return userRepository?.findByLogin(login!!)
  }

  @RequestMapping("/getUsername")
  fun getUsername(principal: Principal?): String? {
    if (principal == null) return null
    return if (principal is OAuth2Authentication && principal.userAuthentication.details is Map<*, *>) {
      (principal.userAuthentication.details as Map<String, String>)["name"]
    } else null
  }

  @RequestMapping("/getLogin")
  fun getLogin(principal: Principal?): String? {
    if (principal == null) return null
    return principal.name
  }

  @RequestMapping("/getPrincipal")
  fun getPrincipal(principal: Principal?) = principal
}
