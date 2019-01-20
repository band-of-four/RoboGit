package org.robogit.repository

import org.robogit.domain.Information
import org.robogit.dto.InformationSumDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param


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

  /**
   * Возвращает страницу товаров, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @param pageable - номер страницы
   * @return лист результатов
   */
  @Query("SELECT  new org.robogit.dto.InformationSumDto(i, sum(p.amount)as s)  FROM ProductOrder p JOIN p.information i GROUP BY i.id ORDER BY s desc")
  fun findPagePopular(pageable:Pageable): Page<InformationSumDto?>?

  /**
   * Возвращает все товары, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @return лист результатов
   */
  @Query("SELECT  new org.robogit.dto.InformationSumDto(i, sum(p.amount)as s)  FROM ProductOrder p JOIN p.information i GROUP BY i.id ORDER BY s desc")
  fun findPopular(): List<InformationSumDto?>?

  /**
   * Возвращает страницу популярных товаров с типом "прочие ресурсы"
   * @param pageable pageable - номер страницы
   * @return лист результатов
   */
  @Query("SELECT new org.robogit.dto.InformationSumDto(i, sum(p.amount)as s) FROM ProductOrder p JOIN p.information i WHERE i.type = org.robogit.domain.Type.OTHER_RESOURCES GROUP BY i.id ORDER BY s desc")
  fun findPopularOther(pageable:Pageable): Page<InformationSumDto>

  /**
   * Возвращает товар типа "прочие ресурсы" по id
   * @param id - ид товара
   * @return товар
   */
  @Query("SELECT i FROM Information i WHERE i.type = org.robogit.domain.Type.OTHER_RESOURCES AND i.id = :id")
  fun findOtherById(@Param("id") id: Int) : Information?

  /**
   * Возвращает все товары типа "прочие ресурсы"
   * @return лист результата
   */
  @Query("SELECT i FROM Information i WHERE i.type = org.robogit.domain.Type.OTHER_RESOURCES")
  fun findOther() : List<Information>

  /**
   * Возвращает [Information] по ид
   * @param id - ид товара
   */
  @Query("SELECT i FROM Information i WHERE i.id = :id")
  fun findInformationById(@Param("id") id: Int) : Information?

  /**
   * Применяет фильтры к прочим ресурсам
   * @param min_price - минимальная цена
   * @param max_price - максимальная цена
   * @return страницу результата
   */
  @Query("SELECT i FROM Information i WHERE " +
          "i.type = org.robogit.domain.Type.OTHER_RESOURCES AND" +
          "(:min_price IS NULL OR :min_price < i.price) AND" +
          "(:max_price IS NULL OR :max_price > i.price)")
  fun filterForOther( pagable: Pageable,
              @Param("min_price") min_price: Float?,
              @Param("max_price") max_price: Float?) : Page<Information>
}