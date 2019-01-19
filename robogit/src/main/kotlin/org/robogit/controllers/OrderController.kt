package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.dto.OrderDto
import org.robogit.dto.OrderSumDto
import org.robogit.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Slf4j
class OrderController {
    @Autowired
    private val orderRepository: OrderRepository? = null

    @GetMapping("/order/{id}")
    fun getOrder(@PathVariable("id") id: Int?): List<OrderDto?>? {
        println("Controller!")
        return orderRepository?.getOrderByIdOrder(id)
    }

    @GetMapping("/order/sum/{id}")
    fun getOrderSum(@PathVariable("id") id: Int?): List<OrderSumDto?>? {
        println("Controller!")
        return orderRepository?.getOrderSumByIdOrder(id)
    }

}