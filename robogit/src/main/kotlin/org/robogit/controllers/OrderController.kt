package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Order
import org.robogit.domain.ProductOrder
import org.robogit.dto.CardElementDto
import org.robogit.dto.OrderDto
import org.robogit.dto.OrderSumDto
import org.robogit.repository.OrderRepository
import org.robogit.repository.ProductOrderRepository
import org.robogit.repository.ProductUserRepository
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/api")
@Slf4j
open class OrderController {
    @Autowired
    private val orderRepository: OrderRepository? = null
    @Autowired
    private val userRepository: UserRepository? = null
    @Autowired
    private val productOrderRepository: ProductOrderRepository? = null
    @Autowired
    private val productUserRepository: ProductUserRepository? = null

    @GetMapping("/order/{id}")
    fun getOrder(@PathVariable("id") id: Int?): List<OrderDto?>? {
        println("Controller!")
        val userId = 100
        println(orderRepository)
        return orderRepository?.getOrderByUserIdAndIdOrder(userId, id)
    }

    @GetMapping("/order/sum/{id}")
    fun getOrderSum(@PathVariable("id") id: Int?): List<OrderSumDto?>? {
        println("Controller!")
        val userId = 100
//        val result = orderRepository?.getOrderSumByUserIdAndIdOrder(userId, id)
        val result2 = orderRepository?.getOrderSumByIdOrder(id)
//        println(result)
        println(result2)
        return orderRepository?.getOrderSumByUserIdAndIdOrder(userId,id)
    }

    @PostMapping("/order/create")
    fun createOrder(@RequestBody card: List<CardElementDto>): ResponseEntity<HttpStatus> {
        println("Controller!")
        val userId = 100
        val order = Order()
        order.date = Date.from(Instant.now())
        val userOpt = userRepository?.findById(userId)
        if (!userOpt?.isPresent!!) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        order.user = userOpt.get()
        val savedOrder = orderRepository?.save(order)

        for (item in card) {
            val byUserIdAndId = productUserRepository?.findByUserIdAndId(userOpt.get().id!!, item.productUserId)
            val productOrder = ProductOrder()
            productOrder.amount = byUserIdAndId?.amount
            productOrder.information = byUserIdAndId?.information
            productOrder.order = savedOrder;
            productOrderRepository?.save(productOrder);
        }

        order.productOrders
        return ResponseEntity(HttpStatus.CREATED)
    }

}