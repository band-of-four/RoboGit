package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Information
import org.robogit.dto.InformationSumDto
import org.robogit.repository.InformationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Slf4j
class OtherController {
    @Autowired
    private val informationRepository: InformationRepository? = null


    @GetMapping("/other/{page}")
    fun getMotor(@PathVariable("page") numPage: Int): List<InformationSumDto>? {
        println("Controller!")
        val page = PageRequest.of(numPage, 50)
        return informationRepository?.findPopularOther(page)?.content
    }

    @GetMapping("/other/filter")
    fun filter(@RequestParam(defaultValue = "0", required = false) pageNum: Int,
               @RequestParam(required = false) min_price: Float?,
               @RequestParam(required = false) max_price: Float?) : List<Information?>?{
        println("Controller!")
        val page = PageRequest.of(pageNum, 50)
        return informationRepository?.filterForOther(page, min_price, max_price)?.content
    }
}