package org.robogit.repository

import org.robogit.domain.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<Order, Int> {
}