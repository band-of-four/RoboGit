package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Information
import org.robogit.domain.MechanicDetail
import org.robogit.dto.MechanicDetailSumDto
import org.robogit.repository.MechanicDetailRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Slf4j
class MechanicDetailController {
    @Autowired
    private val mechanicDetailRepository: MechanicDetailRepository? = null

    @GetMapping("/mechanic_detail")
    fun getInterface(): List<MechanicDetailSumDto?>? {
        println("Controller!")
        return mechanicDetailRepository?.findPopular()
    }

    @GetMapping("/mechanic_detail/{page}")
    fun getMechanicDetailByPage(@PathVariable("page") numPage: Int): List<MechanicDetailSumDto?>? {
        println("Controller!")
        val page = PageRequest.of(numPage, 50)
        return mechanicDetailRepository?.findPagePopular(page)?.content
    }

    @GetMapping("/mechanic_detail/filter")
    fun filter(@RequestParam(defaultValue = "0", required = false) pageNum: Int,
               @RequestParam(required = false) min_price: Float?,
               @RequestParam(required = false) max_price: Float?) : List<Information?>?{
        println("Controller!")
        val page = PageRequest.of(pageNum, 50)
        return mechanicDetailRepository?.filter(page, min_price, max_price)?.content
    }
}