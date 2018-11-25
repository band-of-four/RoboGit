package org.robogit.repository

import org.robogit.domain.ProductUser
import org.springframework.data.repository.CrudRepository

interface ProductUserRepository: CrudRepository<ProductUser, Int> {
}