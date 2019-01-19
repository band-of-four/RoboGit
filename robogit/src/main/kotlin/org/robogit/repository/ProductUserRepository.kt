package org.robogit.repository

import org.robogit.domain.ProductUser
import org.robogit.domain.User
import org.robogit.dto.CardElementDto
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ProductUserRepository: CrudRepository<ProductUser, Int> {

  /**
   * Finds all shopping carts (i.e. instances of [ProductUser]) owned by specified [user].
   * @param user is an owner of the shopping carts.
   * @return List of shopping carts.
   */
  fun findByUser(user: User): List<ProductUser>

  @Query("SELECT pu FROM ProductUser pu JOIN pu.user puu WHERE puu.id = :userId AND pu.id = :id")
  fun findByUserIdAndId(@Param("userId") userId:Int, @Param("id") id:Int) : ProductUser

  @Query("SELECT new org.robogit.dto.CardElementDto(pu.information, pu.amount, pu.id) FROM ProductUser pu JOIN pu.user puu WHERE puu.id = :userId")
  fun findAllByUserId(@Param("userId") userId:Int) : List<CardElementDto>?
}
