package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Platform
import org.robogit.dto.PlatformSumDto
import org.robogit.repository.PlatformRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Slf4j
class PlatformController {
    @Autowired
    private val platformRepository: PlatformRepository? = null

    @GetMapping("/platform/{id}")
    fun getPlatform(@PathVariable("id") id: Int?): Platform {
        println("Controller!")
        return platformRepository?.findById(id!!)!!.get()
    }

    @GetMapping("/platform")
    fun getPlatform(): List<PlatformSumDto?>? {
        println("Controller!")
        return platformRepository?.findPopular()
    }
}