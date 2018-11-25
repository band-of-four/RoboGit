package org.robogit.repository

import org.robogit.domain.Interface
import org.springframework.data.repository.CrudRepository

interface InterfaceRepository: CrudRepository<Interface, Int> {
}