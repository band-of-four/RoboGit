package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Motor
import org.robogit.dto.MotorSumDto
import org.robogit.repository.MotorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

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

    @GetMapping("/motor/filter")
    fun filter(@RequestParam(defaultValue = "0", required = false) pageNum: Int,
               @RequestParam(required = false) min_price: Float?,
               @RequestParam(required = false) max_price: Float?,
               @RequestParam(required = false) min_min_voltage: Float?,
               @RequestParam(required = false) max_min_voltage: Float?,
               @RequestParam(required = false) min_max_voltage: Float?,
               @RequestParam(required = false) max_max_voltage: Float?,
               @RequestParam(required = false) min_power: Float?,
               @RequestParam(required = false) max_power: Float?) : List<Motor?>?{
        println("Controller!")
        val page = PageRequest.of(pageNum, 50)
        return motorRepository?.filter(page, min_price, max_price, min_min_voltage, max_min_voltage,
                min_max_voltage, max_max_voltage, min_power, max_power)?.content
    }
}