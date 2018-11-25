package org.robogit.repository

import org.robogit.domain.Interface
import org.springframework.data.repository.CrudRepository
import java.util.*

interface InterfaceRepository: CrudRepository<Interface, Int> {
  fun findByName(name: String): Interface?
}
