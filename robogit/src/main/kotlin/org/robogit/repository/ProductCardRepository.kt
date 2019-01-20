package org.robogit.repository

import org.robogit.domain.Order
import org.robogit.domain.ProductCard
import org.robogit.domain.ProductOrder
import org.robogit.domain.ProductUser
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ProductCardRepository: CrudRepository<ProductCard, Int> {

  /**
   * Finds list with instances of [ProductCard] with specified [card].
   * @return List with results.
   */
  fun findByCard(card: ProductCard): List<ProductCard>

  /**
   * Поиск товаров в корзине по informationId
   * @param informationId - ид товара
   * @return лист результата
   */
  @Query("SELECT pu FROM ProductCard pu WHERE pu.information.id = :informationId")
  fun findAllByInformationId(@Param("informationId") informationId:Int) : List<ProductCard>?
}
