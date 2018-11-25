package org.robogit.repository

import org.robogit.domain.Repository
import org.robogit.domain.Star
import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface StarRepository: CrudRepository<Star, Int> {
  fun findByUser(user: User): List<Star>

  fun findByRepository(repository: Repository): List<Star>
}
