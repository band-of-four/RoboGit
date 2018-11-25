package org.robogit.repository

import org.robogit.domain.Star
import org.springframework.data.repository.CrudRepository

interface StarRepository: CrudRepository<Star, Int> {
}