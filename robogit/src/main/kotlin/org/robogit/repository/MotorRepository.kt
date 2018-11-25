package org.robogit.repository

import org.robogit.domain.Motor
import org.springframework.data.repository.CrudRepository

interface MotorRepository: CrudRepository<Motor, Int> {
}