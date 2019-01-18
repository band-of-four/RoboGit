package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Controller
import org.robogit.dto.ControllerSumDto
import org.robogit.repository.ControllerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Slf4j
class ControllerController {
    @Autowired
    private val controllerRepository: ControllerRepository? = null

    @GetMapping("/controller/{id}")
    fun getController(@PathVariable("id") id: Int?): Controller {
        println("Controller!")
        return controllerRepository!!.findAll().iterator().next()
    }

    @GetMapping("/controller")
    fun getController(): List<ControllerSumDto?>? {
        println("Controller!")
        return controllerRepository?.findPopular()
    }
}