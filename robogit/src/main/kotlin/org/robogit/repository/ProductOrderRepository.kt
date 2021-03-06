package org.robogit.repository

import org.robogit.domain.Order
import org.robogit.domain.ProductOrder
import org.robogit.domain.ProductUser
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ProductOrderRepository: CrudRepository<ProductOrder, Int> {

  /**
   * Finds list with instances of [ProductOrder] with specified [order].
   * @return List with results.
   */
  fun findByOrder(order: Order): List<ProductOrder>

  /**
   * Поиск товаров в корзине по informationId
   * @param informationId - ид товара
   * @return лист результата
   */
  @Query("SELECT pu FROM ProductOrder pu WHERE pu.information.id = :informationId")
  fun findAllByInformationId(@Param("informationId") informationId: Int) : List<ProductOrder>
}
