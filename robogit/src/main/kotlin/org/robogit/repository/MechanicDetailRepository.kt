package org.robogit.repository

import org.robogit.domain.MechanicDetail
import org.robogit.dto.MechanicDetailSumDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface MechanicDetailRepository: CrudRepository<MechanicDetail, Int> {

  /**
   * Finds all [MechanicDetail]s with specified [material].
   * @param material is a material with which [MechanicDetail]s will be selected.
   * @return List with results.
   */
  fun findByMaterial(material: String): List<MechanicDetail>

  /**
   * Возвращает все механические детали, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @return лист результатов
   */
  @Query("SELECT new org.robogit.dto.MechanicDetailSumDto(m, sum(p.amount)as s)  FROM MechanicDetail m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc nulls last")
  fun findPopular(): List<MechanicDetailSumDto?>?

  /**
   * Возвращает страницу механических деталей, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @param pageable - номер страницы
   * @return лист результатов
   */
  @Query("SELECT new org.robogit.dto.MechanicDetailSumDto(m, sum(p.amount)as s)  FROM MechanicDetail m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc nulls last")
  fun findPagePopular(pageable: Pageable): Page<MechanicDetailSumDto?>?

  /**
   *  Возвращает механическую деталь по ид
   *  @param id - ид детали
   *  @return деталь
   */
  @Query("SELECT m FROM MechanicDetail m WHERE m.id = :id")
  fun findMechanicDetailById(@Param("id") id: Int) : MechanicDetail?

  /**
   * Применяет фильтры к механическим деталям
   * @param min_price - минимальная цена
   * @param max_price - максимальная цена
   * @return страницу результата
   */
  @Query("SELECT m FROM MechanicDetail m JOIN m.information mi WHERE" +
          "(:min_price IS NULL OR :min_price < mi.price) AND" +
          "(:max_price IS NULL OR :max_price > mi.price)")
  fun filter( pagable: Pageable,
              @Param("min_price") min_price: Float?,
              @Param("max_price") max_price: Float?) : Page<MechanicDetail>

}
