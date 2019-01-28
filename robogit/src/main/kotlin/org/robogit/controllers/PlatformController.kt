package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Information
import org.robogit.domain.Platform
import org.robogit.dto.PlatformSumDto
import org.robogit.repository.PlatformRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Slf4j
class PlatformController {
    @Autowired
    private val platformRepository: PlatformRepository? = null

    @GetMapping("/platform/info/{id}")
    fun getPlatform(@PathVariable("id") id: Int?): Platform {
        println("Controller!")
        return platformRepository?.findById(id!!)!!.get()
    }

    @GetMapping("/platform")
    fun getPlatform(): List<PlatformSumDto?>? {
        println("Controller!")
        return platformRepository?.findPopular()
    }

    @GetMapping("/platform/{page}")
    fun getPlatformByPage(@PathVariable("page") numPage: Int): List<PlatformSumDto?>? {
        println("Controller!")
        val page = PageRequest.of(numPage, 50)
        return platformRepository?.findPagePopular(page)?.content
    }

    @GetMapping("/platform/filter")
    fun filter(@RequestParam(defaultValue = "0", required = false) pageNum: Int,
               @RequestParam(required = false) min_price: Float?,
               @RequestParam(required = false) max_price: Float?,
               @RequestParam(required = false) min_min_voltage: Float?,
               @RequestParam(required = false) max_min_voltage: Float?,
               @RequestParam(required = false) min_max_voltage: Float?,
               @RequestParam(required = false) max_max_voltage: Float?,
               @RequestParam(required = false) min_analog_inputs: Int?,
               @RequestParam(required = false) max_analog_inputs: Int?,
               @RequestParam(required = false) min_flashmemory: Int?,
               @RequestParam(required = false) max_flashmemory: Int?,
               @RequestParam(required = false) min_ram: Int?,
               @RequestParam(required = false) max_ram: Int?) : List<Information?>?{
        println("Controller!")
        val page = PageRequest.of(pageNum, 50)
        return platformRepository?.filter(page, min_price, max_price, min_min_voltage, max_min_voltage,
                min_max_voltage, max_max_voltage, min_analog_inputs,
                max_analog_inputs, min_flashmemory, max_flashmemory, min_ram, max_ram)?.content
    }
}