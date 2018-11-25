package org.robogit.repository

import org.robogit.domain.ProductUser
import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface ProductUserRepository: CrudRepository<ProductUser, Int> {

  /**
   * Finds all shopping carts (i.e. instances of [ProductUser]) owned by specified [user].
   * @param user is an owner of the shopping carts.
   * @return List of shopping carts.
   */
  fun findByUser(user: User): List<ProductUser>
}
