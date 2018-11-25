package org.robogit.repository

import org.robogit.domain.Repository
import org.springframework.data.repository.CrudRepository

interface RepositoryRepository: CrudRepository<Repository, Int> {
}