package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Information
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
class OtherController {
    @Autowired
    private val informationRepository: InformationRepository? = null


    @GetMapping("/other/{page}")
    fun getMotor(@PathVariable("page") numPage: Int): List<InformationSumDto>? {
        println("Controller!")
        val page = PageRequest.of(numPage, 50)
        return informationRepository?.findPopularOther(page)?.content
    }
}