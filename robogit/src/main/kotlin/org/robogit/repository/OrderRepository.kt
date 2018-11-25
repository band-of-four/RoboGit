package org.robogit.repository

import org.robogit.domain.Order
import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrderRepository : CrudRepository<Order, Int> {
  fun findByUser(user: User): List<Order>

  fun findByDateAfter(date: Date): List<Order>

  fun findByDateBefore(date: Date): List<Order>

  fun findByDateBetween(from: Date, to: Date): List<Order>

  fun findByDateAfterAndUser(date: Date, user: User): List<Order>

  fun findByDateBeforeAndUser(date: Date, user: User): List<Order>

  fun findByDateBetweenAndUser(from: Date, to: Date, user: User): List<Order>

  fun findByAddress(address: String): List<Order>
}
