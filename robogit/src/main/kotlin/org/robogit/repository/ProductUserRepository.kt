package org.robogit.repository

import org.robogit.domain.ProductUser
import org.robogit.domain.User
import org.robogit.dto.CardElementDto
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ProductUserRepository: CrudRepository<ProductUser, Int> {

  /**
   * Finds all shopping carts (i.e. instances of [ProductUser]) owned by specified [user].
   * @param user is an owner of the shopping carts.
   * @return List of shopping carts.
   */
  fun findByUser(user: User): List<ProductUser>

  /**
   * Находит товар в корзине по ид товара и ид юзера
   * @param userId - ид юзера
   * @param id - ид товара
   * @return
   */
  @Query("SELECT pu FROM ProductUser pu JOIN pu.user puu WHERE puu.id = :userId AND pu.id = :id")
  fun findByUserIdAndId(@Param("userId") userId:Int, @Param("id") id:Int) : ProductUser?

  /**
   * Возвращает все товары в корзине юзера по ид
   * @param userId - ид юзера
   * @return Лист результатов
   */
  @Query("SELECT new org.robogit.dto.CardElementDto(pu.information, pu.amount, pu.id) FROM ProductUser pu JOIN pu.user puu WHERE puu.id = :userId")
  fun findAllByUserId(@Param("userId") userId:Int) : List<CardElementDto>?

  /**
   * Поиск товаров в корзине по informationId
   * @param informationId - ид товара
   * @return лист результата
   */
  @Query("SELECT pu FROM ProductUser pu WHERE pu.information.id = :informationId")
  fun findAllByInformationId(@Param("informationId") informationId:Int) : List<ProductUser>?
}
