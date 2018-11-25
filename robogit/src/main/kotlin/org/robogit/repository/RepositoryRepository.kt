package org.robogit.repository

import org.robogit.domain.Repository
import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface RepositoryRepository: CrudRepository<Repository, Int> {

  /**
   * Finds all repositories owned by specified [user].
   * @param user is an owner of the repositories.
   * @return List with selected repositories.
   */
  fun findByUser(user: User): List<Repository>

  /**
   * Finds corresponding repositories by specified [path].
   * @param path is a path to the repository in the file system directly.
   * @return Selected repositories.
   */
  fun findByPath(path: String): List<Repository>

  /**
   * Finds repositories with specified [name].
   * @param name is a repository title.
   * @return List with selected repositories.
   */
  fun findByName(name: String): List<Repository>
}
