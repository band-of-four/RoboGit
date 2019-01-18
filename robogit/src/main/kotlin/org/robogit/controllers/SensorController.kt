package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.dto.SensorSumDto
import org.robogit.repository.SensorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Slf4j
class SensorController {
    @Autowired
    private val sensorRepository: SensorRepository? = null

    @GetMapping("/sensor")
    fun getSensor(): List<SensorSumDto?>? {
        println("Controller!")
        return sensorRepository?.findPopular()
    }
}