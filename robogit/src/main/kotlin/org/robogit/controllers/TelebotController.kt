package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Order
import org.robogit.dto.OrderDto
import org.robogit.repository.OrderRepository
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/telebot")
@Slf4j
class TelebotController {

  @Autowired
  private var orderRepository: OrderRepository? = null

  @Autowired
  private var userRepository: UserRepository? = null

  @GetMapping("/getOrder/{telegramId}/{orderId}")
  fun getOrder(@PathVariable("telegramId") telegramId: String, @PathVariable("orderId") orderId: Int): Order? {
    val order = orderRepository?.findById(orderId)?.get() ?: return null
    val teleUser = userRepository?.findByTelegramId(telegramId)?.find { it.telegramId == telegramId }
    return if (!order.isPaid!! && order.user?.id == teleUser?.id) order else null
  }

}
