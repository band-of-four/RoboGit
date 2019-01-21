package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.*
import org.robogit.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/telebot/dev")
@Slf4j
class TelegramControllerDev {
  @Autowired
  private val infoRepository: InformationRepository? = null

  @Autowired
  private val interfaceRepository: InterfaceRepository? = null

  @Autowired
  private val motorRepository: MotorRepository? = null

  @Autowired
  private val userRepository: UserRepository? = null

  @Autowired
  private val orderRepository: OrderRepository? = null

  @GetMapping("/addOrder")
  fun addOneOrder() {
    if (motorRepository == null) println("Run away! DI does not work!")
    val info = Information().apply {
      this.name = "TopMotor"
      this.type = Type.MOTOR
      this.creationDate = Date()
      this.amount = 1024
      this.price = 0.013f
    }
    infoRepository?.save(info)

    val motor = Motor().apply {
      this.id = info.id
      this.information = info
      this.maxVoltage = 3f
      this.minVoltage = 2.5f
      this.power = 5f
    }
    val interfece = Interface().apply {
      (this.motors as MutableSet<Motor>).add(motor)
      this.name = "2PinInterface"
    }
    val savedInterface = interfaceRepository?.save(interfece)

    motor.motorInterface = savedInterface
    motorRepository?.save(motor)

    val user = User().apply {
      this.login = "bad_boy"
      this.password = "qwerty12345"
      this.role = Role.AUTHORIZED
      this.telegramId = "12324141"
    }
    userRepository?.save(user)

    val order = Order().apply {
      isPaid = false
      address = "V9zma 5/7"
      date = Date()
      this.user = user
    }
    (order.productOrders as MutableSet<ProductOrder>).add(ProductOrder().apply {
      this.amount = 2
      this.information = motor.information
      this.order = order
      this.unit_price = motor.information?.price
    })
    orderRepository?.save(order)
  }

}
