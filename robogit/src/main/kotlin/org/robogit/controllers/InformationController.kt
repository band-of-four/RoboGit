package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.dto.InformationSumDto
import org.robogit.repository.InformationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Slf4j
class InformationController {
    @Autowired
    private val informationRepository: InformationRepository? = null

    @GetMapping("/information")
    fun getInformation(): List<InformationSumDto?>? {
        println("Controller!")
        return informationRepository?.findPopular()    }

    @GetMapping("/information/{page}")
    fun getInformationByPage(@PathVariable("page") numPage: Int): List<InformationSumDto?>? {
        println("getInformationByPage")
        val page = PageRequest.of(numPage, 50)
        return informationRepository?.findPagePopular(page)?.content
    }
}