package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Card
import org.robogit.domain.ProductCard
import org.robogit.domain.ProductUser
import org.robogit.domain.User
import org.robogit.dto.CardElementDto
import org.robogit.repository.CardRepository
import org.robogit.repository.InformationRepository
import org.robogit.repository.ProductUserRepository
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
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

  @Autowired
  private val cardRepository: CardRepository? = null

  @Autowired
  private val userRepository: UserRepository? = null

  @GetMapping("/get")
  fun getCard(): List<CardElementDto>? {
    println("get card!")
    //User user = SecurityContextHolder.getCurrentInstance.
    val userId = 100
    return productUserRepository?.findAllByUserId(userId)
  }

//    @GetMapping("/get")
//    fun getCard(authentication: Authentication): List<CardElementDto>? {
//        println("get card!")
//        val userDetails = (OpenAmUserDetails) authentication.details
//        return productUserRepository?.findAllByUserId(userDetails.userId)
//    }

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
    val userId = 100
    val byUserIdAndId = productUserRepository?.findByUserIdAndId(userId, productUserId)
    productUserRepository?.delete(byUserIdAndId!!)
    return ResponseEntity<HttpStatus>(HttpStatus.OK)
  }

  @PostMapping("/amount/set")
  fun setAmount(@RequestParam productUserId: Int, @RequestParam amount: Int): ResponseEntity<HttpStatus> {
    println("Controller!")
    //User user = SecurityContextHolder.getCurrentInstance.
    val userId = 100
    val byUserIdAndId = productUserRepository?.findByUserIdAndId(userId, productUserId)
    if (amount < 0) {
      return ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST)
    }
    byUserIdAndId?.amount = amount
    productUserRepository?.save(byUserIdAndId!!)
    return ResponseEntity<HttpStatus>(HttpStatus.OK)
  }

  @PostMapping("/share")
  fun shareCard(): Int? {
    // User user = SecurityContextHolder.getCurrentInstance.
    val user = User().apply { id = 100 } // FIXME
    val products = productUserRepository?.findAllByUserId(user.id
        ?: throw IllegalArgumentException("Not authorized user ${user.login}")) // FIXME maybe should return null
    val cardToSave = Card().apply { this.user = user }
    products?.forEach {
      (cardToSave.productCards as MutableSet<ProductCard>).add(ProductCard().apply {
        this.information = it.information
        this.amount = it.amount
        this.card = cardToSave
      })
    }
    val savedCard = cardRepository?.save(cardToSave)
    return savedCard?.id
  }

  @PostMapping("/getShared")
  fun getSharedCard(@RequestParam sharedId: Int): Card? {
    val card = cardRepository?.findById(sharedId)
    return if (card?.isPresent!!) {
      card.get()
    } else {
      null
    }
  }

  @PostMapping("/copyShared")
  fun copySharedCardToLocal(@RequestParam sharedId: Int): ResponseEntity<HttpStatus> {
    // User user = SecurityContextHolder.getCurrentInstance.
    val user = User().apply { id = 100 } // FIXME
    val sharedCard = getSharedCard(sharedId)
        ?: throw IllegalArgumentException("Shared card with id: $sharedId not found") // FIXME maybe should return bad http status
    cleanLocalCard()
    sharedCard.productCards.forEach {
      (user.products as MutableSet<ProductUser>).add(ProductUser().apply {
        this.user = user
        this.amount = it.amount
        this.information = it.information
      })
    }
    userRepository?.save(user)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/clean")
  fun cleanLocalCard(): ResponseEntity<HttpStatus> {
    // User user = SecurityContextHolder.getCurrentInstance.
    val user = User().apply { id = 100 } // FIXME
    (user.products as MutableSet<ProductUser>).clear()
    userRepository?.save(user)
    return ResponseEntity(HttpStatus.OK)
  }

}
