package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.dto.MotorSumDto
import org.robogit.repository.MotorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Slf4j
class MotorController {
    @Autowired
    private val motorRepository: MotorRepository? = null

    @GetMapping("/motor")
    fun getMotor(): List<MotorSumDto?>? {
        println("Controller!")
        return motorRepository?.findPopular()
    }

    @GetMapping("/motor/{page}")
    fun getMotorByPage(@PathVariable("page") numPage: Int): List<MotorSumDto?>? {
        println("Controller!")
        val page = PageRequest.of(numPage, 50)
        return motorRepository?.findPagePopular(page)?.content
    }
}