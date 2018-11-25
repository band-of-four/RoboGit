package org.robogit.repository

import org.robogit.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
}