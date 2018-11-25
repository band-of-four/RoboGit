package org.robogit.repository

import org.robogit.domain.Repository
import org.robogit.domain.Star
import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface StarRepository: CrudRepository<Star, Int> {

  /**
   * Finds [Star]s registered by [user].
   * @return List with [Star]s.
   */
  fun findByUser(user: User): List<Star>

  /**
   * Finds all [Star]s pertain to the [repository].
   * @return List with [Star]s.
   */
  fun findByRepository(repository: Repository): List<Star>
}
