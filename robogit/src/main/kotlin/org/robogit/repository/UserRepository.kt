package org.robogit.repository

import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {

  /**
   * Finds [User] with specified [login].
   * @return Instance of [User] if exists, null - otherwise.
   */
  fun findByLogin(login: String): User?

  /**
   * @return true if [User] with specified [login] already exists
   */
  fun existsByLogin(login: String): Boolean

  /**
   * Finds [User]s with specified [telegramId].
   * @param telegramId is an identifier related to a [User] of a few [User]s.
   * @return List with selected users.
   */
  fun findByTelegramId(telegramId: String): List<User>

  /**
   * Возвращает юзера по email
   * @param email - email
   * @return User
   */
  fun findByEmail(email: String): User?
}
