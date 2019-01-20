package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Sensor
import org.robogit.dto.SensorSumDto
import org.robogit.repository.SensorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

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

    @GetMapping("/sensor/{page}")
    fun getSensorByPage(@PathVariable("page") numPage: Int): List<SensorSumDto?>? {
        println("Controller!")
        val page = PageRequest.of(numPage, 50)
        return sensorRepository?.findPagePopular(page)?.content
    }

    @GetMapping("/sensor/filter")
    fun filter(@RequestParam(defaultValue = "0", required = false) pageNum: Int,
               @RequestParam(required = false) min_price: Float?,
               @RequestParam(required = false) max_price: Float?,
               @RequestParam(required = false) min_min_voltage: Float?,
               @RequestParam(required = false) max_min_voltage: Float?,
               @RequestParam(required = false) min_max_voltage: Float?,
               @RequestParam(required = false) max_max_voltage: Float?) : List<Sensor?>?{
        println("Controller!")
        val page = PageRequest.of(pageNum, 50, Sort.by(Sort.Direction.ASC,"id"))
        return sensorRepository?.filter(page, min_price, max_price, min_min_voltage, max_min_voltage,
                min_max_voltage, max_max_voltage)?.content
    }
}
