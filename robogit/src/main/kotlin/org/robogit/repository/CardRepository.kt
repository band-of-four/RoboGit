package org.robogit.repository

import org.robogit.domain.Card
import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface CardRepository: CrudRepository<Card, Int> {
  /**
   * Finds all [Card]s owned by a specified [user]
   * @param user is an owner of the [Card]
   * @return list of [Card]s owned by [user]
   */
  fun findAllByUser(user: User): List<Card>?
}
