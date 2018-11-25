package org.robogit.repository

import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
  fun findByLogin(login: String): User?

  fun findByTelegramId(telegramId: String): List<User>
}
