package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.dto.MechanicDetailSumDto
import org.robogit.repository.MechanicDetailRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}