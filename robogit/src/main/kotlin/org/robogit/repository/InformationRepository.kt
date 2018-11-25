package org.robogit.repository

import org.robogit.domain.Information
import org.springframework.data.repository.CrudRepository

interface InformationRepository : CrudRepository<Information, Int> {
  fun findByType(type: String): List<Information>

  fun findByModel(model: String): List<Information>

  fun findByPriceGreaterThan(price: Float): List<Information>

  fun findByPriceLessThan(price: Float): List<Information>

  fun findByPriceBetween(from: Float, to: Float): List<Information>
}
