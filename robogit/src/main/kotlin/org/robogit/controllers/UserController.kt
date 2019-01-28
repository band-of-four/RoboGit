package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.User
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.*
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

  @GetMapping("/getUsername")
  fun getUsername(principal: Principal?): String? {
    if (principal == null) return null
    return if (principal is OAuth2Authentication && principal.userAuthentication.details is Map<*, *>) {
      (principal.userAuthentication.details as Map<String, String>)["name"]
    } else null
  }

  @GetMapping("/getLogin")
  fun getLogin(principal: Principal?): String? {
    if (principal == null) return null
    return principal.name
  }

  @GetMapping("/getPrincipal")
  fun getPrincipal(principal: Principal?) = principal

  @PostMapping("/user/updateProperties")
  fun updateProperties(properties: UserProperties, authentication: Authentication?): ResponseEntity<HttpStatus> {
    if (authentication == null) return ResponseEntity(HttpStatus.UNAUTHORIZED)
    val user = (userRepository?.findByLogin(authentication.name)) ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
    if (!properties.email.isNullOrEmpty()) user.email = properties.email
    if (!properties.name.isNullOrEmpty()) user.name = properties.name
    if (!properties.telegramId.isNullOrEmpty()) user.telegramId = properties.telegramId
    userRepository.save(user)
    return ResponseEntity(HttpStatus.OK)
  }
}

data class UserProperties(val email: String? = null, val telegramId: String? = null, val name: String? = null)
