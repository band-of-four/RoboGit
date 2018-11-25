package org.robogit.repository

import org.robogit.domain.ProductOrder
import org.springframework.data.repository.CrudRepository

interface ProductOrderRepository: CrudRepository<ProductOrder, Int> {
}