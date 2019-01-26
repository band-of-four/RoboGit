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
import javax.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.ModelAttribute


@RestController
@RequestMapping("/api")
@Slf4j
class InformationController {
    @Autowired
    private val informationRepository: InformationRepository? = null

    @ModelAttribute
    fun setResponseHeaders(response: HttpServletResponse) {
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
    }

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

    @GetMapping("/information/by_id/{id}")
    fun getInformationById(@PathVariable("id") id: Int): Information? {
        println("getInformationByPage")
        return informationRepository?.findById(id)?.get()
    }
}