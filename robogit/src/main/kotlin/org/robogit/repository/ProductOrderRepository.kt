package org.robogit.repository

import org.robogit.domain.Order
import org.robogit.domain.ProductOrder
import org.springframework.data.repository.CrudRepository

interface ProductOrderRepository: CrudRepository<ProductOrder, Int> {

  /**
   * Finds list with instances of [ProductOrder] with specified [order].
   * @return List with results.
   */
  fun findByOrder(order: Order): List<ProductOrder>
}
