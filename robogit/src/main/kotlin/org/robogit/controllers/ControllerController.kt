package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Controller
import org.robogit.domain.Information
import org.robogit.dto.ControllerSumDto
import org.robogit.repository.ControllerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Slf4j
class ControllerController {
    @Autowired
    private val controllerRepository: ControllerRepository? = null

    @GetMapping("/controller/info/{id}")
    fun getController(@PathVariable("id") id: Int): Controller? {
        println("Controller!")
        return controllerRepository?.findById(id)?.get()
    }

    @GetMapping("/controller")
    fun getController(): List<ControllerSumDto?>? {
        println("Controller!")
        return controllerRepository?.findPopular()
    }

    @GetMapping("/controller/filter")
    fun filter(@RequestParam(defaultValue = "0", required = false) pageNum: Int,
               @RequestParam(required = false) min_price: Float?,
               @RequestParam(required = false) max_price: Float?,
               @RequestParam(required = false) min_ram: Float?,
               @RequestParam(required = false) max_ram: Float?,
               @RequestParam(required = false) min_min_voltage: Float?,
               @RequestParam(required = false) max_min_voltage: Float?,
               @RequestParam(required = false) min_max_voltage: Float?,
               @RequestParam(required = false) max_max_voltage: Float?,
               @RequestParam(required = false) min_analog_inputs: Int?,
               @RequestParam(required = false) max_analog_inputs: Int?) : List<Information?>?{
        println("FILTER CONTROLLERS!")
        val page = PageRequest.of(pageNum, 50)
        return controllerRepository?.filter(page, min_price, max_price, min_ram, max_ram, min_min_voltage, max_min_voltage,
                min_max_voltage, max_max_voltage, min_analog_inputs,
                max_analog_inputs)?.content
    }

    @GetMapping("/controller/{page}")
    fun getControllerByPage(@PathVariable("page") numPage: Int): List<ControllerSumDto?>? {
        val page = PageRequest.of(numPage, 50)
        return controllerRepository?.findPagePopular(page)?.content
    }


}