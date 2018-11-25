package org.robogit.repository

import org.robogit.domain.Repository
import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface RepositoryRepository: CrudRepository<Repository, Int> {
  fun findByUser(user: User): List<Repository>

  fun findByPath(path: String): List<Repository>

  fun findByName(name: String): List<Repository>
}
