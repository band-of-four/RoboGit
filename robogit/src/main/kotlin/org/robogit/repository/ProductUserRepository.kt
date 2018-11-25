package org.robogit.repository

import org.robogit.domain.ProductUser
import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface ProductUserRepository: CrudRepository<ProductUser, Int> {
  fun findByUser(user: User): List<ProductUser>
}
