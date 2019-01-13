package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.*
import org.robogit.dto.*
import org.robogit.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//всякая хрень для тестов

@RestController
@RequestMapping("/user")
@Slf4j
class UserController {
  @Autowired
  private val userRepository: UserRepository? = null
  @Autowired
  private val platformRepository: PlatformRepository? = null
  @Autowired
  private val informationRepository: InformationRepository? = null
  @Autowired
  private val controllerRepository: ControllerRepository? = null
  @Autowired
  private val mechanicDetailRepository: MechanicDetailRepository? = null
  @Autowired
  private val motorRepository: MotorRepository? = null
  @Autowired
  private val sensorRepository: SensorRepository? = null

  @GetMapping("/all/{id}")
  fun getAll(@PathVariable("id") id: Int?): User {
    println("Controller Hello!")
    return userRepository?.findById(id!!)!!.get()
  }

  @GetMapping("/login/{login}")
  fun getUser(@PathVariable("login") login: String?): User? {
    println("Controller Hello!")
    return userRepository?.findByLogin(login!!)
  }

  @GetMapping("/controller/{id}")
  fun getController(@PathVariable("id") id: Int?): Controller {
    println("Controller Hello!")

    return controllerRepository!!.findAll().iterator().next()
  }

  @GetMapping("/platform/{id}")
  fun getPlatform(@PathVariable("id") id: Int?): Platform {
    println("Controller Hello!")
    return platformRepository?.findById(id!!)!!.get()
  }

  @GetMapping("/information")
  fun getInformation(): List<InformationSumDto?>? {
    println("Controller Hello!")
    return informationRepository?.findPopular()
  }

    @GetMapping("/controller")
    fun getController(): List<ControllerSumDto?>? {
        println("Controller Hello!")
        return controllerRepository?.findPopular()
    }

    @GetMapping("/mechanic_detail")
    fun getInterface(): List<MechanicDetailSumDto?>? {
        println("Controller Hello!")
        return mechanicDetailRepository?.findPopular()
    }

    @GetMapping("/motor")
    fun getMotor(): List<MotorSumDto?>? {
        println("Controller Hello!")
        return motorRepository?.findPopular()
    }

    @GetMapping("/platform")
    fun getPlatform(): List<PlatformSumDto?>? {
        println("Controller Hello!")
        return platformRepository?.findPopular()
    }

    @GetMapping("/sensor")
    fun getSensor(): List<SensorSumDto?>? {
        println("Controller Hello!")
        return sensorRepository?.findPopular()
    }
}
