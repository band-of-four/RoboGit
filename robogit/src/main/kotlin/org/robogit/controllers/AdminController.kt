package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Role
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
@Slf4j
class AdminController {
    @Autowired
    private val userRepository: UserRepository? = null

    @PostMapping("/updateRole")
    fun addPlatform(@RequestParam login: String, @RequestParam role : Role): ResponseEntity<HttpStatus> {
        val user = userRepository?.findByLogin(login)
        user?.role = role
        userRepository?.save(user!!)
        return ResponseEntity(HttpStatus.OK)
    }
}
