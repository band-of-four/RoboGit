package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.User
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
