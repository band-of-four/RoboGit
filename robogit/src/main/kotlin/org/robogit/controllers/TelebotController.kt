package org.robogit.controllers

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Order
import org.robogit.dto.OrderDto
import org.robogit.dto.OrderSumDto
import org.robogit.repository.OrderRepository
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import java.util.*

@RestController
@RequestMapping("/api/telebot")
@Slf4j
class TelebotController {

  @Autowired
  private var orderRepository: OrderRepository? = null

  @Autowired
  private var userRepository: UserRepository? = null

  @GetMapping("/getOrder/{telegramId}/{orderId}")
  fun getOrder(@PathVariable("telegramId") telegramId: String,
               @PathVariable("orderId") orderId: Int): Order? {
    val order = orderRepository?.findById(orderId)?.get() ?: return null
    val teleUser = userRepository?.findByTelegramId(telegramId)?.find { it.telegramId == telegramId }
    return if (!order.isPaid!! && order.user?.id == teleUser?.id) order else null
  }

  @GetMapping("/getFullPrice/{orderId}")
  fun getOrderContent(@PathVariable("orderId") orderId: Int): List<OrderSumDto?>? {
    return orderRepository?.getOrderSumByIdOrder(orderId) ?: return null
  }

  @PostMapping("/setDestination")
  fun setDestination(@RequestBody destinationDto: DestinationDto): ResponseEntity<HttpStatus> {
    val optionalOrder = orderRepository?.findById(destinationDto.orderId!!)
    val order = if (optionalOrder?.isPresent!!) optionalOrder.get() else return ResponseEntity(HttpStatus.BAD_REQUEST)
    val teleUser = (userRepository?.findByTelegramId(destinationDto.telegramId!!)
        ?.find { it.telegramId == destinationDto.telegramId })
        ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
    return if (order.user?.id == teleUser.id) {
      order.address = destinationDto.address
      orderRepository?.save(order)
      ResponseEntity(HttpStatus.OK)
    } else {
      ResponseEntity(HttpStatus.BAD_REQUEST)
    }
  }

  @PostMapping("/setDeliveryDate")
  fun setDeliveryDate(@RequestBody deliveryDateDto: DeliveryDateDto): String {
    val optionalOrder = orderRepository?.findById(deliveryDateDto.orderId!!)
    val order = if (optionalOrder?.isPresent!!) optionalOrder.get()
    else return "Order with id=${deliveryDateDto.orderId} was not found"

    val teleUser = (userRepository?.findByTelegramId(deliveryDateDto.telegramId!!)
        ?.find { it.telegramId == deliveryDateDto.telegramId })
        ?: return "Your order with id=${deliveryDateDto.orderId} was not found"
    return if (order.user?.id == teleUser.id) {
      order.deliveryDate = deliveryDateDto.date
      orderRepository?.save(order)
      "Delivery time set"
    } else {
      "You can not manage foreign orders"
    }
  }

}

data class DestinationDto(var orderId: Int? = null, var telegramId: String? = null, var address: String? = null)

data class DeliveryDateDto(var orderId: Int? = null, var telegramId: String? = null,
                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy") var date: Date? = null)
