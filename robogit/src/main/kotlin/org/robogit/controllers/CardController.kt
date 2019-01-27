//package org.robogit.controllers
//
//import org.robogit.domain.Card
//import org.robogit.domain.ProductCard
//import org.robogit.domain.ProductUser
//import org.robogit.domain.User
//import org.robogit.dto.CardElementDto
//import org.robogit.repository.CardRepository
//import org.robogit.repository.InformationRepository
//import org.robogit.repository.ProductUserRepository
//import org.robogit.repository.UserRepository
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.security.core.Authentication
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/api")
//class CardController {
//  @Autowired
//  private val productUserRepository: ProductUserRepository? = null
//
//  @Autowired
//  private val informationRepository: InformationRepository? = null
//
//  @Autowired
//  private val cardRepository: CardRepository? = null
//
//  @Autowired
//  private val userRepository: UserRepository? = null
//
//  @GetMapping("/card/get")
//  fun getCard(authentication: Authentication): List<CardElementDto>? {
//      println("get card!")
//      val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
//      println(userDetails.userId)
//      return productUserRepository?.findAllByUserId(userDetails.userId)
//  }
//
//  @PostMapping("/card/add")
//  fun addToCard(@RequestParam partId: Int, @RequestParam amount: Int, authentication: Authentication)
//          : ResponseEntity<HttpStatus> {
//    println("Controller!")
//    val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
//    val information = informationRepository?.findById(partId)?.get()
//    val user = userRepository?.findById(userDetails.userId)?.get()
//    val productUser_ = productUserRepository?.findByUserIdAndProductId(user?.id!!, partId)
//    if (productUser_ != null){
//      productUser_.amount = productUser_.amount!! + 1
//      productUserRepository?.save(productUser_)
//      return ResponseEntity(HttpStatus.OK)
//    }
//    val productUser = ProductUser()
//    productUser.information = information
//    productUser.amount = amount
//    productUser.user = user
//    productUserRepository?.save(productUser)
//    return ResponseEntity(HttpStatus.CREATED)
//  }
//
//  @PostMapping("/card/remove")
//  fun removeFromCard(@RequestParam partId: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
//    println("Controller!")
//    val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
//    val byUserIdAndId = productUserRepository?.findByUserIdAndProductId(userDetails.userId, partId)
//    productUserRepository?.delete(byUserIdAndId!!)
//    return ResponseEntity(HttpStatus.OK)
//  }
//
//  @PostMapping("/card/amount/set")
//  fun setAmount(@RequestParam productId: Int, @RequestParam amount: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
//    println("Controller!")
//    val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
//    val byUserIdAndId = productUserRepository?.findByUserIdAndProductId(userDetails.userId, productId)
//    if (amount < 0) {
//      return ResponseEntity(HttpStatus.BAD_REQUEST)
//    }
//    byUserIdAndId?.amount = amount
//    productUserRepository?.save(byUserIdAndId!!)
//    return ResponseEntity(HttpStatus.OK)
//  }
//
//  @PostMapping("/card/share")
//  fun shareCard(authentication: Authentication): Int? {
//    val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
//    val user = User().apply { id = userDetails.userId }
//    val products = productUserRepository?.findAllByUserId(user.id
//        ?: throw IllegalArgumentException("Not authorized user ${user.login}")) // FIXME maybe should return null
//    val cardToSave = Card().apply { this.user = user }
//    products?.forEach {
//      (cardToSave.productCards as MutableSet<ProductCard>).add(ProductCard().apply {
//        this.information = it.information
//        this.amount = it.amount
//        this.card = cardToSave
//      })
//    }
//    val savedCard = cardRepository?.save(cardToSave)
//    return savedCard?.id
//  }
//
//  @PostMapping("/card/getShared")
//  fun getSharedCard(@RequestParam sharedId: Int): Card? {
//    val card = cardRepository?.findById(sharedId)
//    return if (card?.isPresent!!) {
//      card.get()
//    } else {
//      null
//    }
//  }
//
//  @PostMapping("/card/copyShared")
//  fun copySharedCardToLocal(@RequestParam sharedId: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
//    val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
//    val user = User().apply { id = userDetails.userId  }
//    val sharedCard = getSharedCard(sharedId)
//        ?: throw IllegalArgumentException("Shared card with id: $sharedId not found") // FIXME maybe should return bad http status
//    cleanLocalCard(authentication)
//    sharedCard.productCards.forEach {
//      (user.products as MutableSet<ProductUser>).add(ProductUser().apply {
//        this.user = user
//        this.amount = it.amount
//        this.information = it.information
//      })
//    }
//    userRepository?.save(user)
//    return ResponseEntity(HttpStatus.OK)
//  }
//
//  @PostMapping("/card/clean")
//  fun cleanLocalCard(authentication: Authentication): ResponseEntity<HttpStatus> {
//    val userDetails: OpenAmUserDetails = authentication.details as OpenAmUserDetails
//    val user = User().apply { id = userDetails.userId }
//    val infos = productUserRepository?.findAllByUserId(user.id!!)?.map { it.information }
//    val products = infos?.map{productUserRepository?.findByUserIdAndProductId(user.id!!, it.id!!)}
////    val products =  cardDto?.map{productUserRepository?.findById(it.productUserId)?.get()}
//    productUserRepository?.deleteAll(products!!)
//    return ResponseEntity(HttpStatus.OK)
//  }
//
//}
