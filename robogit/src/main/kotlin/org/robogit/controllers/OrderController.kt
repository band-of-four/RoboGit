package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Information
import org.robogit.domain.Order
import org.robogit.domain.ProductOrder
import org.robogit.dto.CardElementDto
import org.robogit.dto.OrderDto
import org.robogit.dto.OrderSumDto
import org.robogit.repository.*
import org.robogit.security.OpenAmUserDetails
import org.robogit.utils.MailSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
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
    @Autowired
    private val informationRepository: InformationRepository? = null

    @GetMapping("/order/{id}")
    fun getOrder(@PathVariable("id") id: Int?, authentication: Authentication): List<OrderDto?>? {
        println("Controller!")
        val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
        val userId = userDetails.userId
        return orderRepository?.getOrderByUserIdAndIdOrder(userId, id)
    }

    @GetMapping("/order/sum/{id}")
    fun getOrderSum(@PathVariable("id") id: Int?, authentication: Authentication): List<OrderSumDto?>? {
        println("Controller!")
        val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
        val userId = userDetails.userId
        val result2 = orderRepository?.getOrderSumByIdOrder(id)
        println(result2)
        return orderRepository?.getOrderSumByUserIdAndIdOrder(userId,id)
    }

    @PostMapping("/order/create")
    fun createOrder(authentication: Authentication): ResponseEntity<String> {
        println("Controller!")
        val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
        val userId = userDetails.userId
        val order = Order()
        order.date = Date.from(Instant.now())
        val userOpt = userRepository?.findById(userId)
        if (!userOpt?.isPresent!!) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        order.user = userOpt.get()

        val card = productUserRepository?.findAllByUserId(userId)

        card?.forEach {
            val byUserIdAndId = productUserRepository?.findByUserIdAndProductId(userOpt.get().id!!, it.information.id!!)
            val information = byUserIdAndId?.information!!
            if (information.amount!! < it.amount){
                return ResponseEntity(it.information.name!!, HttpStatus.BAD_REQUEST)
            }
        }

        val savedOrder = orderRepository?.save(order)
        for (item in card!!) {
            val byUserIdAndId = productUserRepository?.findByUserIdAndProductId(userOpt.get().id!!, item.information.id!!)
            val productOrder = ProductOrder()
            val information = byUserIdAndId?.information!!
            information.amount = information.amount!! - item.amount
            informationRepository?.save(information)
            productOrder.amount = byUserIdAndId.amount
            productOrder.information = information
            productOrder.order = savedOrder
            productOrder.unit_price = information.price
            productOrder.name = information.name
            productOrderRepository?.save(productOrder)
        }
        val mail = MailSender()
        if (order.user?.email != null && order.user?.email!!.isNotEmpty()) mail.sendMail(order.user?.email!!, "Заказ №" + order.id, mail.createOrderMessage(card))

        println("SAVED ORDER ID: "+savedOrder!!.id.toString())
        return ResponseEntity(savedOrder!!.id.toString(), HttpStatus.CREATED)
    }
}