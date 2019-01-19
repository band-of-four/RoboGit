package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Controller
import org.robogit.domain.ProductUser
import org.robogit.domain.User
import org.robogit.dto.CardElementDto
import org.robogit.dto.ControllerSumDto
import org.robogit.repository.ControllerRepository
import org.robogit.repository.InformationRepository
import org.robogit.repository.ProductUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/card")
@Slf4j
class CardController {
    @Autowired
    private val productUserRepository: ProductUserRepository? = null
    @Autowired
    private val informationRepository: InformationRepository? = null

    @GetMapping("/get")
    fun getCard(): List<CardElementDto>? {
        println("get card!")
        //User user = SecurityContextHolder.getCurrentInstance.
        val userId = 100;
        return productUserRepository?.findAllByUserId(userId)
    }

    @PostMapping("/add")
    fun addToCard(@RequestParam partId: Int, @RequestParam amount: Int): ResponseEntity<HttpStatus> {
        println("Controller!")
        //User user = SecurityContextHolder.getCurrentInstance.
//        val userId = 100;
//        User user
//        val information = informationRepository?.findById(partId)?.get()
//        val productUser = ProductUser()
//        productUser.information = information
//        productUser.amount = amount
//        productUser.user = user
//        productUserRepository?.save(productUser)
        return ResponseEntity<HttpStatus>(HttpStatus.CREATED)
    }

    @PostMapping("/remove")
    fun removeFromCard(@RequestParam productUserId: Int): ResponseEntity<HttpStatus> {
        println("Controller!")
        //User user = SecurityContextHolder.getCurrentInstance.
        val userId = 100;
        val byUserIdAndId = productUserRepository?.findByUserIdAndId(userId, productUserId);
        productUserRepository?.delete(byUserIdAndId!!)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @PostMapping("/amount/set")
    fun setAmount(@RequestParam productUserId: Int,  @RequestParam amount: Int): ResponseEntity<HttpStatus> {
        println("Controller!")
        //User user = SecurityContextHolder.getCurrentInstance.
        val userId = 100;
        val byUserIdAndId = productUserRepository?.findByUserIdAndId(userId, productUserId)
        if(amount<0){
            return ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST)
        }
        byUserIdAndId?.amount = amount
        productUserRepository?.save(byUserIdAndId!!)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }
}