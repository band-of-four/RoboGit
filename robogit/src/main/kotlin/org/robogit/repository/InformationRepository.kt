package org.robogit.repository

import org.robogit.domain.Information
import org.springframework.data.repository.CrudRepository

interface InformationRepository : CrudRepository<Information, Int> {

  /**
   * Finds all instances of [Information] with specified [Information.type].
   * @param type is a type of product with which instances of [Information] will be selected.
   * @return List with results.
   */
  fun findByType(type: String): List<Information>

  /**
   * Finds all instances of [Information] with specified [Information.model].
   * @param model is a model of product with which instances of [Information] will be selected.
   * @return List with results.
   */
  fun findByModel(model: String): List<Information>

  /**
   * Finds all instances of [Information] with [Information.price] less than [price].
   * @param price is the left search boundary.
   * @return List with results.
   */
  fun findByPriceGreaterThan(price: Float): List<Information>

  /**
   * Finds all instances of [Information] with [Information.price] greater than [price].
   * @param price is the right search boundary.
   * @return List with results.
   */
  fun findByPriceLessThan(price: Float): List<Information>

  /**
   * Finds all instances of [Information] with [Information.price] between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByPriceBetween(from: Float, to: Float): List<Information>
}
