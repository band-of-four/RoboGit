package org.robogit.repository

import org.robogit.domain.Order
import org.robogit.domain.User
import org.robogit.dto.OrderDto
import org.robogit.dto.OrderSumDto
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface OrderRepository : CrudRepository<Order, Int> {
  /**
   * Finds all [Order]s owned by a specified [user].
   * @param user is a registered [User].
   * @return List with selected [Order]s.
   */
  fun findByUser(user: User): List<Order>

  /**
   * Finds all [Order]s ordered after specified [date].
   * @param date is the left search boundary.
   * @return List with selected [Order]s.
   */
  fun findByDateAfter(date: Date): List<Order>

  /**
   * Finds all [Order]s ordered before specified [date].
   * @param date is the right search boundary.
   * @return List with selected [Order]s.
   */
  fun findByDateBefore(date: Date): List<Order>

  /**
   * Finds all [Order]s ordered between [from] and [to] dates.
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with selected [Order]s.
   */
  fun findByDateBetween(from: Date, to: Date): List<Order>

  /**
   * Finds all [Order]s ordered after specified [date] owned by [user].
   * @param date is the left search boundary of date.
   * @param user is an owner of the order.
   * @return List with selected [Order]s.
   */
  fun findByDateAfterAndUser(date: Date, user: User): List<Order>

  /**
   * Finds all [Order]s ordered before specified [date] owned by [user].
   * @param date is the right search boundary of date.
   * @param user is an owner of the order.
   * @return List with selected [Order]s.
   */
  fun findByDateBeforeAndUser(date: Date, user: User): List<Order>

  /**
   * Finds all [Order]s ordered between [from] and [to] dates, owned by [user].
   * @param from is the left search boundary of date.
   * @param to is the right search boundary of date.
   * @param user is an owner of the order.
   * @return List with selected [Order]s.
   */
  fun findByDateBetweenAndUser(from: Date, to: Date, user: User): List<Order>

  /**
   * Finds all [Order]s ordered to a specified [address].
   * @param address is an delivery address.
   * @return List with selected [Order]s.
   */
  fun findByAddress(address: String): List<Order>

  /**
   * Возвращает таблицу c заказом заказа
   * @param idOrder  - номер заказа
   * @param userId - ид пользователя
   */
  @Query("SELECT new org.robogit.dto.OrderDto" +
      "(i.name as name, p.amount as amount, p.unit_price as price, (p.amount*p.unit_price) as sum) " +
      "FROM ProductOrder p JOIN Information i ON (p.information.id = i.id) " +
      "JOIN p.order po " +
      "JOIN po.user pou " +
      "WHERE p.order.id = :idOrder AND pou.id = :userId")
  fun getOrderByUserIdAndIdOrder(@Param("userId") userId: Int, @Param("idOrder") idOrder: Int?): List<OrderDto?>?

  /**
   * Возвращает таблицу c заказом заказа
   * @param idOrder  - номер заказа
   */
  @Query("SELECT new org.robogit.dto.OrderDto" +
      "(i.name as name, p.amount as amount, p.unit_price as price, (p.amount*p.unit_price) as sum) " +
      "FROM ProductOrder p JOIN Information i ON (p.information.id = i.id)" +
      "WHERE p.order.id = ?#{[0]}")
  fun getOrderByIdOrder(idOrder: Int?): List<OrderDto?>?


  /**
   * Возвращает сумму заказа
   * @param idOrder  - номер заказа
   */
  @Query("SELECT new org.robogit.dto.OrderSumDto(sum(p.unit_price*p.amount))" +
      "FROM ProductOrder p JOIN Information i ON (p.information.id = i.id)" +
      "WHERE p.order.id = ?#{[0]}")
  fun getOrderSumByIdOrder(idOrder: Int?): List<OrderSumDto?>?

  /**
   * Возвращает сумму заказа
   * @param idOrder  - номер заказа
   * @param userId - ид пользователя
   */
  @Query("SELECT new org.robogit.dto.OrderSumDto(sum(p.unit_price*p.amount))" +
      "FROM ProductOrder p JOIN Information i ON (p.information.id = i.id)" +
      "WHERE p.order.id = :idOrder AND p.order.user.id = :userId")
  fun getOrderSumByUserIdAndIdOrder(@Param("userId") userId: Int, @Param("idOrder") idOrder: Int?): List<OrderSumDto?>?
}
