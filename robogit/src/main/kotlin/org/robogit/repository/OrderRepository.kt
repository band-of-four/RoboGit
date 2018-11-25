package org.robogit.repository

import org.robogit.domain.Order
import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository
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
}
