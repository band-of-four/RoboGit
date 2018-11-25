package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Controller
import org.robogit.domain.Platform
import org.robogit.domain.User
import org.robogit.repository.ControllerRepository
import org.robogit.repository.PlatformRepository
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
@Slf4j
class UserController {
    @Autowired
    private val userRepository: UserRepository? = null
    @Autowired
    private val platformRepository: PlatformRepository? = null

    @Autowired
    private val controllerRepository: ControllerRepository? = null

    @GetMapping("/all/{id}")
    fun getAll(@PathVariable("id") id: Int?): User {
        println("Controller Hello!")
        return userRepository?.findById(id)!!.get()
    }

    @GetMapping("/controller/{id}")
    fun getController(@PathVariable("id") id: Int?): Controller {
        println("Controller Hello!")

        return controllerRepository!!.findAll().iterator().next()
    }

    @GetMapping("/platform/{id}")
    fun getPlatform(@PathVariable("id") id: Int?): Platform {
        println("Controller Hello!")
        return platformRepository?.findById(id)!!.get()
    }

}
